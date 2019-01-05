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
        get() = 18

    override fun getTitle(player: Player): String {
        return TeleportMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, TeleportButtons.TELEPORT_TO_RANDOM_CHUNK)
        registerButton(2, TeleportButtons.TELEPORT_TO_SPAWN)
        registerButton(4, TeleportButtons.TELEPORT_TO_LAST_BREAK_CHUNK)
        registerButton(10, TeleportButtons.TELEPORT_TO_PLAYER)
        registerButton(12, TeleportButtons.TELEPORT_TO_DEATH_CHUNK)
        registerButton(17, TeleportButtons.TELEPORT_TOGGLE)
    }

}