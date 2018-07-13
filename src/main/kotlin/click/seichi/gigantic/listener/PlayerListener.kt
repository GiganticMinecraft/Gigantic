package click.seichi.gigantic.listener

import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.player.PlayerRepository
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.*

/**
 * @author tar0ss
 */
class PlayerListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player ?: return
        val inventory = player.inventory
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
//        if (!BreakSkillDispatcher(Explosion(), gPlayer, MineBlockReason.EXPLOSION, block).dispatch()) {
//            event.isCancelled = true
//        }
    }

    // プレイヤーの全てのインベントリークリックをキャンセル
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        event.whoClicked as? Player ?: return
        event.isCancelled = true
    }

    // プレイヤーのメニュー以外のインベントリーオープンをキャンセル
    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        event.player as? Player ?: return
        if (event.inventory.holder is Menu) return
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerArmorStandManipulate(event: PlayerArmorStandManipulateEvent) {
        event.isCancelled = true
    }

    // プレイヤーの全てのドロップをキャンセル
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onDropItem(event: PlayerDropItemEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerChangedMainHand(event: PlayerChangedMainHandEvent) {
        // TODO change item
    }

    @EventHandler
    fun onPlayerItemConsume(event: PlayerItemConsumeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player ?: return
        val slot = player.inventory.heldItemSlot
        val gPlayer = player.gPlayer ?: return
        gPlayer.belt.getHookedItem(slot)?.onInteract(player, event)
    }

    @EventHandler
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        event.isCancelled = true
        val player = event.player ?: return
        val gPlayer = player.gPlayer ?: return
        gPlayer.switchBelt()
    }

    @EventHandler
    fun onLevelUp(event: LevelUpEvent) {
        val gPlayer = event.player.gPlayer ?: return
        gPlayer.mana.onLevelUp(event)
        gPlayer.aptitude.onLevelUp(event)
    }

}