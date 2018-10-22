package click.seichi.gigantic.extension

import click.seichi.gigantic.util.NoiseData
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.World

/**
 * @author unicroak
 */
fun World.spawnColoredParticle(
        location: Location,
        color: Color,
        count: Int = 1,
        noiseData: NoiseData = NoiseData()
) = players.forEach { it.spawnColoredParticle(location, color, count, noiseData) }

fun World.spawnColoredParticleSpherically(
        location: Location,
        color: Color,
        count: Int = 1,
        radius: Double
) = players.forEach { it.spawnColoredParticleSpherically(location, color, count, radius) }
