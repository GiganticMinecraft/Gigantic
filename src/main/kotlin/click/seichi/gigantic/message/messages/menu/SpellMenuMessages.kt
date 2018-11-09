package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.spell.SpellParameters
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object SpellMenuMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "魔法"
    )

    val TERRA_DRAIN =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: 木を倒し、自身の体力を回復する"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "マナ消費量 ${SpellParameters.TERRA_DRAIN_MANA_PER_BLOCK} / ブロック"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量(原木): 最大体力の %.1f ".format(SpellParameters.TERRA_DRAIN_LOG_HEAL_PERCENT).plus("%")
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量(葉): 最大体力の %.1f ".format(SpellParameters.TERRA_DRAIN_LEAVES_HEAL_PERCENT).plus("%")
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "原木を破壊して発動"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.BLUE}" +
                                    "---スニークで通常破壊"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}----------------"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
                    )
            )

    val TERRA_DRAIN_TITLE_ON = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "回復魔法: テラ・ドレイン"
    )

    val TERRA_DRAIN_TITLE_OFF = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "回復魔法: テラ・ドレイン -封印-"
    )


    val STELLA_CLAIR_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "回復魔法: ステラ・クレア"
    )

    val GRAND_NATURA =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: 植物を吸収する"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "マナ消費量 ${SpellParameters.GRAND_NATURA_MANA_PER_BLOCK} / ブロック"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "芝生又はキノコブロックを破壊時に発動"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.BLUE}" +
                                    "---スニークで通常破壊"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}----------------"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
                    )
            )

    val GRAND_NATURA_TITLE_ON = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "破壊魔法: グランド・ナトラ"
    )

    val GRAND_NATURA_TITLE_OFF = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "破壊魔法: グランド・ナトラ -封印-"
    )


    val AQUA_LINEA =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: ブロックが泡となって消える"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "マナ消費量 ${SpellParameters.AQUA_LINEA_MANA_PER_BLOCK} / ブロック"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "破壊時に発動"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.BLUE}" +
                                    "---スニークで通常破壊"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}----------------"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
                    )
            )

    val AQUA_LINEA_TITLE_ON = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "破壊魔法: アクア・リネーア"
    )

    val AQUA_LINEA_TITLE_OFF = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "破壊魔法: アクア・リネーア -封印-"
    )

}