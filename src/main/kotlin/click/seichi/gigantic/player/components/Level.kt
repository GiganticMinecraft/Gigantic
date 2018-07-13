package click.seichi.gigantic.player.components

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.config.PlayerLevelConfig.LEVEL_MAP
import click.seichi.gigantic.config.PlayerLevelConfig.MAX
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.schedule.Scheduler
import click.seichi.gigantic.sound.PlayerSounds
import io.reactivex.Observable
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class Level {

    enum class ExpProducer(private val producing: (Player) -> Long) {
        MINE_BLOCK(
                { player ->
                    player.gPlayer?.mineBlock?.get(MineBlockReason.GENERAL) ?: 0L
                }
        )
        ;

        fun produce(player: Player) = producing(player)
    }

    private val expProduceCalculating: (Player) -> Long = { player ->
        ExpProducer.values().map { it.produce(player) }.sum()
    }


    var current: Int = 0
        private set

    private var exp: Long = 0L

    private fun canLevelUp(nextLevel: Int, exp: Long) =
            exp >= LEVEL_MAP[nextLevel] ?: Long.MAX_VALUE

    fun init(player: Player) {
        // 経験値を計算
        exp = expProduceCalculating(player)
        // レベルを更新
        updateLevel()
        // 表示を更新
        display(player)
    }


    fun update(player: Player) {
        // 次の経験値を計算
        val nextExp = expProduceCalculating(player)
        val diff = nextExp - exp
        exp = nextExp
        // レベルを更新
        val isLevelUp = updateLevel()
        // レベルと経験値によって音を出力
        playSound(player, isLevelUp, diff)
        // call Event
        if (isLevelUp) Bukkit.getPluginManager().callEvent(LevelUpEvent(current, player))
        // 表示を更新
        display(player)
    }

    private fun display(player: Player) {
        player.level = current
        val expToLevel = LEVEL_MAP[current] ?: 0L
        val expToNextLevel = LEVEL_MAP[current + 1] ?: LEVEL_MAP[MAX]!!
        player.exp = (exp - expToLevel).div((expToNextLevel - expToLevel).toFloat())
    }

    private fun playSound(player: Player, isLevelUp: Boolean, diff: Long) {
        if (isLevelUp) {
            PlayerSounds.LEVEL_UP.play(player.location)
        } else if (diff > 0) {
            val count = if (diff > 20) 20 else diff
            Observable.interval(2, TimeUnit.MILLISECONDS)
                    .take(count)
                    .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                    .subscribe { PlayerSounds.INCREASE_EXP.play(player) }
        }
    }

    private fun updateLevel(): Boolean {
        var isLevelUp = false
        while (canLevelUp(current + 1, exp)) {
            current++
            isLevelUp = true
            if (current >= MAX) {
                current = MAX
                break
            }
        }
        return isLevelUp
    }

}
