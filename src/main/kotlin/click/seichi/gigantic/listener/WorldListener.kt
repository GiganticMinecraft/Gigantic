package click.seichi.gigantic.listener

import org.bukkit.Difficulty
import org.bukkit.GameRule
import org.bukkit.entity.EntityType
import org.bukkit.entity.Mob
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.WorldInitEvent

/**
 * @author tar0ss
 */
class WorldListener : Listener {

// not working 原因不明
//    @EventHandler
//    fun onPopulate(event: ChunkPopulateEvent) {
//        val world = event.world ?: return
//        val chunk = event.chunk ?: return
//        (0..15).forEach { x ->
//            (0..15).forEach { z ->
//                (1..4).forEach loopY@{ y ->
//                    val block = chunk.getBlock(x, y, z) ?: return@loopY
//                    if(block.type == Material.BEDROCK)
//                        block.type = Material.STONE
//                }
//            }
//        }
//    }

    @EventHandler
    fun onLoad(event: ChunkLoadEvent) {
        event.chunk.entities.filter { it.type == EntityType.ARMOR_STAND }.forEach { it.remove() }
    }

    @EventHandler
    fun onWorldInit(event: WorldInitEvent) {
        val world = event.world ?: return
        world.setSpawnFlags(false, false)
        world.pvp = false
        world.difficulty = Difficulty.NORMAL
        world.time = 6000
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false)
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        world.setGameRule(GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK, false)
        world.setGameRule(GameRule.DO_FIRE_TICK, false)
        world.setGameRule(GameRule.DO_LIMITED_CRAFTING, false)
        world.setGameRule(GameRule.SPAWN_RADIUS, 40)
    }

    @EventHandler
    fun onSpawnEntity(event: EntitySpawnEvent) {
        if (event.entity !is Mob) return
        event.isCancelled = true
    }

}