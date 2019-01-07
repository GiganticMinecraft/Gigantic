package click.seichi.gigantic.battle

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.updateDisplay
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.BattleMessages
import click.seichi.gigantic.message.messages.RelicMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.ai.SoulMonsterState
import click.seichi.gigantic.popup.pops.BattlePops
import click.seichi.gigantic.popup.pops.PopUpParameters
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.sound.sounds.BattleSounds
import click.seichi.gigantic.util.NoiseData
import click.seichi.gigantic.util.Random
import org.bukkit.Chunk
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

/**
 * @author tar0ss
 */
class Battle internal constructor(
        val chunk: Chunk,
        val spawner: Player,
        val players: Set<Player>,
        val monster: SoulMonster,
        val quest: Quest?
) {
    private val uuid = UUID.randomUUID()
    // 使用言語
    private val locale = spawner.wrappedLocale
    // 参加プレイヤー
    private val battlers = players.map { BattlePlayer(it, it == spawner) }.toMutableSet()
    // スポーンプレイヤー
    private val battleSpawner = this.battlers.find { it.isSpawner }!!
    // 敵
    private val enemy = BattleMonster(monster, this.battleSpawner, chunk)
    // 経過時間
    var elapsedTick: Long = -1L
        private set

    fun getJoinedPlayers() = battlers.toSet()

    fun isJoined(player: Player) = battlers.find { it.equals(player) } != null

    fun leave(player: Player) {
        val battlePlayer = battlers.find { it.equals(player) } ?: return
        leave(battlePlayer)
    }

    private fun leave(battlePlayer: BattlePlayer) {
        battlers.remove(battlePlayer)
        enemy.leave(battlePlayer)
        enemy.updateTargets(battlers)
    }

    fun start(spawnLocation: Location) {
        enemy.awake(spawnLocation, getJoinedPlayers())
        battlers.forEach {
            BattleSounds.START.playOnly(it.player)
        }
        object : BukkitRunnable() {
            override fun run() {
                elapsedTick++
                update()
                if (battlers.isEmpty()) {
                    cancel()
                    return
                }
            }
        }.runTaskTimer(Gigantic.PLUGIN, 1L, 1L)
    }

    fun update() {
        getJoinedPlayers().forEach {
            if (!it.player.isValid || it.player.gameMode != GameMode.SURVIVAL) {
                leave(it)
            }
        }
        when {
            !battleSpawner.player.isValid
                    || battlers.isEmpty()
                    || enemy.state == SoulMonsterState.DISAPPEAR -> end()
            battleSpawner.player.isDead -> lose()
            enemy.state == SoulMonsterState.DEATH -> win()
        }
        enemy.update(elapsedTick)
        elapsedTick++
    }

    fun end() {
        battlers.toSet().forEach {
            Achievement.update(it.player)
            leave(it)
        }
        enemy.remove()
        BattleManager.endBattle(this)
    }

    fun tryDefence(player: Player, block: Block) {
        enemy.defencedByPlayer(block)
    }

    fun tryAttack(player: Player, block: Block) {
        val battler = battlers.find { it.equals(player) } ?: return
        val damage = 1L
        val trueDamage = enemy.damageByPlayer(player, damage)
        BattlePops.BATTLE_DAMAGE(trueDamage).pop(block.centralLocation, NoiseData(sizeY = PopUpParameters.BATTLE_DAMAGE_DIFF))
    }

    private fun win() {
        battlers.forEach {
            BattleSounds.WIN.play(it.player.location)
            BattleMessages.WIN(monster).sendTo(it.player)
            monster.defeatedBy(it.player)
        }
        monster.dropRelicSet.firstOrNull { it.probability > Random.nextDouble() }
                ?.let { drop ->
                    battlers.forEach { battlePlayer ->
                        drop.relic.dropTo(battlePlayer.player)
                        RelicMessages.DROP(drop).sendTo(battlePlayer.player)
                    }
                }
        quest?.process(battleSpawner.player)
        battleSpawner.player.updateDisplay(true, true)
        end()
    }

    private fun lose() {
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