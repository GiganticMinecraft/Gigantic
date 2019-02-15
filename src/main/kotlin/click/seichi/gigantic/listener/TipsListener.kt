package click.seichi.gigantic.listener

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.message.Tips
import click.seichi.gigantic.player.Display
import click.seichi.gigantic.util.Random
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import kotlin.random.asKotlinRandom

/**
 * @author tar0ss
 */
class TipsListener : Listener {

    private val tips = Tips.values().toList()

    private val remainSet = tips.map { it.ordinal }.toMutableSet()

    @EventHandler
    fun onTick(event: TickEvent) {
        if (event.ticks.plus(10) % 20L.times(60L).times(Config.TIPS_INTERVAL) != 0L) return
        // 一定間隔で呼び出す

        // 全て消費したら補充
        if (remainSet.isEmpty()) {
            remainSet.addAll(tips.map { it.ordinal })
        }

        // 次のメッセージへ
        val nextIndex = remainSet.random(Random.generator.asKotlinRandom())
        remainSet.remove(nextIndex)

        val message = tips[nextIndex]

        Bukkit.getServer().onlinePlayers
                .asSequence()
                .filterNotNull()
                .filter { it.isValid }
                .forEach { player ->
                    if (Display.TIPS.isDisplay(player)) {
                        message.sendTo(player)
                    }
                }
    }

}