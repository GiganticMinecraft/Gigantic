package click.seichi.gigantic.player.components

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.config.PlayerLevelConfig.LEVEL_MAP
import click.seichi.gigantic.config.PlayerLevelConfig.MAX
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


    var current: Int = 1
        private set

    private var exp: Long = 0L

    private fun calcLevel(exp: Long) =
            (1..MAX).firstOrNull {
                !canLevelUp(it + 1, exp)
            } ?: MAX

    private fun canLevelUp(nextLevel: Int, exp: Long) =
            exp > LEVEL_MAP[nextLevel] ?: Long.MAX_VALUE

    fun update(player: Player, playSound: Boolean) {
        val nextExp = expProduceCalculating(player)
        val diff = nextExp - exp
        exp = nextExp
        var isLevelUp = false
        while (canLevelUp(current + 1, exp)) {
            current++
            isLevelUp = true
            if (current >= MAX) {
                current = MAX
                break
            }
        }
        if (!playSound) return
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

}
