package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.item.items.menu.FollowSettingButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.menu.RefineItem
import click.seichi.gigantic.message.messages.menu.FollowSettingMenuMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object PlayerListRefineMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return FollowSettingMenuMessages.REFINE.asSafety(player.wrappedLocale)
    }

    val backButton = BackButton(this, FollowSettingMenu)

    init {
        registerButton(0, backButton)
        RefineItem.values().forEach { item ->
            registerButton(item.slot, FollowSettingButtons.REFINE_ITEM(item))
        }
    }


}