package click.seichi.gigantic

import org.bukkit.plugin.java.JavaPlugin

/**
 * @author tar0ss
 */
class Gigantic : JavaPlugin() {

    companion object {
        lateinit var PLUGIN: Gigantic
    }

    override fun onEnable() {
        PLUGIN = this
        logger.info("Gigantic is enabled!!")

    }

    override fun onDisable() {
        server.scheduler.cancelTasks(this)
        logger.info("Gigantic is disabled!!")
    }

}