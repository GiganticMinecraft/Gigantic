package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.TeleportMenu
import click.seichi.gigantic.menu.menus.TeleportToHomeMenu
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

    private val deathMaterialSet = setOf(
            Material.LAVA
    )

    val TELEPORT_TO_PLAYER = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.TELEPORT_PLAYER.isGranted(player)) return null
            return ItemStack(Material.PLAYER_HEAD).apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_PLAYER.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === TeleportToPlayerMenu) return false
            if (!Achievement.TELEPORT_PLAYER.isGranted(player)) return false
            TeleportToPlayerMenu.open(player)
            return true
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

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            if (player.gameMode != GameMode.SURVIVAL) {
                player.sendMessage(TeleportMessages.RANDOM_TELEPORT_IN_BREAK_TIME.asSafety(player.wrappedLocale))
                return false
            }
            var chunk: Chunk? = null
            var location: Location? = null
            var count = 0
            // 適用可能か ダメならfalse
            var isValid = false
            while (!isValid && count++ < 20) {
                chunk = randomChunk(player)
                location = chunk.getSpawnableLocation()
                isValid = when {
                    chunk.isBattled -> false
                    chunk.isSpawnArea -> false
                    oceanBiomeSet.contains(location.block.biome) -> false
                    location.block.getRelative(BlockFace.DOWN, 2).type == Material.BEDROCK -> false
                    deathMaterialSet.contains(location.block.getRelative(BlockFace.DOWN, 2).type) -> false
                    else -> true
                }
            }
            if (!isValid) {
                PlayerSounds.FAIL.playOnly(player)
                return true
            }
            if (chunk == null) return true
            if (!chunk.isLoaded) {
                if (chunk.load(true)) return true
            }
            player.teleport(location!!)
            PlayerSounds.TELEPORT.play(location)
            return true
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

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            if (!Achievement.TELEPORT_LAST_DEATH.isGranted(player)) return false
            val chunk = player.getOrPut(Keys.LAST_DEATH_CHUNK) ?: return false
            chunk.load(true)
            var location: Location? = null
            var count = 0
            // 適用可能か ダメならfalse
            var isValid = false
            while (!isValid && count++ < 20) {
                location = chunk.getSpawnableLocation()
                isValid = when {
                    deathMaterialSet.contains(location.block.getRelative(BlockFace.DOWN, 2).type) -> false
                    else -> true
                }
            }
            if (!isValid) {
                TeleportMessages.CANT_TELEPORT.sendTo(player)
                PlayerSounds.FAIL.playOnly(player)
                return true
            }
            player.teleport(location)
            if (player.gameMode == GameMode.SURVIVAL)
                PlayerSounds.TELEPORT.play(location!!)
            player.offer(Keys.LAST_DEATH_CHUNK, null)
            return true
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

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            if (!Achievement.TELEPORT_PLAYER.isGranted(player)) return false
            player.transform(Keys.TELEPORT_TOGGLE) { !it }
            PlayerSounds.TOGGLE.playOnly(player)
            TeleportMenu.reopen(player)
            return true
        }

    }

    val TELEPORT_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                return when {
                    !to.isValid -> ItemStack(Material.GRAY_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_INVALID_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    // ミュートされている時×
                    to.isMute(player.uniqueId) -> ItemStack(Material.PURPLE_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_TOGGLE_OFF_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    // テレポートできないかつフォローされていない時×
                    !to.getOrPut(Keys.TELEPORT_TOGGLE) &&
                            !to.isFollow(player.uniqueId) -> ItemStack(Material.PURPLE_STAINED_GLASS_PANE).apply {
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
                    to.isFlying -> ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE).apply {
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

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                if (!to.isValid) return false
                if (to.isMute(player.uniqueId)) return false
                if (!to.getOrPut(Keys.TELEPORT_TOGGLE) &&
                        !to.isFollow(player.uniqueId)) return false
                if (to.gameMode != GameMode.SURVIVAL) return false
                if (to.world != player.world) return false
                if (to.isFlying) return false
                player.teleport(to)
                // 休憩中にテレポートするときは音を消すため条件付き
                if (player.gameMode == GameMode.SURVIVAL)
                    PlayerSounds.TELEPORT.play(to.location)
                return true
            }

        }
    }

    val TELEPORT_TO_SPAWN = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.OAK_SAPLING).apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_SPAWN.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            player.teleport(player.world.spawnLocation)
            if (player.gameMode == GameMode.SURVIVAL)
                PlayerSounds.TELEPORT.play(player.world.spawnLocation)
            return true
        }

    }

    val TELEPORT_TO_LAST_BREAK_CHUNK = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            player.getOrPut(Keys.LAST_BREAK_CHUNK) ?: return null
            return ItemStack(Material.DIAMOND_PICKAXE).apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_LAST_BREAK.asSafety(player.wrappedLocale))
                hideAllFlag()
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            val chunk = player.getOrPut(Keys.LAST_BREAK_CHUNK) ?: return false
            chunk.load(true)
            var location: Location? = null
            var count = 0
            // 適用可能か ダメならfalse
            var isValid = false
            while (!isValid && count++ < 20) {
                location = chunk.getSpawnableLocation()
                isValid = when {
                    deathMaterialSet.contains(location.block.getRelative(BlockFace.DOWN, 2).type) -> false
                    else -> true
                }
            }
            if (!isValid) {
                TeleportMessages.CANT_TELEPORT.sendTo(player)
                PlayerSounds.FAIL.playOnly(player)
                return true
            }
            player.teleport(location)
            if (player.gameMode == GameMode.SURVIVAL)
                PlayerSounds.TELEPORT.play(location!!)
            return true
        }

    }


    val TELEPORT_TO_HOME = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.TELEPORT_HOME.isGranted(player)) return null
            return ItemStack(Material.RED_BED).apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_HOME.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === TeleportToHomeMenu) return false
            if (!Achievement.TELEPORT_HOME.isGranted(player)) return false
            TeleportToHomeMenu.open(player)
            return true
        }

    }

}