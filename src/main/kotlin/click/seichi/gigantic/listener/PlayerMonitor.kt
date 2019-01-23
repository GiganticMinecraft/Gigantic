package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.animation.animations.PlayerAnimations
import click.seichi.gigantic.breaker.Cutter
import click.seichi.gigantic.breaker.Miner
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.enchantment.ToolEnchantment
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.event.events.RelicGenerateEvent
import click.seichi.gigantic.event.events.SenseEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.popup.pops.PlayerPops
import click.seichi.gigantic.sound.sounds.PlayerSounds
import com.google.common.io.ByteStreams
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

/**
 * @author tar0ss
 */
class PlayerMonitor : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player ?: return

        // ここで実績を確認する．これ以前では実績を使ってはいけない
        Achievement.update(player, isForced = true)

        player.updateDisplay(true, true)

        if (Achievement.MANA_STONE.isGranted(player) && player.maxMana > 0.toBigDecimal())
            PlayerMessages.MANA_DISPLAY(player.mana, player.maxMana).sendTo(player)

        player.saturation = Float.MAX_VALUE
        player.foodLevel = 20
        // 4秒間無敵付与
        player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,
                80,
                5,
                false,
                false
        ))
        // レベル表記を更新
        player.playerListName = PlayerMessages.PLAYER_LIST_NAME_PREFIX(player.wrappedLevel).plus(player.name)
        player.displayName = PlayerMessages.DISPLAY_NAME_PREFIX(player.wrappedLevel).plus(player.name)
        player.playerListHeader = PlayerMessages.PLAYER_LIST_HEADER.asSafety(player.wrappedLocale)
        player.playerListFooter = PlayerMessages.PLAYER_LIST_FOOTER.asSafety(player.wrappedLocale)

        // サーバー名を受信
        val out = ByteStreams.newDataOutput().apply {
            writeUTF("GetServer")
        }
        player.sendPluginMessage(Gigantic.PLUGIN, "BungeeCord", out.toByteArray())
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.player.gameMode != GameMode.SURVIVAL) return

        val player = event.player ?: return
        val block = event.block ?: return

        if (Config.DEBUG_MODE)
            player.sendMessage("tasks: ${Bukkit.getScheduler().pendingTasks.size}")


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

        Achievement.update(event.player)

        PlayerMessages.LEVEL_UP_LEVEL(event.level).sendTo(player)
        PlayerMessages.LEVEL_UP_TITLE(event.level).sendTo(player)
        PlayerPops.LEVEL_UP.follow(player, meanY = 3.7)
        PlayerAnimations.LAUNCH_FIREWORK.start(player.location)
        PlayerSounds.LEVEL_UP.play(player.location)

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
        player.playerListName = PlayerMessages.PLAYER_LIST_NAME_PREFIX(event.level).plus(player.name)
        player.displayName = PlayerMessages.DISPLAY_NAME_PREFIX(player.wrappedLevel).plus(player.name)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onSense(event: SenseEvent) {
        event.player.updateWillRelationship()
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onGenerate(event: RelicGenerateEvent) {
        event.player.updateWillRelationship()
    }

}