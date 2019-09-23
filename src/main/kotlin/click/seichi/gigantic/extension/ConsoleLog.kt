package click.seichi.gigantic.extension

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.Bukkit

/**
 * @author tar0ss
 */

fun warning(msg: LocalizedText) {
    warning(msg.asSafety(Gigantic.DEFAULT_LOCALE))
}


fun info(msg: LocalizedText) {
    info(msg.asSafety(Gigantic.DEFAULT_LOCALE))
}


fun fine(msg: LocalizedText) {
    fine(msg.asSafety(Gigantic.DEFAULT_LOCALE))
}

fun warning(str: String) {
    Bukkit.getServer().logger.warning(str)
}

fun info(str: String) {
    Bukkit.getServer().logger.info(str)
}

fun fine(str: String) {
    Bukkit.getServer().logger.fine(str)
}