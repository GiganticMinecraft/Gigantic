package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.event.events.ComboEvent
import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.DeathMessages
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.player.ExpReason
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.player.spell.spells.SkyWalk
import click.seichi.gigantic.sound.sounds.PlayerSounds
import kotlinx.coroutines.runBlocking
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockMultiPlaceEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.*
import java.math.BigDecimal
import java.math.BigInteger


/**
 * @author tar0ss
 */
class PlayerListener : Listener {

    // プレイヤーデータのロード
    @EventHandler
    fun onPlayerPreLoginAsync(event: AsyncPlayerPreLoginEvent) {
        // データロード完了までログインをブロック
        runBlocking {
            /**
             * 複数サーバで動かすと，ログアウト時の書き込みよりもログイン時の読込の方が早くなってしまい，
             * データが消失するので，1秒間隔で読み込み可能かチェックする．
             * [Config.LOAD_TIME]秒で強制的に読み込む
             */
            PlayerCacheMemory.add(event.uniqueId, event.name)
        }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        if (!PlayerCacheMemory.contains(player.uniqueId)) {
            return
        }
        // 全ての設置ブロックを削除
        player.getOrPut(Keys.SPELL_SKY_WALK_PLACE_BLOCKS).apply {
            forEach { block ->
                SkyWalk.revert(block)
            }
            Gigantic.USE_BLOCK_SET.removeAll(this)
        }

        if (player.gameMode == GameMode.SPECTATOR) {
            player.getOrPut(Keys.AFK_LOCATION)?.let {
                player.teleportSafely(it)
            }
            player.gameMode = GameMode.SURVIVAL
        }
        val uniqueId = player.uniqueId

        runTaskAsync {
            PlayerCacheMemory.writeThenRemoved(uniqueId)
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        /**
         * この処理がないと、cacheがロードされずに参加できてしまう
         */
        if (!PlayerCacheMemory.contains(player.uniqueId)) {
            player.kickPlayer("Server is starting...Please wait a few seconds.")
            return
        }

        if (!player.isOp) player.gameMode = GameMode.SURVIVAL

        // 必要であればホームにテレポートさせる
        player.getOrPut(Keys.HOME_MAP).map { it.value }
                .firstOrNull { it.teleportOnSwitch && it.isValid }
                ?.let {
                    it.teleportOnSwitch = false
                    player.teleportSafely(it.location)
                    PlayerSounds.TELEPORT.play(it.location)
                }


        player.updateLevel(true)

        player.manipulate(CatalogPlayerCache.MANA) {
            it.updateMaxMana(player.level)
        }

        player.updateWillRelationship(true)
    }

    /**
     * プレイヤーのメニュー以外のインベントリーオープンをキャンセル
     * プレイヤーインベントリのオープンは検知されない．あれはクライアントサイドのみ
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onInventoryOpen(event: InventoryOpenEvent) {
        if (event.player !is Player) return
        if (event.player.gameMode != GameMode.SURVIVAL) return
        if (event.inventory.holder is Menu) return
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerArmorStandManipulate(event: PlayerArmorStandManipulateEvent) {
        if (event.player.gameMode != GameMode.SURVIVAL) return
        event.isCancelled = true
    }

    // プレイヤーの全てのドロップをキャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onDropItem(event: PlayerDropItemEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerItemConsume(event: PlayerItemConsumeEvent) {
        if (event.item.type == Material.FIREWORK_ROCKET) return
        if (event.player.gameMode != GameMode.SURVIVAL) return
        event.isCancelled = true
    }

    @EventHandler
    fun onCombo(event: ComboEvent) {
        event.player.updateTool()
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onChangeFoodLevel(event: FoodLevelChangeEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onDamage(event: EntityDamageEvent) {
        if (event.entity !is Player) return
        if (event.cause == EntityDamageEvent.DamageCause.STARVATION) {
            event.isCancelled = true
            return
        }
        if (event.cause == EntityDamageEvent.DamageCause.SUICIDE)
            event.damage = Double.MAX_VALUE
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onRegainHealth(event: EntityRegainHealthEvent) {
        if (event.entity !is Player) return
        if (event.regainReason == EntityRegainHealthEvent.RegainReason.SATIATED) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity
        event.deathMessage = null
        event.keepInventory = true
        event.keepLevel = true
        player.offer(Keys.LAST_DEATH_CHUNK, player.location.chunk)
        if (Achievement.TELEPORT_LAST_DEATH.isGranted(player)) {
            DeathMessages.DEATH_TELEPORT.sendTo(player)
        }


        player.manipulate(CatalogPlayerCache.LEVEL) { level ->
            player.manipulate(CatalogPlayerCache.EXP) {
                val expToCurrentLevel = PlayerLevelConfig.LEVEL_MAP[level.current] ?: BigDecimal.ZERO
                val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level.current + 1] ?: BigDecimal.ZERO
                val maxPenalty = player.wrappedExp.minus(expToCurrentLevel)

                val penaltyMineBlock = expToNextLevel.minus(expToCurrentLevel)
                        .divide(100.toBigDecimal())
                        .times(Config.PLAYER_DEATH_PENALTY.toBigDecimal())
                        .coerceAtMost(maxPenalty)

                it.add(penaltyMineBlock.times((-1).toBigDecimal()), ExpReason.DEATH_PENALTY)
                if (penaltyMineBlock.toBigInteger() != BigInteger.ZERO)
                    PlayerMessages.DEATH_PENALTY(penaltyMineBlock).sendTo(player)
            }
        }



        player.updateLevel()
        player.updateDisplay(true, true)
    }

    @EventHandler
    fun onReSpawn(event: PlayerRespawnEvent) {
        val player = event.player


        Bukkit.getScheduler().scheduleSyncDelayedTask(Gigantic.PLUGIN, {
            if (!player.isValid) return@scheduleSyncDelayedTask

            // HPを3割にする
            player.health = 6.0
            player.updateDisplay(true, true)

            if (Achievement.MANA_STONE.isGranted(player) && player.maxMana > 0.toBigDecimal()) {
                // マナをゼロにする
                player.manipulate(CatalogPlayerCache.MANA) {
                    it.set(BigDecimal.ZERO)
                }
                PlayerMessages.MANA_DISPLAY(player.mana, player.maxMana).sendTo(player)
            } else {
                player.foodLevel = 20
            }
        }, 1L)

    }

    @EventHandler
    fun onChangeGameMode(event: PlayerGameModeChangeEvent) {
        when (event.newGameMode) {
            GameMode.SURVIVAL -> {
                event.player.updateDisplay(true, true)
            }
            GameMode.CREATIVE -> {
                event.player.inventory.clear()
            }
            else -> {
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlaceBlock(event: BlockPlaceEvent) {
        val player = event.player
        val block = event.block
        if (player.gameMode != GameMode.SURVIVAL) return
        if (block.type == Material.TORCH) return
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onMultiPlaceBlock(event: BlockMultiPlaceEvent) {
        val player = event.player
        if (player.gameMode != GameMode.SURVIVAL) return
        event.isCancelled = true
    }

    // スポーン付近を破壊した場合問答無用でキャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun cancelSpawnArea(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        if (player.gameMode != GameMode.SURVIVAL) return
        if (!block.isSpawnArea) return
        PlayerMessages.SPAWN_PROTECT.sendTo(player)
        event.isCancelled = true
    }

    // ツール以外で破壊したときキャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun cancelNotToolBreaking(event: BlockBreakEvent) {
        val player = event.player
        if (player.gameMode != GameMode.SURVIVAL) return
        val slot = player.inventory.heldItemSlot
        val belt = player.getOrPut(Keys.BELT)
        if (slot == belt.toolSlot) return
        PlayerMessages.BREAK_NOT_TOOL.sendTo(player)
        event.isCancelled = true
    }

    // ツール以外で破壊したときキャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun cancelNotSneakingUnderBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        if (player.gameMode != GameMode.SURVIVAL) return
        if (!block.isUnder(player)) return
        if (player.isSneaking) return
        event.isCancelled = true
        if (!ToggleSetting.UNDER_PLAYER.getToggle(player)) return
        PlayerMessages.BREAK_UNDER_BLOCK_NOT_SNEAKING.sendTo(player)
    }

    // スキルで破壊中のブロックを破壊したときキャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun cancelBrokenBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        if (player.gameMode != GameMode.SURVIVAL) return
        if (!Gigantic.USE_BLOCK_SET.contains(block)) return
        event.isCancelled = true
    }

    // プレイヤーの近くを破壊したときキャンセル,ただし相互フォローなら良し
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun cancelNearAnotherPlayer(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        if (player.gameMode != GameMode.SURVIVAL) return
        block.firstOrNullOfNearPlayer(player) ?: return
        PlayerMessages.NOT_BREAK_NEAR_ANOTHER_PLAYER.sendTo(player)
        event.isCancelled = true
    }

    // 重力が１を超えた時キャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun cancelOverGravity(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        if (player.gameMode != GameMode.SURVIVAL) return
        if (block.calcCrustGravity() <= Config.MAX_BREAKABLE_GRAVITY) return
        PlayerMessages.NOT_BREAK_OVER_GRAVITY.sendTo(player)
        event.isCancelled = true
    }

    // ｙ座標が0ならキャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun cancelFloorBlock(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        if (player.gameMode != GameMode.SURVIVAL) return
        if (block.y != 0) return
        PlayerMessages.FLOOR_BLOCK.sendTo(player)
        event.isCancelled = true
    }

    @EventHandler
    fun updatePlayerListHeaderFooter(event: TickEvent) {
        if (event.ticks % Defaults.PLAYERLIST_UPDATE_INTERVAL != 0L) return
        Bukkit.getServer().onlinePlayers
                .asSequence()
                .filterNotNull()
                .filter { it.isValid }
                .forEach { player ->
                    PlayerMessages.LOCATION_INFO(player).sendTo(player)
                }
    }

}