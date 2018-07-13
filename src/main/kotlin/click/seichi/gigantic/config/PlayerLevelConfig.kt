package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.util.Polynomial
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author tar0ss
 */
object PlayerLevelConfig : SimpleConfiguration("level", Gigantic.PLUGIN) {

    val MAX by lazy { getInt("max") }

    val LEVEL_MAP by lazy {
        (1..MAX).map {
            it to getLong("level_to_exp.$it")
        }.toMap()
    }

    override fun makeFile(file: File, plugin: JavaPlugin, fileName: String) {
        val defaultMaxLevel = 999
        val pol = Polynomial(-1.0000, 6.3779, -5.4233, 1.9360, 0.11550)
        file.printWriter().use { out ->
            out.println("max: $defaultMaxLevel")
            out.println("level_to_exp:")
            (1..defaultMaxLevel).forEach { out.println("  $it: ${pol.calculation(it)}") }
        }
    }

}