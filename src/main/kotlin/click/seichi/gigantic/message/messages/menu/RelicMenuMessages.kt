package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * @author tar0ss
 */
object RelicMenuMessages {

    val NUM = LocalizedText(
            Locale.JAPANESE to "所持数"
    )

    val WILL = LocalizedText(
            Locale.JAPANESE to "の意志"
    )

    val RELICS = LocalizedText(
            Locale.JAPANESE to "レリック一覧"
    )

    val CONDITIONS_FIRST_LINE = { line: String ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                        "条件: " +
                        "${ChatColor.RESET}${ChatColor.WHITE}" +
                        line
        )
    }
    val CONDITIONS = { line: String ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                        line
        )
    }

    val BONUS_EXP = { mul: BigDecimal ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                        "ボーナス経験値: " +
                        "${ChatColor.RESET}${ChatColor.WHITE}" +
                        "整地量 x " +
                        mul.setScale(2, RoundingMode.HALF_UP)
        )
    }

    val GENERATED_ETHEL_LORE = { will: Will, relic: Relic ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "" + will.chatColor + "${ChatColor.BOLD}" +
                                    will.getName(it) +
                                    "のエーテルを" +
                                    "${Defaults.RELIC_GENERATOR_REQUIRE_ETHEL}" +
                                    "使って"
                        }
                ),
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "" + will.chatColor + "${ChatColor.BOLD}" +
                                    "レリック「${relic.getName(it)}」を手に入れた"
                        }
                )
        )
    }

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

    val SPECIAL_RELIC_MENU_TITLE = LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.RESET}${ChatColor.WHITE}" +
                        "特殊 " +
                        RelicMenuMessages.RELICS.asSafety(it)
            }
    )

    val ACTIVE_RELIC_MENU_TITLE = LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.RESET}${ChatColor.WHITE}" +
                        "ボーナス発生中 " +
                        RelicMenuMessages.RELICS.asSafety(it)
            }
    )

    val ALL_RELIC_MENU_TITLE = LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.RESET}${ChatColor.WHITE}" +
                        "全てのレリックを表示"
            }
    )


    val RELIC_MENU_LORE = { num: Long, type: Int, allType: Int ->
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

    val ACTIVE_RELIC_MENU_LORE = { num: Long, type: Int, bonus: Double ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GREEN}" +
                                "合計" +
                                "${num}個" +
                                "$type" +
                                "種類"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.YELLOW}" +
                                "合計ボーナス値: " +
                                "${ChatColor.WHITE}" +
                                "${bonus.toBigDecimal().setScale(2, RoundingMode.HALF_UP)}"
                )
        )
    }

    val RELIC_TITLE = { chatColor: ChatColor, relic: Relic, amount: Long ->
        LocalizedText(
                Locale.JAPANESE.let {
                    it to "${ChatColor.RESET}" +
                            chatColor + "${ChatColor.BOLD}" +
                            relic.getName(it) +
                            "($amount)"
                })

    }


}