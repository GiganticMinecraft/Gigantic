package click.seichi.gigantic.config

import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.util.Polynomial
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author tar0ss
 */
object HealthConfig : SimpleConfiguration("health") {

    val HEALTH_MAP by lazy {
        (1..PlayerLevelConfig.MAX).map {
            it to getLong("level_to_health.$it")
        }.toMap()
    }

    override fun makeFile(file: File, plugin: JavaPlugin, fileName: String) {
        val pol = Polynomial(0.0, 15.28, 0.9039, 0.0099)

        file.printWriter().use { out ->
            out.println("level_to_health:")
            (1..5).forEach { out.println("  $it: ${Defaults.HEALTH}") }
            (6..PlayerLevelConfig.MAX).forEach { out.println("  $it: ${pol.calculation(it)}") }
        }

    }

}