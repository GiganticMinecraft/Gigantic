package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.button.buttons.MenuButtons
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object SpecialThanksMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return MenuMessages.SPECIAL_THANKS_TITLE.asSafety(player.wrappedLocale)
    }

    private val playerMap: Map<String, String> = mapOf(
            "kamikami46" to "6b076b43-c186-4a37-b8b5-46c84abbf46c",
            "Inubeco" to "a164535e-8597-4f55-9e34-1712fa69b831",
            "yuki_256" to "4ca99a6c-6c80-452f-a3a3-01b6aba7fccf"
    )

    init {
        var index = 0
        playerMap.values.forEach { uuidString ->
            val uuid = UUID.fromString(uuidString) ?: return@forEach
            registerButton(index, MenuButtons.PLAYER_HEAD(uuid))
            index++
        }
    }

}