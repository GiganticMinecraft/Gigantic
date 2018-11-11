package click.seichi.gigantic.spirit

import click.seichi.gigantic.Gigantic
import org.bukkit.Bukkit

/**
 * @author unicroak
 * @author tar0ss
 */
object SpiritManager {

    private val spiritSet: MutableSet<Spirit> = mutableSetOf()

    fun onEnabled() {
        Bukkit.getScheduler().runTaskTimer(Gigantic.PLUGIN, {
            render()
        }, 0, 1)
    }

    fun spawn(spirit: Spirit) {
        spirit.spawn()
        spiritSet.add(spirit)
    }

    fun getSpiritSet() = spiritSet.toSet()

    private fun render() = spiritSet
            .onEach { spirit -> spirit.render() }
            .removeIf { spirit -> !spirit.isAlive }
            .let { }

}