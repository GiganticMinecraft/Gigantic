package click.seichi.gigantic.listener

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.hasAptitude
import click.seichi.gigantic.extension.isCrust
import click.seichi.gigantic.extension.isTree
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/**
 * @author tar0ss
 */
class WillListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        val block = event.block ?: return
        if (!block.isCrust && !block.isTree) return

        Will.values().filter {
            // カウント条件を満たしている
            when (it.grade) {
                WillGrade.BASIC -> Achievement.WILL_BASIC.isGranted(player)
                WillGrade.ADVANCED -> Achievement.WILL_ADVANCED.isGranted(player)
            }
        }.filter {
            // 適性を持たない
            !player.hasAptitude(it)
        }.filter {
            // スポーン条件を満たしている
            it.canSpawn(player, block)
        }.forEach { will ->
            player.transform(Keys.WILL_SECRET_MAP[will]!!) {
                it.inc()
            }
        }
    }

}