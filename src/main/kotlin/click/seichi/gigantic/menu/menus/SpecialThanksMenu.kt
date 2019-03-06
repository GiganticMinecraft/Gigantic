package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.OfflinePlayerHeadButton
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.BagMessages
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object SpecialThanksMenu : Menu() {

    override val size: Int
        get() = playerMap.size.minus(1).div(9).plus(1).coerceIn(1..6).times(9)

    override fun getTitle(player: Player): String {
        return BagMessages.SPECIAL_THANKS_TITLE.asSafety(player.wrappedLocale)
    }

    private val playerMap: Map<String, String> = mapOf(
            "kamikami46" to "6b076b43-c186-4a37-b8b5-46c84abbf46c",
            "Inubeco" to "a164535e-8597-4f55-9e34-1712fa69b831",
            "yuki_256" to "4ca99a6c-6c80-452f-a3a3-01b6aba7fccf",
            "Rukure2017" to "f90b2eb7-c1b5-41fc-81de-f68537c497c9",
            "taaa150" to "9599901c-fa82-4943-b748-b46e183c53f4",
            "Neko_emon" to "2b4791d3-b71f-4e73-9891-33aeaa3a5ae4",
            "unicroak" to "0c578f76-d354-4727-a881-281edf77e186",
            "Mon_chi" to "4d84f609-0287-4db1-84e5-89e62e68396d",
            "kamui0429" to "8e62e785-24e8-45cc-bd16-5496e8b9f504",
            "Lucky3028" to "0ea34656-b1c7-45c0-8b89-1ec55a70fe17",
            "shirotubu" to "0a25e0f2-b952-4fe4-9843-3c5608aa2f0c",
            "nubasu" to "d85b4d71-88b1-4aef-ab98-225f46705f38",
            "f_mode" to "d22dc4b7-a1b8-4ff0-a887-9fde3f20970a"
    )

    init {
        var index = 0
        playerMap.forEach { name, uuidString ->
            val uuid = UUID.fromString(uuidString) ?: return@forEach
            val button = OfflinePlayerHeadButton(uuid, name)
            registerButton(index, button)
            index++
        }
    }

}