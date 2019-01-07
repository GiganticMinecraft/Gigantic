package click.seichi.gigantic.listener

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.player.Defaults
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @author tar0ss
 */
class AchievementListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player ?: return
        player.transform(Keys.UPDATE_COUNT) { count ->
            val next = count.inc()
            if (next % Defaults.ACHIEVEMENT_BLOCK_BREAK_UPDATE_COUNT == 0) {
                Achievement.update(player)
                0
            } else next
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player ?: return

        // ここで実績を確認する．これ以前では実績を使ってはいけない
        Achievement.update(player, isForced = true)
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onLevelUp(event: LevelUpEvent) {
        Achievement.update(event.player)
    }
}