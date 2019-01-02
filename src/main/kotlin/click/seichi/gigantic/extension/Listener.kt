package click.seichi.gigantic.extension

import click.seichi.gigantic.Gigantic
import org.bukkit.event.Listener

/**
 * @author unicroak
 */
fun Listener.register() = Gigantic.PLUGIN.let {
    it.server.pluginManager.registerEvents(this, it)
}