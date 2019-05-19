package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.GiganticEvent
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.animation.animations.PlayerAnimations
import click.seichi.gigantic.breaker.Cutter
import click.seichi.gigantic.breaker.Miner
import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.enchantment.ToolEnchantment
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.event.events.RelicGenerateEvent
import click.seichi.gigantic.event.events.SenseEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.GiganticEventMessages
import click.seichi.gigantic.message.messages.LoginMessages
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.sound.sounds.PlayerSounds
import com.google.common.io.ByteStreams
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable
import org.joda.time.DateTime

/**
 * @author tar0ss
 */
class PlayerMonitor : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        if (!PlayerCacheMemory.contains(player.uniqueId)) {
            return
        }

        player.saturation = Float.MAX_VALUE
        player.foodLevel = 20

        // ログインメッセージ送信
        LoginMessages.LOGIN_CHAT.sendTo(player)
        LoginMessages.LOGIN_TITLE.sendTo(player)

        // 期間限定メッセージ送信
        GiganticEvent.trySendLoginMessageTo(player)

        // ここで実績を確認する．これ以前では実績を使ってはいけない
        Achievement.update(player, isForced = true)

        player.updateDisplay(true, true)

        if (Achievement.MANA_STONE.isGranted(player) && player.maxMana > 0.toBigDecimal())
            PlayerMessages.MANA_DISPLAY(player.mana, player.maxMana).sendTo(player)


        // デフォルトのスピードに設定
        player.walkSpeed = player.getOrPut(Keys.WALK_SPEED).toFloat()

        // 安全な位置にテレポート
        if (player.location.block.getRelative(BlockFace.DOWN).isAir) {
            player.location.chunk.getSpawnableLocation().let {
                player.teleportSafely(it)
            }
        }

        // リソースパック更新
        if (player.isNormalTexture)
            player.setResourcePack(Config.RESOURCE_DEFAULT)
        else
            player.setResourcePack(Config.RESOURCE_NO_PARTICLE)

        // レベル表記を更新
        player.setPlayerListName(PlayerMessages.PLAYER_LIST_NAME_PREFIX(player.wrappedLevel).plus(player.name))
        player.setDisplayName(PlayerMessages.DISPLAY_NAME_PREFIX(player.wrappedLevel).plus(player.name))

        // タブリストを更新
        PlayerMessages.LOCATION_INFO(player).sendTo(player)

        // 固定バーを表示
        player.getOrPut(Keys.SUSTAINED_SIDEBAR).tryShow(player)

        // サーバー名を受信
        val out = ByteStreams.newDataOutput().apply {
            writeUTF("GetServer")
        }
        player.sendPluginMessage(Gigantic.PLUGIN, "BungeeCord", out.toByteArray())

        //イベント用コード
        if (GiganticEvent.JMS_KING.isActive() || Gigantic.IS_DEBUG) {
            if (player.wrappedLevel < 20) return
            val givenAt = player.getOrPut(Keys.EVENT_JMS_KING_GIVEN_AT)
            val now = DateTime.now()
            if (now.dayOfYear == givenAt.dayOfYear) return

            object : BukkitRunnable() {
                override fun run() {
                    if (!player.isValid) return
                    player.offer(Keys.EVENT_JMS_KING_GIVEN_AT, now)
                    Relic.CUP_OF_KING.dropTo(player)
                    PlayerSounds.PICK_UP.playOnly(player)
                    GiganticEventMessages.DROPPED_RELIC(Relic.CUP_OF_KING).sendTo(player)
                }
            }.runTaskLater(Gigantic.PLUGIN, 60L)

        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.player.gameMode != GameMode.SURVIVAL) return

        val player = event.player
        val block = event.block

        if (Config.DEBUG_MODE) {
            player.sendMessage("tasks: ${Bukkit.getScheduler().pendingTasks.size}")
        }

        if (block.isLog && ToolEnchantment.CUTTER.has(player)) {
            Cutter().breakRelationalBlock(block, true)
        }

        Miner().onBreakBlock(player, block)

        player.effect.generalBreak(player, block)

        player.offer(Keys.LAST_BREAK_CHUNK, block.chunk)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onLevelUp(event: LevelUpEvent) {
        val player = event.player

        PlayerMessages.LEVEL_UP_LEVEL(event.level).sendTo(player)
        PlayerMessages.LEVEL_UP_TITLE(event.level).sendTo(player)
        PlayerAnimations.LAUNCH_FIREWORK.start(player.location)
        PlayerSounds.LEVEL_UP.play(player.location)

        Achievement.update(event.player)

        player.manipulate(CatalogPlayerCache.MANA) {
            val prevMax = it.max
            it.updateMaxMana(event.level)
            it.increase(it.max, true)
            if (prevMax == it.max) return@manipulate
            if (!Achievement.MANA_STONE.isGranted(player)) return@manipulate
            PlayerMessages.LEVEL_UP_MANA(prevMax, it.max).sendTo(player)
        }

        if (Achievement.MANA_STONE.isGranted(player) && player.maxMana > 0.toBigDecimal())
            PlayerMessages.MANA_DISPLAY(player.mana, player.maxMana).sendTo(player)

        // player list name レベル表記を更新
        player.setPlayerListName(PlayerMessages.PLAYER_LIST_NAME_PREFIX(event.level).plus(player.name))
        player.setDisplayName(PlayerMessages.DISPLAY_NAME_PREFIX(player.wrappedLevel).plus(player.name))
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onSense(event: SenseEvent) {
        event.player.updateWillRelationship()
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onGenerate(event: RelicGenerateEvent) {
        event.player.updateWillRelationship()
    }

    // トーテムに設定
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun tryUseTotem(event: EntityDamageEvent) {
        val player = event.entity as? Player ?: return
        val finalDamage = event.finalDamage
        // 死亡しなければ終了
        if (finalDamage < player.health) return
        // トーテム0個なら終了
        if (player.getOrPut(Keys.TOTEM) <= 0) return
        // トーテムを持っていないまたは最大体力の半分以上のダメージを受けていなければ終了
        if (player.inventory.heldItemSlot != Defaults.TOTEM_SLOT && finalDamage < player.healthScale.div(2.0)) return
        player.transform(Keys.TOTEM) { it.minus(1) }
        val currentSlot = player.inventory.heldItemSlot
        player.inventory.heldItemSlot = Defaults.TOTEM_SLOT
        object : BukkitRunnable() {
            override fun run() {
                if (!player.isValid) return
                player.updateDisplay(applyMainHand = true, applyOffHand = true)
                player.inventory.heldItemSlot = currentSlot
            }
        }.runTaskLater(Gigantic.PLUGIN, 1L)
    }

}