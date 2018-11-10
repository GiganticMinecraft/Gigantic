package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import click.seichi.gigantic.util.Random
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class MonsterSpirit(
        spawnReason: SpawnReason,
        breakLocation: Location,
        val monster: SoulMonster,
        targetPlayer: Player? = null
) : Spirit(spawnReason, breakLocation.chunk) {

    val spawnLocation = searchSpawnLocation(breakLocation)

    private fun searchSpawnLocation(breakLocation: Location): Location? {
        val chunk = breakLocation.chunk
        var count = 0
        var location: Location?
        while (true) {
            count++
            val x = Random.nextInt(15)
            val z = Random.nextInt(15)
            location = chunk.world.getHighestBlockAt(x, z).getRelative(BlockFace.UP).location
            val distance = breakLocation.distance(location)
            if (distance > 3.0 || count >= 10) break
        }
        return location
    }

    // 60秒
    override val lifespan = 20 * 60
    override val spiritType = SpiritType.MONSTER

    override fun onSpawn() {
        // TODO playOnly player
//        MonsterSpiritSounds.SPAWN.play(location)
    }

    override fun onRender() {


    }

}