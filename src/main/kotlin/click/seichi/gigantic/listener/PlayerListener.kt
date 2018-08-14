package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.PlayerAnimations
import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.central
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.popup.PlayerPops
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.skill.Skills
import click.seichi.gigantic.sound.sounds.PlayerSounds
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
import kotlin.math.roundToLong


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

        RaidManager.getBattleList().firstOrNull { it.isJoined(player) }?.left(player)

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

        player.manipulate(CatalogPlayerCache.LEVEL) { level ->
            level.calculate(ExpProducer.calcExp(player)) {}
            PlayerMessages.EXP_BAR_DISPLAY(level).sendTo(player)
        }

        player.manipulate(CatalogPlayerCache.MANA) {
            it.updateMaxMana()
            it.createBar()
            if (LockedFunction.MANA.isUnlocked(player))
                it.display()
        }

        player.manipulate(CatalogPlayerCache.HEALTH) {
            it.updateMaxHealth()
            PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
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
                player.find(CatalogPlayerCache.APTITUDE) ?: return,
                true
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
        Skills.SWAP.tryInvoke(player)
    }

    @EventHandler
    fun onLevelUp(event: LevelUpEvent) {
        val player = event.player

        PlayerMessages.LEVEL_UP_LEVEL(event.level).sendTo(player)
        PlayerPops.LEVEL_UP.follow(player, meanY = 3.7)
        PlayerAnimations.LEVEL_UP.follow(player, meanY = 2.0)
        PlayerAnimations.LAUNCH_FIREWORK.start(player.location)
        PlayerSounds.LEVEL_UP.play(player.location)

        player.manipulate(CatalogPlayerCache.MANA) {
            val prevMax = it.max
            it.updateMaxMana()
            it.increase(it.max, true)
            if (prevMax == it.max) return@manipulate
            if (LockedFunction.MANA.isUnlocked(player)) {
                it.display()
                PlayerMessages.LEVEL_UP_MANA(prevMax, it.max).sendTo(player)
            }
        }

        player.manipulate(CatalogPlayerCache.HEALTH) {
            val prevMax = it.max
            it.updateMaxHealth()
            it.increase(it.max)
            PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
            if (prevMax == it.max) return@manipulate
            PlayerMessages.LEVEL_UP_HEALTH(prevMax, it.max).sendTo(player)
        }

        player.find(Keys.BELT)?.wear(player)
        player.find(Keys.BAG)?.carry(player)

        player.updateInventory()

        trySendingUnlockMessage(player)

        // Update will aptitude
        tryToSpawnNewWill(player)
    }

    fun tryToSpawnNewWill(player: Player) {
        val level = player.find(CatalogPlayerCache.LEVEL) ?: return
        player.manipulate(CatalogPlayerCache.APTITUDE) { willAptitude ->
            willAptitude.addIfNeeded().forEachIndexed { index, will ->
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
        val player = event.entity as? Player ?: return
        if (event.cause == EntityDamageEvent.DamageCause.STARVATION) {
            event.isCancelled = true
            return
        }
        // 5 times damage
        var wrappedDamage = event.finalDamage.times(5).roundToLong()
        event.damage = 0.0
        player.manipulate(CatalogPlayerCache.HEALTH) {

            if (event.cause == EntityDamageEvent.DamageCause.SUICIDE)
                wrappedDamage = Long.MAX_VALUE

            it.decrease(wrappedDamage)
            // 遅延なしだと２回死んでしまう(体力が0.0になったあとにダメージを受けるため)
            // ダメージを受けない（イベントをキャンセル）すればいいのだが、そうすると、各ダメージごとのEntityEffectが表示されない
            Bukkit.getScheduler().runTaskLater(
                    Gigantic.PLUGIN,
                    { PlayerMessages.HEALTH_DISPLAY(it).sendTo(player) },
                    1
            )

        }
    }

    @EventHandler
    fun onRegainHealth(event: EntityRegainHealthEvent) {
        val player = event.entity as? Player ?: return
        if (event.regainReason == EntityRegainHealthEvent.RegainReason.SATIATED) {
            event.isCancelled = true
            return
        }
        player.manipulate(CatalogPlayerCache.HEALTH) {
            // 5 times regain
            val wrappedRegain = event.amount.times(5).roundToLong()
            it.increase(wrappedRegain)
            PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
        }
        event.amount = 0.0
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity ?: return
        event.keepInventory = true
        event.keepLevel = true
        player.manipulate(CatalogPlayerCache.LEVEL) { level ->
            player.manipulate(CatalogPlayerCache.MINE_BLOCK) {
                // 7 percent
                val expToCurrentLevel = PlayerLevelConfig.LEVEL_MAP[level.current] ?: 0L
                val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level.current + 1] ?: 0L
                val maxPenalty = level.exp.minus(expToCurrentLevel)
                val penaltyMineBlock = expToNextLevel.minus(expToCurrentLevel).div(100L).times(7L).coerceAtMost(maxPenalty)
                it.add(penaltyMineBlock, MineBlockReason.DEATH_PENALTY)
                if (penaltyMineBlock != 0L)
                    PlayerMessages.DEATH_PENALTY(penaltyMineBlock).sendTo(player)
            }
            level.calculate(ExpProducer.calcExp(player)) {}
            PlayerMessages.EXP_BAR_DISPLAY(level).sendTo(player)
        }
    }

    @EventHandler
    fun onReSpawn(event: PlayerRespawnEvent) {
        val player = event.player ?: return
        player.manipulate(CatalogPlayerCache.HEALTH) {
            // 30 percent
            it.increase(it.max.div(10.0).times(3.0).toLong())
            // 遅延させないと反映されないため
            Bukkit.getScheduler().runTaskLater(
                    Gigantic.PLUGIN,
                    { PlayerMessages.HEALTH_DISPLAY(it).sendTo(player) },
                    1
            )
        }
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
            else -> {
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