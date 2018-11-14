package click.seichi.gigantic.battle

import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.RelicMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.ai.SoulMonsterState
import click.seichi.gigantic.popup.pops.BattlePops
import click.seichi.gigantic.popup.pops.PopUpParameters
import click.seichi.gigantic.sound.sounds.BattleSounds
import org.bukkit.Chunk
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class Battle internal constructor(
        val chunk: Chunk,
        val spawner: Player,
        monster: SoulMonster
) {
    // 使用言語
    private val locale = spawner.wrappedLocale
    // 経過時間
    var elapsedTick: Long = -1L
    // 敵
    val enemy = BattleMonster(monster, spawner, chunk, locale)
    // プレイヤー
    private val players = mutableSetOf<Player>()
    // 敵との戦闘が始まっているかどうか
    var isStarted: Boolean = false
        private set

    fun getJoinedPlayers() = players.toList()

    fun isJoined(player: Player) = players.contains(player)

    fun spawnEnemy() {
        enemy.spawn()
    }

    fun disappearCondition(): Boolean {
        when {
            !spawner.isValid -> return true
            spawner.location.distance(enemy.location) > 30.0 && !isStarted -> return true
            players.isEmpty() && isStarted -> return true
        }
        return false
    }


    fun join(player: Player): Boolean {
        return if (!isStarted) {
            players.add(player)
            enemy.join(player)
            true
        } else false
    }

    fun leave(player: Player) {
        players.remove(player)
        enemy.leave(player)
        if (isStarted)
            enemy.updateTargets(players)

        if (!isStarted && players.isEmpty()) {
            enemy.resetAwakeProgress()
        }
    }

    fun updateAwakeProgress(nextProgress: Double) {
        enemy.updateAwakeProgress(nextProgress)
    }


    fun start() {
        enemy.awake()
        players.forEach {
            BattleSounds.START.playOnly(it)
        }
        isStarted = true
    }

    fun update() {
        if (!isStarted) {
            if (elapsedTick % 8L == 0L)
                MonsterSpiritAnimations.AMBIENT_EXHAUST(enemy.color).exhaust(
                        spawner,
                        enemy.eyeLocation,
                        meanY = 0.9
                )
        }
        MonsterSpiritAnimations.AMBIENT(enemy.color).start(enemy.location)
        enemy.update(elapsedTick)
        when (enemy.state) {
            SoulMonsterState.DEATH -> win()
            SoulMonsterState.DISAPPEAR -> end()
            SoulMonsterState.KILL_SPAWNER -> lose()
            else -> {
            }
        }
        elapsedTick++
    }

    fun end() {
        players.toSet().forEach { leave(it) }
        enemy.remove()
    }

    fun tryDefence(player: Player, block: Block) {
        enemy.defencedByPlayer(block)
    }

    fun tryAttack(player: Player, block: Block) {
        val damage = 1.toBigDecimal()
        val trueDamage = enemy.damageByPlayer(player, damage)
        BattlePops.BATTLE_DAMAGE(trueDamage).pop(block.centralLocation, diffY = PopUpParameters.BATTLE_DAMAGE_DIFF)
    }

    private fun win() {
        BattleSounds.WIN.play(enemy.eyeLocation)

        enemy.randomDrops()?.let { drop ->
            players.forEach { player ->
                player.transform(Keys.RELIC_MAP[drop.relic]!!) {
                    it + 1
                }
                RelicMessages.DROP(drop).sendTo(player)
            }
        }
        end()
    }

    private fun lose() {
        end()
    }
}