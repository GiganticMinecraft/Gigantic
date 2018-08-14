package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.config.PlayerLevelConfig.MAX
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.util.Polynomial
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author tar0ss
 */
object ManaConfig : SimpleConfiguration("mana", Gigantic.PLUGIN) {

    val MANA_MAP by lazy {
        (1..MAX).map {
            it to getLong("level_to_mana.$it")
        }.toMap()
    }

    override fun makeFile(file: File, plugin: JavaPlugin, fileName: String) {
        val pol = Polynomial(-83.30, 12.50, 0.5833)

        file.printWriter().use { out ->
            out.println("level_to_mana:")
            (0..9).forEach { out.println("  $it: ${Defaults.mana}") }
            (10..MAX).forEach { out.println("  $it: ${pol.calculation(it)}") }
        }
    }
}