package click.seichi.gigantic.listener

import click.seichi.gigantic.player.PlayerRepository
import click.seichi.gigantic.skill.SkillResolver
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author tar0ss
 */
class PlayerListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player ?: return

        PlayerRepository.add(player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player ?: return

        PlayerRepository.remove(player)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player ?: return
        val block = event.block ?: return
        // エクスプロージョン発火
        if (!SkillResolver(player).fireExplosion(block)) {
            event.isCancelled = true
        }
    }
}