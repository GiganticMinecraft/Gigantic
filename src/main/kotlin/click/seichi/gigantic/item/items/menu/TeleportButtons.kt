package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.TeleportMenu
import click.seichi.gigantic.menu.menus.TeleportToPlayerMenu
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.util.Random
import org.bukkit.*
import org.bukkit.block.Biome
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object TeleportButtons {

    val TELEPORT_TO_PLAYER = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.TELEPORT_PLAYER.isGranted(player)) return null
            return ItemStack(Material.PLAYER_HEAD).apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_PLAYER.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === TeleportToPlayerMenu) return
            if (!Achievement.TELEPORT_PLAYER.isGranted(player)) return
            TeleportToPlayerMenu.open(player)
        }

    }

    val TELEPORT_TO_RANDOM_CHUNK = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.CHORUS_FRUIT).apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_RANDOM_CHUNK.asSafety(player.wrappedLocale))
            }
        }

        private val oceanBiomeSet = setOf(
                Biome.OCEAN,
                Biome.COLD_OCEAN,
                Biome.DEEP_COLD_OCEAN,
                Biome.DEEP_FROZEN_OCEAN,
                Biome.DEEP_LUKEWARM_OCEAN,
                Biome.DEEP_OCEAN,
                Biome.DEEP_WARM_OCEAN,
                Biome.FROZEN_OCEAN,
                Biome.LUKEWARM_OCEAN,
                Biome.WARM_OCEAN
        )

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (player.gameMode != GameMode.SURVIVAL) {
                player.sendMessage(TeleportMessages.IN_BREAK_TIME.asSafety(player.wrappedLocale))
                return
            }
            var chunk: Chunk? = null
            var location: Location? = null
            var count = 0
            // 適用可能か ダメならfalse
            var isValid = false
            while (!isValid && count++ < 20) {
                chunk = randomChunk(player)
                location = chunk.getSpawnableLocation()
                isValid = chunk.let {
                    !(it.isBattled ||
                            it.isSpawnArea ||
                            oceanBiomeSet.contains(it.getBlock(0, 0, 0).biome) ||
                            location.block.getRelative(BlockFace.DOWN).type == Material.BEDROCK)
                }
            }
            if (chunk == null) return
            if (!chunk.isLoaded) {
                if (chunk.load(true)) return
            }
            player.teleport(location!!)
            PlayerSounds.TELEPORT.play(location)
        }

        private fun randomChunk(player: Player): Chunk {
            val radius = Config.WORLD_SIDE_LENGTH.div(16).div(2).toInt()
            return player.world.getChunkAt(Random.nextInt(-radius, radius), Random.nextInt(-radius, radius))
        }

    }

    val TELEPORT_TO_DEATH_CHUNK = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.TELEPORT_LAST_DEATH.isGranted(player)) return null
            player.getOrPut(Keys.LAST_DEATH_CHUNK) ?: return null
            return ItemStack(Material.BONE).apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_LAST_DEATH.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (!Achievement.TELEPORT_LAST_DEATH.isGranted(player)) return
            if (player.gameMode != GameMode.SURVIVAL) {
                player.sendMessage(TeleportMessages.IN_BREAK_TIME.asSafety(player.wrappedLocale))
                return
            }
            val chunk = player.getOrPut(Keys.LAST_DEATH_CHUNK) ?: return
            chunk.load(true)
            val location = chunk.getSpawnableLocation()
            player.teleport(location)
            PlayerSounds.TELEPORT.play(location)
            player.offer(Keys.LAST_DEATH_CHUNK, null)
        }

    }

    val TELEPORT_TOGGLE = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.TELEPORT_PLAYER.isGranted(player)) return null
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
            if (!Achievement.TELEPORT_PLAYER.isGranted(player)) return
            player.transform(Keys.TELEPORT_TOGGLE) { !it }
            PlayerSounds.TOGGLE.playOnly(player)
            TeleportMenu.reopen(player)
        }

    }

    val TELEPORT_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
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