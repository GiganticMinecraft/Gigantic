package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.button.buttons.MenuButtons
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

/**
 * @author tar0ss
 */
object SpecialThanksMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return "${ChatColor.BLACK}" +
                MenuMessages.SPECIAL_THANKS_TITLE.asSafety(player.wrappedLocale)
    }

    private val playerMap: Map<String, String> = mapOf(
            "kamikami46" to "6b076b43-c186-4a37-b8b5-46c84abbf46c",
            "Inubeco" to "a164535e-8597-4f55-9e34-1712fa69b831",
            "yuki_256" to "4ca99a6c-6c80-452f-a3a3-01b6aba7fccf",
            "Rukure2017" to "f90b2eb7-c1b5-41fc-81de-f68537c497c9",
            "taaa150" to "9599901c-fa82-4943-b748-b46e183c53f4"
    )

    init {
        var index = 0
        playerMap.values.forEach { uuidString ->
            val uuid = UUID.fromString(uuidString) ?: return@forEach
            val button = MenuButtons.PLAYER_HEAD(uuid)
            val offlinePlayer = (button.getItemStack()?.itemMeta as? SkullMeta)?.owningPlayer ?: return@forEach
            if (offlinePlayer.name == null) return@forEach
            registerButton(index, button)
            index++
        }
    }

}