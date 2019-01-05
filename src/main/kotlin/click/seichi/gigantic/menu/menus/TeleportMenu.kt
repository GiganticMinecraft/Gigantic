package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.TeleportButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object TeleportMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return TeleportMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, TeleportButtons.TELEPORT_TO_RANDOM_CHUNK)
        registerButton(1, TeleportButtons.TELEPORT_TO_PLAYER)
        registerButton(2, TeleportButtons.TELEPORT_TO_SPAWN)
        registerButton(4, TeleportButtons.TELEPORT_TO_DEATH_CHUNK)
        registerButton(8, TeleportButtons.TELEPORT_TOGGLE)
    }

}