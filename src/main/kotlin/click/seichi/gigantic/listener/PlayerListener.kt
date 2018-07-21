package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.central
import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.PlayerRepository
import click.seichi.gigantic.player.belt.Belt
import click.seichi.gigantic.player.defalutInventory.inventories.MainInventory
import click.seichi.gigantic.spirit.SpiritManager
import click.seichi.gigantic.spirit.spawnreason.WillSpawnReason
import click.seichi.gigantic.spirit.spirits.WillSpirit
import click.seichi.gigantic.will.WillSize
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


/**
 * @author tar0ss
 */
class PlayerListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player ?: return
        PlayerRepository.add(player)
        player.inventory.heldItemSlot = Belt.TOOL_SLOT
        player.updateInventory()
        player.saturation = Float.MAX_VALUE
        player.foodLevel = 20
        // 4秒間無敵付与
        player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
                80,
                5,
                false,
                false
        ))
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player ?: return

        if (player.gameMode == GameMode.SPECTATOR) {
            player.teleport(MainInventory.lastLocationMap.remove(player.uniqueId))
            player.gameMode = GameMode.SURVIVAL
        }

        PlayerRepository.remove(player)
    }

    // プレイヤーのメニュー以外のインベントリーオープンをキャンセル
    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        event.player as? Player ?: return
        if (event.player.gameMode != GameMode.SURVIVAL) return
        if (event.inventory.holder is Menu) return
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerArmorStandManipulate(event: PlayerArmorStandManipulateEvent) {
        if (event.player.gameMode != GameMode.SURVIVAL) return
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
        if (event.player.gameMode != GameMode.SURVIVAL) return
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerItemHeld(event: PlayerItemHeldEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        val gPlayer = player.gPlayer ?: return
        gPlayer.belt.getHookedItem(event.newSlot)?.onItemHeld(player, event)
        if (event.newSlot != Belt.TOOL_SLOT) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        event.isCancelled = true

        val gPlayer = player.gPlayer ?: return
        gPlayer.switchBelt()
    }

    @EventHandler
    fun onLevelUp(event: LevelUpEvent) {
        val gPlayer = event.player.gPlayer ?: return

        // Update player mana
        gPlayer.mana.run {
            updateMaxMana(gPlayer.level)
            increase(max, true)
        }

        // Displays
        PlayerMessages.MANA_DISPLAY(gPlayer.manaBar, gPlayer.mana)

        // Update player Belt
        gPlayer.belt.update(event.player)

        // Update will aptitude
        val will = gPlayer.aptitude.addIfNeeded(gPlayer.level) ?: return

        // Messages
        if (gPlayer.level.current == 1) PlayerMessages.FIRST_OBTAIN_WILL_APTITUDE(will).sendTo(event.player)
        else PlayerMessages.OBTAIN_WILL_APTITUDE(will).sendTo(event.player)

        // Spawn will that added to player
        SpiritManager.spawn(WillSpirit(WillSpawnReason.AWAKE, event.player.eyeLocation
                .clone()
                .let {
                    it.add(
                            it.direction.x * 2,
                            0.0,
                            it.direction.z * 2
                    )
                }, will, event.player, WillSize.MEDIUM))
    }

    @EventHandler
    fun onChangeFoodLevel(event: FoodLevelChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onHunger(event: EntityDamageEvent) {
        event.entity as? Player ?: return
        if (event.cause == EntityDamageEvent.DamageCause.STARVATION) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        event.keepInventory = true
        event.keepLevel = true
    }

    @EventHandler
    fun onChangeGameMode(event: PlayerGameModeChangeEvent) {
        if (event.newGameMode != GameMode.SURVIVAL) return
        val player = event.player ?: return
        val gPlayer = player.gPlayer ?: return
        gPlayer.belt.update(player)
        gPlayer.defaultInventory.update(player)
    }

    @EventHandler
    fun onRegainBySatiated(event: EntityRegainHealthEvent) {
        if (event.regainReason != EntityRegainHealthEvent.RegainReason.SATIATED) return
        event.isCancelled = true
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        if (event.block.type != Material.LOG && event.block.type != Material.LOG_2) return
        breakTree(event.block, event.block)
    }

    @Suppress("DEPRECATION")
    private fun breakTree(tree: Block, baseBlock: Block) {
        if (tree.type != Material.LOG && tree.type != Material.LOG_2 &&
                tree.type != Material.LEAVES && tree.type != Material.LEAVES_2) return
        if (Math.abs(tree.location.x - baseBlock.location.x) >= 3
                || Math.abs(tree.location.z - baseBlock.location.z) >= 3) return
        if (tree != baseBlock) {
            tree.world.playEffect(tree.location.central, Effect.STEP_SOUND, tree.type.id)
            tree.type = Material.AIR
        }
        for (face in BlockFace.values().subtract(listOf(BlockFace.SELF, BlockFace.DOWN)))
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, { breakTree(tree.getRelative(face), baseBlock) }, 4L)
    }

}