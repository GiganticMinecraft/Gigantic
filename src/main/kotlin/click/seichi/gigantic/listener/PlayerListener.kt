package click.seichi.gigantic.listener

import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.player.PlayerRepository
import click.seichi.gigantic.skill.breakskill.Explosion
import click.seichi.gigantic.skill.dispather.BreakSkillDispatcher
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
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
        if (event.isCancelled) return
        val player = event.player ?: return
        val block = event.block ?: return
        val gPlayer = player.gPlayer ?: return
        // エクスプロージョン発火
        if (!BreakSkillDispatcher(Explosion(), gPlayer, MineBlockReason.EXPLOSION, block).dispatch()) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        event.whoClicked as? Player ?: return
        event.isCancelled = true
    }

    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        event.player as? Player ?: return
        event.isCancelled = true
    }


}