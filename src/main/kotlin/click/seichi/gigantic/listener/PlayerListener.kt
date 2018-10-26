package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.PlayerAnimations
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.button.buttons.FixedButtons
import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.MineBlockReason
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.event.events.RelationalBlockBreakEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.player.skill.Skills
import click.seichi.gigantic.popup.PlayerPops
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.spirit.SpiritManager
import click.seichi.gigantic.spirit.spawnreason.WillSpawnReason
import click.seichi.gigantic.spirit.spirits.WillSpirit
import click.seichi.gigantic.will.WillSize
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Cow
import org.bukkit.entity.Fish
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

        RaidManager.getBattleList().firstOrNull { it.isJoined(player) }?.drop(player)

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
        }

        player.manipulate(CatalogPlayerCache.HEALTH) {
            it.updateMaxHealth()
            PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
        }
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

        player.getOrPut(Keys.BELT).wear(player)
        player.getOrPut(Keys.BAG).carry(player)
        player.updateInventory()

        PlayerMessages.MEMORY_SIDEBAR(
                player.find(CatalogPlayerCache.MEMORY) ?: return,
                player.find(CatalogPlayerCache.APTITUDE) ?: return,
                true
        ).sendTo(player)
    }

    fun trySendingUnlockMessage(player: Player) {
        Keys.LOCKED_FUNCTION_MAP.forEach { func, key ->
            // すでに解除済みであれば終了
            if (func.isUnlocked(player)) {
                func.unlockAction(player)
                return@forEach
            }
            // 解除可能でなければ終了
            if (!func.canUnlocked(player)) return@forEach
            // 解除処理
            player.transform(key) { hasUnlocked ->
                if (!hasUnlocked) {
                    func.unlockAction(player)
                    func.unlockMessage?.sendTo(player)
                }
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
        val belt = player.getOrPut(Keys.BELT)
        belt.getHotButton(event.newSlot)?.onItemHeld(player, event)
        if (belt.hasFixedSlot() && !belt.isFixed(event.newSlot))
            event.isCancelled = true
    }

    @EventHandler
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        val player = event.player ?: return
        event.isCancelled = true
        Skills.SWITCH.tryInvoke(player)
    }

    @EventHandler
    fun onLevelUp(event: LevelUpEvent) {
        val player = event.player

        PlayerMessages.LEVEL_UP_LEVEL(event.level).sendTo(player)
        PlayerPops.LEVEL_UP.follow(player, meanY = 3.7)
//        PlayerAnimations.LEVEL_UP.follow(player, meanY = 2.0)
        PlayerAnimations.LAUNCH_FIREWORK.start(player.location)
        PlayerSounds.LEVEL_UP.play(player.location)

        trySendingUnlockMessage(player)

        player.manipulate(CatalogPlayerCache.MANA) {
            it.updateMaxMana()
            it.increase(it.max, true)
        }

        player.manipulate(CatalogPlayerCache.HEALTH) {
            val prevMax = it.max
            it.updateMaxHealth()
            it.increase(it.max)
            PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
            if (prevMax == it.max) return@manipulate
            PlayerMessages.LEVEL_UP_HEALTH(prevMax, it.max).sendTo(player)
        }

        player.getOrPut(Keys.BELT).wear(player)
        player.getOrPut(Keys.BAG).carry(player)

        player.updateInventory()

        // Update will aptitude
        tryToSpawnNewWill(player)
    }

    fun tryToSpawnNewWill(player: Player) {
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
                PlayerMessages.OBTAIN_WILL_APTITUDE(will).sendTo(player)
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
        if (event.cause == EntityDamageEvent.DamageCause.STARVATION) {
            event.isCancelled = true
            return
        }
        if (event.cause == EntityDamageEvent.DamageCause.SUICIDE)
            event.damage = Double.MAX_VALUE
    }

    @EventHandler
    fun onRegainHealth(event: EntityRegainHealthEvent) {
        event.entity as? Player ?: return
        if (event.regainReason == EntityRegainHealthEvent.RegainReason.SATIATED) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity ?: return
        event.keepInventory = true
        event.keepLevel = true
        // asSafetyを使うとnullの除外ができないのでこのまま
        player.getOrPut(Keys.DEATH_MESSAGE)?.asSafety(player.wrappedLocale)?.let { deathMessage ->
            event.deathMessage = deathMessage
        }
        player.offer(Keys.DEATH_MESSAGE, null)

        RaidManager.getBattleList().firstOrNull { it.isJoined(player) }?.drop(player)

        player.manipulate(CatalogPlayerCache.LEVEL) { level ->
            player.manipulate(CatalogPlayerCache.MINE_BLOCK) {
                val expToCurrentLevel = PlayerLevelConfig.LEVEL_MAP[level.current] ?: 0L
                val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level.current + 1] ?: 0L
                val maxPenalty = level.exp.minus(expToCurrentLevel)
                val penaltyMineBlock = expToNextLevel.minus(expToCurrentLevel)
                        .div(100L)
                        .times(Config.DEATH_PENALTY.times(100).roundToLong())
                        .coerceAtMost(maxPenalty)
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
        Bukkit.getServer().scheduler.runTaskLater(Gigantic.PLUGIN, {
            player.manipulate(CatalogPlayerCache.HEALTH) {
                it.increase(it.max.div(10.0).times(3.0).toLong())
            }
            PlayerMessages.HEALTH_DISPLAY(player.find(CatalogPlayerCache.HEALTH) ?: return@runTaskLater
            ).sendTo(player)
        }, 1L)

    }

    @EventHandler
    fun onChangeGameMode(event: PlayerGameModeChangeEvent) {
        when (event.newGameMode) {
            GameMode.SURVIVAL -> {
                val belt = event.player.getOrPut(Keys.BELT)
                belt.getFixedSlot()?.let {
                    event.player.inventory.heldItemSlot = it
                }
                belt.wear(event.player)
                event.player.getOrPut(Keys.BAG).carry(event.player)
            }
            else -> {
            }
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event is RelationalBlockBreakEvent) return
        val player = event.player ?: return
        val block = event.block ?: return
        player.offer(Keys.TERRA_DRAIN_SKILL_BLOCK, block)
        Skills.TERRA_DRAIN.tryInvoke(player)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onScoopAnimal(event: PlayerInteractEntityEvent) {
        val entity = event.rightClicked ?: return
        val player = event.player ?: return
        val belt = player.getOrPut(Keys.BELT)
        if (entity !is Cow && entity is Fish) return
        if (belt != Belt.SCOOP) return
        player.inventory.itemInMainHand = FixedButtons.BUCKET.getItemStack(player)

    }

}