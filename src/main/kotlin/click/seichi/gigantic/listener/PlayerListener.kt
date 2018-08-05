package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.bag.bags.MainBag
import click.seichi.gigantic.belt.belts.CutBelt
import click.seichi.gigantic.belt.belts.DigBelt
import click.seichi.gigantic.belt.belts.MineBelt
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.config.PlayerLevelConfig.MAX
import click.seichi.gigantic.data.PlayerCacheMemory
import click.seichi.gigantic.data.keys.Keys
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.central
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
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
        if (player.gameMode == GameMode.SPECTATOR) {
            player.teleport(MainBag.lastLocationMap.remove(player.uniqueId) ?: return)
            player.gameMode = GameMode.SURVIVAL
        }
        val uniqueId = player.uniqueId
        PlayerCacheMemory.remove(uniqueId, true)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player ?: return
        if (!player.isOp) player.gameMode = GameMode.SURVIVAL
        player.offer(Keys.EXP, ExpProducer.calcExp(player))

        // level計算
        val exp = player.find(Keys.EXP) ?: 0L
        player.transform(Keys.LEVEL) { current ->
            var level = current
            while (exp >= PlayerLevelConfig.LEVEL_MAP[level + 1] ?: Long.MAX_VALUE) {
                if (level + 1 > MAX) break
                level++
            }
            level
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

        // display
        PlayerMessages.EXP_BAR_DISPLAY(
                player.find(Keys.LEVEL) ?: 0,
                player.find(Keys.EXP) ?: 0L
        ).sendTo(player)
//        PlayerMessages.MEMORY_SIDEBAR(player.find(Keys.MEMORY_MAP[]))
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
        if (!belt.isFixed(event.newSlot)) {
            event.isCancelled
        }
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
            is MineBelt -> DigBelt
            is DigBelt -> CutBelt
            else -> MineBelt
        })
    }

    @EventHandler
    fun onLevelUp(event: LevelUpEvent) {

//        val gPlayer = event.player.gPlayer ?: return
//
//        // Update unlock function
//        LockedFunction.values()
//                .firstOrNull { gPlayer.level.current == it.unlockLevel }
//                ?.unlockMessage?.sendTo(event.player)
//
//        // Update player mana
//        gPlayer.mana.run {
//            updateMaxMana(gPlayer.level)
//            increase(max, true)
//        }
//
//        // Displays
//        if (LockedFunction.MANA.isUnlocked(gPlayer)) {
//            val title = PlayerMessages.MANA_BAR_TITLE(gPlayer.mana).asSafety(gPlayer.locale)
//            PlayerBars.MANA(gPlayer.mana, title).show(gPlayer.manaBar)
//        }
//
//        // Update player Belt
//        gPlayer.belt.carry(event.player)
//
//        // Update player inventory
//        gPlayer.defaultInventory.carry(event.player)
//
//        // Update will aptitude
//        val newWillList = gPlayer.aptitude.addIfNeeded(gPlayer.level).toMutableList()
//        if (newWillList.isEmpty()) return
//
//        // Spawn will that added to player
//        newWillList.forEachIndexed { index, will ->
//            SpiritManager.spawn(WillSpirit(WillSpawnReason.AWAKE, event.player.eyeLocation
//                    .clone()
//                    .let {
//                        it.add(
//                                it.direction.x * 2,
//                                index.toDouble(),
//                                it.direction.z * 2
//                        )
//                    }, will, event.player, WillSize.MEDIUM))
//        }
//
//        // Will messages
//        if (gPlayer.level.current == 1) {
//            PlayerMessages.FIRST_OBTAIN_WILL_APTITUDE(newWillList.removeAt(0)).sendTo(event.player)
//            newWillList.forEach {
//                PlayerMessages.OBTAIN_WILL_APTITUDE(it).sendTo(event.player)
//            }
//        } else {
//            newWillList.forEach {
//                PlayerMessages.OBTAIN_WILL_APTITUDE(it).sendTo(event.player)
//            }
//        }
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
//        when (event.newGameMode) {
//            GameMode.SURVIVAL -> {
//                val player = event.player ?: return
//                val gPlayer = player.gPlayer ?: return
//                player.inventory.heldItemSlot = Belt.TOOL_SLOT
//                gPlayer.belt.carry(player)
//                gPlayer.defaultInventory.carry(player)
//            }
//        }
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