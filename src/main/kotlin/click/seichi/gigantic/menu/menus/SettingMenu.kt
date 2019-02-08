package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.SettingButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.SettingMenuMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object SettingMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return SettingMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        // アクションバーの表示設定
        registerButton(0, SettingButtons.DISPLAY_SETTING)
        // ツール切り替え設定
        registerButton(7, SettingButtons.TOOL_SWITCH_SETTING)
    }
}