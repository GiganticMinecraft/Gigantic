package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.animation.animations.PlayerAnimations
import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.ExpReason
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.popup.pops.PlayerPops
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.tool.Tool
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockMultiPlaceEvent
import org.bukkit.event.block.BlockPlaceEvent
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

    // プレイヤーデータのロード
    @EventHandler
    fun onPlayerPreLoginAsync(event: AsyncPlayerPreLoginEvent) {
        runBlocking {
            /**
             * 複数サーバで動かすと，ログアウト時の書き込みよりもログイン時の読込の方が早くなってしまい，
             * データが消失するので，ある程度余裕を持って３秒delay．
             * このためだけのcolumn用意するべきかも
             */
            delay(TimeUnit.SECONDS.convert(3L, TimeUnit.MILLISECONDS))
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
        PlayerCacheMemory.writeThenRemoved(uniqueId, true)
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

        player.updateLevel(true)

        player.manipulate(CatalogPlayerCache.MANA) {
            it.updateMaxMana()
        }
        player.find(CatalogPlayerCache.MANA)?.let {
            if (Achievement.MANA_STONE.isGranted(player) && it.max > 0.toBigDecimal())
                PlayerMessages.MANA_DISPLAY(it).sendTo(player)
        }


        player.manipulate(CatalogPlayerCache.HEALTH) {
            it.updateMaxHealth()
        }
        player.find(CatalogPlayerCache.HEALTH)?.let {
            PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
        }

        player.offer(Keys.IS_UPDATE_PROFILE, true)
        player.getOrPut(Keys.BAG).carry(player)

        player.saturation = Float.MAX_VALUE
        player.foodLevel = 20
        // 4秒間無敵付与
        player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
                80,
                5,
                false,
                false
        ))

        Achievement.update(player)
    }

    // プレイヤーのメニュー以外のインベントリーオープンをキャンセル
    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        if (event.player !is Player) return
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
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        val belt = player.getOrPut(Keys.BELT)
        if (event.action == Action.PHYSICAL) return
        val slot = player.inventory.heldItemSlot
        belt.findItem(slot)?.onInteract(player, event)
        belt.offHandItem?.onInteract(player, event)
    }

    @EventHandler
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        val player = event.player ?: return
        event.isCancelled = true
        var current: Tool? = null
        player.manipulate(CatalogPlayerCache.TOOL_SWITCHER) {
            current = it.current
            it.switch()
        }
        val nextTool = player.getOrPut(Keys.TOOL)
        if (current == nextTool) return
        nextTool.update(player)
        PlayerSounds.SWITCH.playOnly(player)
    }

    @EventHandler
    fun onLevelUp(event: LevelUpEvent) {
        val player = event.player

        PlayerMessages.LEVEL_UP_LEVEL(event.level).sendTo(player)
        PlayerMessages.LEVEL_UP_TITLE(event.level).sendTo(player)
        PlayerPops.LEVEL_UP.follow(player, meanY = 3.7)
        PlayerAnimations.LAUNCH_FIREWORK.start(player.location)
        PlayerSounds.LEVEL_UP.play(player.location)

        Achievement.update(player)

        player.manipulate(CatalogPlayerCache.MANA) {
            val prevMax = it.max
            it.updateMaxMana()
            it.increase(it.max, true)
            if (prevMax == it.max) return@manipulate
            if (!Achievement.MANA_STONE.isGranted(player)) return@manipulate
            PlayerMessages.LEVEL_UP_MANA(prevMax, it.max).sendTo(player)
        }

        player.manipulate(CatalogPlayerCache.HEALTH) {
            val prevMax = it.max
            it.updateMaxHealth()
            it.increase(it.max)
            if (prevMax == it.max) return@manipulate
            PlayerMessages.LEVEL_UP_HEALTH(prevMax, it.max).sendTo(player)
        }

        player.find(CatalogPlayerCache.MANA)?.let {
            if (Achievement.MANA_STONE.isGranted(player) && it.max > 0.toBigDecimal())
                PlayerMessages.MANA_DISPLAY(it).sendTo(player)
        }
        player.find(CatalogPlayerCache.HEALTH)?.let {
            PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
        }

        player.offer(Keys.IS_UPDATE_PROFILE, true)
        player.getOrPut(Keys.BAG).carry(player)

    }

    @EventHandler
    fun onChangeFoodLevel(event: FoodLevelChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.entity !is Player) return
        if (event.cause == EntityDamageEvent.DamageCause.STARVATION) {
            event.isCancelled = true
            return
        }
        if (event.cause == EntityDamageEvent.DamageCause.SUICIDE)
            event.damage = Double.MAX_VALUE
    }

    @EventHandler
    fun onRegainHealth(event: EntityRegainHealthEvent) {
        if (event.entity !is Player) return
        if (event.regainReason == EntityRegainHealthEvent.RegainReason.SATIATED) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity ?: return
        event.keepInventory = true
        event.keepLevel = true
        player.offer(Keys.LAST_DEATH_CHUNK, player.location.chunk)
        player.getOrPut(Keys.DEATH_MESSAGE)?.asSafety(player.wrappedLocale)?.let { deathMessage ->
            event.deathMessage = "${ChatColor.RED}" + deathMessage
        }
        player.offer(Keys.DEATH_MESSAGE, null)

        player.manipulate(CatalogPlayerCache.LEVEL) { level ->
            player.manipulate(CatalogPlayerCache.EXP) {
                val expToCurrentLevel = PlayerLevelConfig.LEVEL_MAP[level.current] ?: 0L
                val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level.current + 1] ?: 0L
                val maxPenalty = level.exp.minus(expToCurrentLevel)
                val penaltyMineBlock = expToNextLevel.minus(expToCurrentLevel)
                        .div(100L)
                        .times(Config.PLAYER_DEATH_PENALTY.times(100).roundToLong())
                        .coerceAtMost(maxPenalty)
                it.add(penaltyMineBlock.times(-1L), ExpReason.DEATH_PENALTY)
                if (penaltyMineBlock != 0L)
                    PlayerMessages.DEATH_PENALTY(penaltyMineBlock).sendTo(player)
            }
        }

        player.updateLevel()
    }

    @EventHandler
    fun onReSpawn(event: PlayerRespawnEvent) {
        val player = event.player ?: return
        Bukkit.getServer().scheduler.runTaskLater(Gigantic.PLUGIN, {
            if (!player.isValid) return@runTaskLater
            player.manipulate(CatalogPlayerCache.HEALTH) {
                it.increase(it.max.div(10.0).times(3.0).toLong())
            }
            PlayerMessages.HEALTH_DISPLAY(player.find(CatalogPlayerCache.HEALTH) ?: return@runTaskLater
            ).sendTo(player)

            player.offer(Keys.IS_UPDATE_PROFILE, true)
            player.getOrPut(Keys.BAG).carry(player)
        }, 1L)

    }

    @EventHandler
    fun onChangeGameMode(event: PlayerGameModeChangeEvent) {
        when (event.newGameMode) {
            GameMode.SURVIVAL -> {
                event.player.updateInventory(true, true)
            }
            GameMode.CREATIVE -> {
                event.player.inventory.clear()
            }
            else -> {
            }
        }
    }

    @EventHandler
    fun onPlaceBlock(event: BlockPlaceEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        event.isCancelled = true
    }

    @EventHandler
    fun onMultiPlaceBlock(event: BlockMultiPlaceEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        event.isCancelled = true
    }

    // スポーン付近を破壊した場合問答無用でキャンセル
    @EventHandler(priority = EventPriority.HIGHEST)
    fun cancelSpawnArea(event: BlockBreakEvent) {
        val player = event.player ?: return
        val block = event.block ?: return
        if (player.gameMode == GameMode.CREATIVE) return
        if (!block.isSpawnArea) return
        PlayerMessages.SPAWN_PROTECT.sendTo(player)
        event.isCancelled = true
    }

    // ツール以外で破壊したときキャンセル
    @EventHandler
    fun cancelNotToolBreaking(event: BlockBreakEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        val slot = player.inventory.heldItemSlot
        val belt = player.getOrPut(Keys.BELT)
        if (slot == belt.toolSlot) return
        PlayerMessages.BREAK_NOT_TOOL.sendTo(player)
        event.isCancelled = true
    }

    // ツール以外で破壊したときキャンセル
    @EventHandler
    fun cancelNotSneakingUnderBlockBreak(event: BlockBreakEvent) {
        val player = event.player ?: return
        val block = event.block ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        if (!block.isUnder(player)) return
        if (player.isSneaking) return
        PlayerMessages.BREAK_UNDER_BLOCK_NOT_SNEAKING.sendTo(player)
        event.isCancelled = true
    }

}