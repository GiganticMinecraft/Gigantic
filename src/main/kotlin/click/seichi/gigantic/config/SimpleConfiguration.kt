package click.seichi.gigantic.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author tar0ss
 */
abstract class SimpleConfiguration(
        fileName: String,
        plugin: JavaPlugin,
        val file: File = File(plugin.dataFolder, "$fileName.yml")
) : YamlConfiguration() {

    init {
        if (!file.exists()) {
            plugin.saveResource(file.name, false)
        }
        this.load(file)
    }

    fun save() = save(file)
}