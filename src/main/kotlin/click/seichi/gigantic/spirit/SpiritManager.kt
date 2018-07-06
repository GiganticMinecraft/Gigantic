package click.seichi.gigantic.spirit

import click.seichi.gigantic.Gigantic
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author unicroak
 * @author tar0ss
 */
object SpiritManager {

    private val spiritSet: MutableSet<Spirit> = mutableSetOf()

    init {
        object : BukkitRunnable() {
            override fun run() = render()
        }.runTaskTimer(Gigantic.PLUGIN, 0, 1)
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