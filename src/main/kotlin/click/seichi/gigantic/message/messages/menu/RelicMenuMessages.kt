package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object RelicMenuMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "レリックを選ぶ"
    )

    val NUM = LocalizedText(
            Locale.JAPANESE to "所持数"
    )

    val WILL = LocalizedText(
            Locale.JAPANESE to "の意志"
    )

    val RELICS = LocalizedText(
            Locale.JAPANESE to "レリック一覧"
    )

    val CONDITIONS = LocalizedText(
            Locale.JAPANESE to "条件: "
    )

    val BONUS_EXP = LocalizedText(
            Locale.JAPANESE to "ボーナス経験値: "
    )

    val BREAK_MUL = LocalizedText(
            Locale.JAPANESE to "整地量 x "
    )

    val GENERATED_ETHEL = LocalizedText(
            Locale.JAPANESE to "のエーテルを${Defaults.RELIC_GENERATOR_REQUIRE_ETHEL}使って"
    )

    val GENERATED_RELIC_PREFIX =
            LocalizedText(
                    Locale.JAPANESE to "レリック「"
            )
    val GENERATED_RELIC_SUFIX =
            LocalizedText(
                    Locale.JAPANESE to "」を手に入れた"
            )

    val RELATIONSHIP = LocalizedText(
            Locale.JAPANESE to "あなたとの関係: "
    )

    val WILL_RELIC_MENU_TITLE = { will: Will ->
        LocalizedText(
                Locale.JAPANESE.let {
                    it to "" + will.chatColor +
                            "${ChatColor.BOLD}" +
                            will.getName(it) +
                            RelicMenuMessages.WILL.asSafety(it) +
                            " " +
                            "${ChatColor.RESET}${ChatColor.WHITE}" +
                            RelicMenuMessages.RELICS.asSafety(it)
                }
        )
    }

    val WILL_RELIC_MENU_LORE = { num: Long, type: Int, allType: Int ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GREEN}" +
                                "手に入れたレリック: " +
                                "${ChatColor.WHITE}" +
                                "${num}個"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GREEN}" +
                                "コンプリート達成率: " +
                                "${ChatColor.WHITE}" +
                                "$type/$allType"
                )
        )
    }


}