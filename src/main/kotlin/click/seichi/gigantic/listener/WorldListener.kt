package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.message.messages.SystemMessages
import org.bukkit.Bukkit
import org.bukkit.Difficulty
import org.bukkit.GameRule
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldInitEvent
import org.bukkit.event.world.WorldSaveEvent
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

/**
 * @author tar0ss
 */
class WorldListener : Listener {

    @EventHandler
    fun onWorldInit(event: WorldInitEvent) {
        val world = event.world ?: return
        world.setSpawnFlags(false, false)
        world.pvp = false
        world.difficulty = Difficulty.NORMAL
        world.time = 6000
        world.isAutoSave = true
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false)
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        world.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, true)
        world.setGameRule(GameRule.DO_FIRE_TICK, false)
        world.setGameRule(GameRule.DO_LIMITED_CRAFTING, false)
        world.setGameRule(GameRule.SPAWN_RADIUS, 40)
        world.worldBorder.setCenter(0.0, 0.0)
        world.worldBorder.size = Config.WORLD_SIDE_LENGTH
        world.worldBorder.warningDistance = 0
        world.worldBorder.warningTime = 0
    }

    @EventHandler
    fun onWorldSave(event: WorldSaveEvent) {

        val uniqueIdSet = Bukkit.getOnlinePlayers().map { it.uniqueId }.toMutableSet()

        if (uniqueIdSet.isEmpty()) return

        Bukkit.getServer().consoleSender
                .sendMessage(SystemMessages.REGULAR_PLAYER_CACHE_SAVE.asSafety(Gigantic.DEFAULT_LOCALE))

        val targetSize = uniqueIdSet.size
        val successSet = mutableSetOf<UUID>()
        val failSet = mutableSetOf<UUID>()

        object : BukkitRunnable() {
            override fun run() {
                val uniqueId = uniqueIdSet.firstOrNull()
                if (uniqueId == null) {
                    Bukkit.getServer().consoleSender
                            .sendMessage(SystemMessages.TARGET.asSafety(Gigantic.DEFAULT_LOCALE) +
                                    targetSize + SystemMessages.PEOPLE.asSafety(Gigantic.DEFAULT_LOCALE))
                    Bukkit.getServer().consoleSender
                            .sendMessage(SystemMessages.SAVE_COMPLETE.asSafety(Gigantic.DEFAULT_LOCALE) +
                                    successSet + SystemMessages.PEOPLE.asSafety(Gigantic.DEFAULT_LOCALE))
                    if (failSet.isNotEmpty()) {
                        Bukkit.getServer().consoleSender
                                .sendMessage(SystemMessages.SAVE_FAIL.asSafety(Gigantic.DEFAULT_LOCALE) +
                                        failSet + SystemMessages.PEOPLE.asSafety(Gigantic.DEFAULT_LOCALE))
                    }
                    Bukkit.getServer().consoleSender
                            .sendMessage(SystemMessages.REGULAR_PLAYER_CACHE_SAVE_COMPLETE.asSafety(Gigantic.DEFAULT_LOCALE))
                    cancel()
                    return
                }
                uniqueIdSet.remove(uniqueId)

                if (!PlayerCacheMemory.contains(uniqueId)) {
                    successSet.add(uniqueId)
                    return
                }

                try {
                    PlayerCacheMemory.write(uniqueId, false)
                    successSet.add(uniqueId)
                } catch (e: Exception) {
                    e.printStackTrace()
                    failSet.add(uniqueId)
                }

            }
        }.runTaskTimer(Gigantic.PLUGIN, 0L, 1L)

    }

}