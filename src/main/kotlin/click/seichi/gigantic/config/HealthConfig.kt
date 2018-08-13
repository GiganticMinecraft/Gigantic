package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.util.Polynomial
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author tar0ss
 */
object HealthConfig : SimpleConfiguration("health", Gigantic.PLUGIN) {

    val HEALTH_MAP by lazy {
        (1..PlayerLevelConfig.MAX).map {
            it to getLong("level_to_health.$it")
        }.toMap()
    }

    override fun makeFile(file: File, plugin: JavaPlugin, fileName: String) {
        val pol = Polynomial(-236.11, 7.0833, 7.9528)

        file.printWriter().use { out ->
            out.println("level_to_health:")
            (1..6).forEach { out.println("  $it: 100") }
            (7..PlayerLevelConfig.MAX).forEach { out.println("  $it: ${pol.calculation(it)}") }
        }
    }

}