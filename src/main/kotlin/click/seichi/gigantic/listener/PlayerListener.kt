package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.belt.belts.CutBelt
import click.seichi.gigantic.belt.belts.DigBelt
import click.seichi.gigantic.belt.belts.MineBelt
import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.spirit.SpiritManager
import click.seichi.gigantic.spirit.spawnreason.WillSpawnReason
import click.seichi.gigantic.spirit.spirits.WillSpirit
import click.seichi.gigantic.will.WillSize
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
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
import java.util.concurrent.TimeUnit


/**
 * @author tar0ss
 */
class PlayerListener : Listener {

    @EventHandler
    fun onPlayerPreLoginAsync(event: AsyncPlayerPreLoginEvent) {
        runBlocking {
            /**
             * 複数サーバで動かすと，ログアウト時の書き込みよりもログイン時の読込の方が早くなってしまい，
             * データが消失するので，ある程度余裕を持って３秒delay．
             * このためだけのcolumn用意するべきかも
             */
            delay(3L, TimeUnit.SECONDS)
            PlayerCacheMemory.add(event.uniqueId, event.name)
        }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player ?: return
        if (!PlayerCacheMemory.contains(player.uniqueId)) {
            return
        }
        if (player.gameMode == GameMode.SPECTATOR) {
            player.find(CatalogPlayerCache.AFK_LOCATION)?.getLocation()?.let {
                player.teleport(it)
            }
            player.gameMode = GameMode.SURVIVAL
        }
        val uniqueId = player.uniqueId
        PlayerCacheMemory.remove(uniqueId, true)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player ?: return

        /**
         * この処理がないと、cacheがロードされずに参加できてしまう
         */
        if (!PlayerCacheMemory.contains(player.uniqueId)) {
            player.kickPlayer("Server is starting...Please wait a few seconds.")
            return
        }

        if (!player.isOp) player.gameMode = GameMode.SURVIVAL

        player.manipulate(CatalogPlayerCache.LEVEL) {
            it.calculate(ExpProducer.calcExp(player)) {}
            PlayerMessages.EXP_BAR_DISPLAY(it).sendTo(player)
        }

        player.manipulate(CatalogPlayerCache.MANA) {
            it.updateMaxMana()
            it.createBar()
            if (LockedFunction.MANA.isUnlocked(player))
                it.display()
        }

        player.find(Keys.BELT)?.wear(player)
        player.find(Keys.BAG)?.carry(player)

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

        // Messages
        player.transform(Keys.IS_FIRST_JOIN) {
            if (it) PlayerMessages.FIRST_JOIN.sendTo(player)
            false
        }

        trySendingUnlockMessage(player)

        PlayerMessages.MEMORY_SIDEBAR(
                player.find(CatalogPlayerCache.MEMORY) ?: return,
                player.find(CatalogPlayerCache.APTITUDE) ?: return
        ).sendTo(player)
    }

    fun trySendingUnlockMessage(player: Player) {
        Keys.HAS_UNLOCKED_MAP.forEach { func, key ->
            if (!func.isUnlocked(player)) return@forEach
            player.transform(key) { hasUnlocked ->
                if (!hasUnlocked) func.unlockMessage?.sendTo(player)
                true
            }
        }
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
        // TODO change button
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
        val belt = player.find(Keys.BELT) ?: return
        belt.getHotButton(event.newSlot)?.onItemHeld(player, event)
        if (belt.hasFixedSlot() && !belt.isFixed(event.newSlot))
            event.isCancelled = true
    }

    @EventHandler
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        event.isCancelled = true
        switchBelt(player)

    }

    // TODO 自由に選択できるようにする
    fun switchBelt(player: Player) {
        val oldBelt = player.find(Keys.BELT) ?: return
        player.offer(Keys.BELT, when (oldBelt) {
            MineBelt -> DigBelt
            DigBelt -> CutBelt
            else -> MineBelt
        }.apply { wear(player) })
    }

    @EventHandler
    fun onLevelUp(event: LevelUpEvent) {
        val player = event.player

        trySendingUnlockMessage(player)

        player.manipulate(CatalogPlayerCache.MANA) {
            it.updateMaxMana()
            it.increase(it.max, true)
            if (LockedFunction.MANA.isUnlocked(player))
                it.display()
        }

        player.find(Keys.BELT)?.wear(player)
        player.find(Keys.BAG)?.carry(player)

        player.updateInventory()

        // Update will aptitude
        tryToSpawnNewWill(player)
    }

    fun tryToSpawnNewWill(player: Player) {
        val level = player.find(CatalogPlayerCache.LEVEL) ?: return
        player.manipulate(CatalogPlayerCache.APTITUDE) {
            it.addIfNeeded().forEachIndexed { index, will ->
                SpiritManager.spawn(WillSpirit(WillSpawnReason.AWAKE, player.eyeLocation
                        .clone()
                        .let {
                            it.add(
                                    it.direction.x * 2,
                                    index.toDouble(),
                                    it.direction.z * 2
                            )
                        }, will, player, WillSize.MEDIUM))
                if (index == 0 && level.current == 1) {
                    PlayerMessages.FIRST_OBTAIN_WILL_APTITUDE(will).sendTo(player)
                } else {
                    PlayerMessages.OBTAIN_WILL_APTITUDE(will).sendTo(player)
                }
            }
        }
    }

    @EventHandler
    fun onChangeFoodLevel(event: FoodLevelChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        event.entity as? Player ?: return
        event.isCancelled = true
    }

    @EventHandler
    fun onRegainHealth(event: EntityRegainHealthEvent) {
        event.entity as? Player ?: return
        event.isCancelled = true
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        event.keepInventory = true
        event.keepLevel = true
    }

    @EventHandler
    fun onChangeGameMode(event: PlayerGameModeChangeEvent) {
        when (event.newGameMode) {
            GameMode.SURVIVAL -> {
                val belt = event.player.find(Keys.BELT) ?: return
                belt.getFixedSlot()?.let {
                    event.player.inventory.heldItemSlot = it
                }
                belt.wear(event.player)
                event.player.find(Keys.BAG)?.carry(event.player)
            }
        }
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
        if (Math.abs(tree.location.x - baseBlock.location.x) >= 5
                || Math.abs(tree.location.z - baseBlock.location.z) >= 5) return
        if (tree != baseBlock) {
            tree.world.playEffect(tree.location.central, Effect.STEP_SOUND, tree.type.id)
            tree.type = Material.AIR
        }
        for (face in BlockFace.values().subtract(listOf(BlockFace.SELF, BlockFace.DOWN)))
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, { breakTree(tree.getRelative(face), baseBlock) }, 4L)
    }

}