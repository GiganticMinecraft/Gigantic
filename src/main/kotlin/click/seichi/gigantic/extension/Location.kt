package click.seichi.gigantic.extension

import click.seichi.gigantic.util.NoiseData
import org.bukkit.Location

/**
 * @author unicroak
 */

fun Location.noised(noiseData: NoiseData): Location = this.clone().add(
        noiseData.noiser(noiseData.size.x),
        noiseData.noiser(noiseData.size.y),
        noiseData.noiser(noiseData.size.z)
)

val Location.central
    get() = this.block.centralLocation
