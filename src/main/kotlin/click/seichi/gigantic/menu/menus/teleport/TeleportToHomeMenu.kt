package click.seichi.gigantic.menu.menus.teleport

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.item.items.menu.HomeButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.menu.menus.TeleportMenu
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import click.seichi.gigantic.player.Defaults
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object TeleportToHomeMenu : Menu() {

    override val size: Int
        get() = 18

    override fun getTitle(player: Player): String {
        return TeleportMessages.TELEPORT_TO_HOME.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, BackButton(this, TeleportMenu))
        val slotList = (9..17).step(2).toList()
        (0 until Defaults.MAX_HOME).forEach { homeId ->
            val slot = slotList.getOrNull(homeId) ?: return@forEach
            registerButton(slot, HomeButtons.HOME(homeId))
        }
    }
}