package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.FollowSettingMenuButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.FollowSettingMenuMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object FollowSettingMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return FollowSettingMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        // フォロー一覧
        registerButton(0, FollowSettingMenuButtons.FOLLOW)
        // フォロワー一覧
        registerButton(1, FollowSettingMenuButtons.FOLLOWER)
        // フォローする
        registerButton(2, FollowSettingMenuButtons.FOLLOW_ONLINE)
        // ミュート一覧
        registerButton(6, FollowSettingMenuButtons.MUTE)
        // ミュートする
        registerButton(7, FollowSettingMenuButtons.MUTE_ONLINE)
    }
}