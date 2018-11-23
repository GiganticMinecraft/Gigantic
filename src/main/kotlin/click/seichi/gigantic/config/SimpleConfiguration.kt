package click.seichi.gigantic.config

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author tar0ss
 */
abstract class SimpleConfiguration(
        private val fileName: String
) : YamlConfiguration() {

    fun init(plugin: JavaPlugin) {
        val file = File(plugin.dataFolder, "$fileName.yml")
        if (!file.exists()) {
            this.makeFile(file, plugin, fileName)
        }
        this.load(file)
    }

    protected open fun makeFile(file: File, plugin: JavaPlugin, fileName: String) {
        plugin.saveResource(file.name, false)
    }
}