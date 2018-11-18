package click.seichi.gigantic.button.buttons.menu

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.menus.TeleportMenu
import click.seichi.gigantic.menu.menus.TeleportToPlayerMenu
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.util.Random
import org.bukkit.ChatColor
import org.bukkit.Chunk
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object TeleportButtons {

    val TELEPORT_TO_PLAYER = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!Achievement.TELEPORT.isGranted(player)) return null
            return ItemStack(Material.PLAYER_HEAD).apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_PLAYER.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === TeleportToPlayerMenu) return
            if (!Achievement.TELEPORT.isGranted(player)) return
            TeleportToPlayerMenu.open(player)
        }

    }

    val TELEPORT_TO_RANDOM_CHUNK = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.CHORUS_FRUIT).apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_RANDOM_CHUNK.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            var chunk: Chunk? = null
            var count = 0
            while (chunk == null && count++ < 20) {
                chunk = randomChunk(player).let {
                    if (it.isBattled || it.isSpawnArea) null else it
                }
            }
            if (chunk == null) return
            if (!chunk.isLoaded) {
                if (chunk.load(true)) return
            }
            val location = chunk.getSpawnableLocation()
            player.teleport(location)
            PlayerSounds.TELEPORT.play(location)
        }

        private fun randomChunk(player: Player): Chunk {
            val radius = Config.WORLD_SIDE_LENGTH.div(16).div(2).toInt()
            return player.world.getChunkAt(Random.nextInt(-radius, radius), Random.nextInt(-radius, radius))
        }

    }

    val TELEPORT_TO_BATTLE_CHUNK = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!Achievement.TELEPORT.isGranted(player)) return null
            if (!Achievement.QUEST.isGranted(player)) return null
            val lastBattle = player.getOrPut(Keys.LAST_BATTLE) ?: return null
            return lastBattle.monster.getIcon().apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_BATTLE_CHUNK.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (!Achievement.TELEPORT.isGranted(player)) return
            if (!Achievement.QUEST.isGranted(player)) return
            val lastBattle = player.getOrPut(Keys.LAST_BATTLE) ?: return
            val chunk = lastBattle.chunk
            chunk.load(true)
            val location = chunk.getSpawnableLocation()
            player.teleport(location)
            PlayerSounds.TELEPORT.play(location)

        }

    }

    val TELEPORT_TOGGLE = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!Achievement.TELEPORT.isGranted(player)) return null
            val toggle = player.getOrPut(Keys.TELEPORT_TOGGLE)
            return ItemStack(Material.DAYLIGHT_DETECTOR).apply {
                if (toggle)
                    setDisplayName(TeleportMessages.TELEPORT_TOGGLE_ON.asSafety(player.wrappedLocale))
                else
                    setDisplayName(TeleportMessages.TELEPORT_TOGGLE_OFF.asSafety(player.wrappedLocale))

                setLore(*TeleportMessages.TELEPORT_TOGGLE_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())

            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (!Achievement.TELEPORT.isGranted(player)) return
            player.transform(Keys.TELEPORT_TOGGLE) { !it }
            PlayerSounds.TOGGLE.playOnly(player)
            TeleportMenu.reopen(player)
        }

    }

    val TELEPORT_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                return when {
                    !to.isValid -> ItemStack(Material.BLACK_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_INVALID_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    !to.getOrPut(Keys.TELEPORT_TOGGLE) -> ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_TOGGLE_OFF_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    to.gameMode == GameMode.SPECTATOR -> ItemStack(Material.YELLOW_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_AFK_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    to.gameMode != GameMode.SURVIVAL -> ItemStack(Material.BROWN_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_NOT_SURVIVAL_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    to.world != player.world -> ItemStack(Material.CYAN_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_INVALID_WORLD_LORE(to.world)
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    to.isFlying -> ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_FLYING_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    else -> to.getHead().apply {
                        setDisplayName("${ChatColor.GREEN}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_LORE(player)
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                if (!to.isValid) return
                if (!to.getOrPut(Keys.TELEPORT_TOGGLE)) return
                if (to.gameMode != GameMode.SURVIVAL) return
                if (to.world != player.world) return
                if (to.isFlying) return
                player.teleport(to)
                PlayerSounds.TELEPORT.play(to.location)
            }

        }
    }
}