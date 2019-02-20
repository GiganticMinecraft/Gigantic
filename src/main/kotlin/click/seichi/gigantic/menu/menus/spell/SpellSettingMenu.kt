package click.seichi.gigantic.menu.menus.spell

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.item.items.menu.SpellSettingButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.menu.menus.SpellMenu
import click.seichi.gigantic.message.messages.menu.SpellSettingMenuMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object SpellSettingMenu : Menu() {

    override val size: Int
        get() = 3 * 9

    override fun getTitle(player: Player): String {
        return SpellSettingMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        // 戻るボタン
        registerButton(0, BackButton(this, SpellMenu))

        // マルチ・ブレイク
        registerButton(11, SpellSettingButtons.MULTI_BREAK)

        // 横幅変更（上げる）
        registerButton(12, SpellSettingButtons.BIGGER_WIDTH)
        // 横幅変更（下げる）
        registerButton(10, SpellSettingButtons.SMALLER_WIDTH)

        // 高さ変更（上げる）
        registerButton(2, SpellSettingButtons.BIGGER_HEIGHT)
        // 高さ変更（下げる）
        registerButton(20, SpellSettingButtons.SMALLER_HEIGHT)

        // 奥行変更（上げる）
        registerButton(3, SpellSettingButtons.BIGGER_DEPTH)
        // 奥行変更（下げる）
        registerButton(19, SpellSettingButtons.SMALLER_DEPTH)

        // ルナ・フレックス
        registerButton(15, SpellSettingButtons.LUNA_FLEX)

        // 速度を一段階上昇
        registerButton(6, SpellSettingButtons.BIGGER_DEGREE)
        // 速度を一段階下降
        registerButton(24, SpellSettingButtons.SMALLER_DEGREE)

    }

}