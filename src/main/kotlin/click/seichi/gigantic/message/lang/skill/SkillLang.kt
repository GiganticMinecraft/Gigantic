package click.seichi.gigantic.message.lang.skill

import click.seichi.gigantic.message.LocalizedString
import java.util.*

/**
 * @author tar0ss
 */
object SkillLang {

    val ACTIVATE = LocalizedString(
            Locale.JAPANESE to "ON",
            Locale.ENGLISH to "ON"
    )

    val NOT_ACTIVATE = LocalizedString(
            Locale.JAPANESE to "OFF",
            Locale.ENGLISH to "OFF"
    )

    val FAILED_TO_LOAD = LocalizedString(
            Locale.JAPANESE to "読み込み失敗",
            Locale.ENGLISH to "Failed to load"
    )

    val LOCKED = LocalizedString(
            Locale.JAPANESE to "%DELETE%",
            Locale.ENGLISH to "%DELETE%"
    )

    val COOLDOWN = LocalizedString(
            Locale.JAPANESE to "クールダウン",
            Locale.ENGLISH to "Cooldown"
    )

    val NOT_SURVIVAL = LocalizedString(
            Locale.JAPANESE to "非サバイバル",
            Locale.ENGLISH to "Not survival"
    )

    val NOT_SEICHI_WORLD = LocalizedString(
            Locale.JAPANESE to "非整地ワールド",
            Locale.ENGLISH to "Not seichi world"
    )

    val FLYING = LocalizedString(
            Locale.JAPANESE to "Flyモード",
            Locale.ENGLISH to "Fly mode"
    )

    val NOT_SEICHI_TOOL = LocalizedString(
            Locale.JAPANESE to "非整地ツール",
            Locale.ENGLISH to "Not seichi tool"
    )


    val UPPER_BLOCK = LocalizedString(
            Locale.JAPANESE to "上方ブロック",
            Locale.ENGLISH to "Upper block"
    )

    val FOOTHOLD_BLOCK = LocalizedString(
            Locale.JAPANESE to "足場ブロック",
            Locale.ENGLISH to "Foothold block"
    )

    val NO_BLOCK = LocalizedString(
            Locale.JAPANESE to "空",
            Locale.ENGLISH to "null"
    )

    val NO_MANA = LocalizedString(
            Locale.JAPANESE to "マナ不足",
            Locale.ENGLISH to "Not enough mana"
    )

    val NO_DURABILITY = LocalizedString(
            Locale.JAPANESE to "耐久不足",
            Locale.ENGLISH to "Not enough durability"
    )

}