package click.seichi.gigantic.battle

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.BattleMessages
import click.seichi.gigantic.message.messages.RelicMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.ai.SoulMonsterState
import click.seichi.gigantic.popup.pops.BattlePops
import click.seichi.gigantic.popup.pops.PopUpParameters
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.sound.sounds.BattleSounds
import click.seichi.gigantic.sound.sounds.SoulMonsterSounds
import org.bukkit.Chunk
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
class Battle internal constructor(
        val chunk: Chunk,
        val spawner: Player,
        val monster: SoulMonster,
        val quest: Quest?
) {
    private val uuid = UUID.randomUUID()
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
        BattleMessages.SPAWN(monster).sendTo(spawner)
        enemy.spawn()
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
            it.offer(Keys.LAST_BATTLE, null)
        }
        isStarted = true
    }

    private fun disappearCondition(): Boolean {
        return when {
            !spawner.isValid -> true
            spawner.location.distance(enemy.location) > 30.0 && !isStarted -> true
            players.isEmpty() && isStarted -> true
            else -> false
        }
    }

    private fun loseCondition(): Boolean {
        return when {
            !isStarted -> false
            spawner.isValid && spawner.isDead -> true
            else -> false
        }
    }

    fun update() {
        when (enemy.state) {
            SoulMonsterState.DEATH -> win()
            SoulMonsterState.DISAPPEAR -> {
                SoulMonsterSounds.DISAPPEAR.play(enemy.eyeLocation)
                end()
            }
            else -> {
            }
        }
        getJoinedPlayers().forEach {
            if (!it.isValid) {
                leave(it)
                if (it.isDead) {
                    it.offer(Keys.LAST_BATTLE, this)
                }
            }
        }
        if (loseCondition()) {
            lose()
            return
        }
        if (disappearCondition()) {
            end()
            return
        }
        if (!isStarted) {
            if (elapsedTick % 8L == 0L)
                MonsterSpiritAnimations.AMBIENT_EXHAUST(enemy.color).exhaust(
                        spawner,
                        enemy.eyeLocation,
                        meanY = 0.9
                )
        }
        MonsterSpiritAnimations.AMBIENT(enemy.color).start(enemy.eyeLocation)
        enemy.update(elapsedTick)
        elapsedTick++
    }


    var isEnded: Boolean = false
        private set

    fun end() {
        isEnded = true
        players.toSet().forEach { leave(it) }
        enemy.remove()
    }

    fun tryDefence(player: Player, block: Block) {
        enemy.defencedByPlayer(block)
    }

    fun tryAttack(player: Player, block: Block) {
        if (!isStarted) return
        val damage = 1.toBigDecimal()
        val trueDamage = enemy.damageByPlayer(player, damage)
        BattlePops.BATTLE_DAMAGE(trueDamage).pop(block.centralLocation, diffY = PopUpParameters.BATTLE_DAMAGE_DIFF)
    }

    private fun win() {
        BattleSounds.WIN.play(enemy.eyeLocation)
        players.forEach {
            BattleMessages.WIN(monster).sendTo(it)
            enemy.defeatedBy(it)
        }
        quest?.process(spawner, monster)
        enemy.randomDrops()?.let { drop ->
            players.forEach { player ->
                drop.relic.dropTo(player)
                RelicMessages.DROP(drop).sendTo(player)
                Achievement.update(player)
            }
        }
        end()
    }

    private fun lose() {
        players.forEach {
            it.offer(Keys.LAST_BATTLE, this)
        }
        end()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Battle) return false
        return this.uuid == other.uuid
    }

    override fun hashCode(): Int {
        return this.uuid.hashCode()
    }
}