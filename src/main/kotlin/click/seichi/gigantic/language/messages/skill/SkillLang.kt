package click.seichi.gigantic.language.messages.skill

import click.seichi.gigantic.language.LocalizedText
import java.util.*

/**
 * @author tar0ss
 */
object SkillLang {

    val ACTIVATE = LocalizedText(
            Locale.JAPANESE to "ON",
            Locale.ENGLISH to "ON"
    )

    val NOT_ACTIVATE = LocalizedText(
            Locale.JAPANESE to "OFF",
            Locale.ENGLISH to "OFF"
    )

    val LOCKED = LocalizedText(
            Locale.JAPANESE to "%DELETE%",
            Locale.ENGLISH to "%DELETE%"
    )

    val COOLDOWN = LocalizedText(
            Locale.JAPANESE to "クールダウン",
            Locale.ENGLISH to "Cooldown"
    )

    val NOT_SURVIVAL = LocalizedText(
            Locale.JAPANESE to "非サバイバル",
            Locale.ENGLISH to "Not survival"
    )

    val NOT_SEICHI_WORLD = LocalizedText(
            Locale.JAPANESE to "非整地ワールド",
            Locale.ENGLISH to "Not seichi world"
    )

    val FLYING = LocalizedText(
            Locale.JAPANESE to "Flyモード",
            Locale.ENGLISH to "Fly mode"
    )

    val NOT_SEICHI_TOOL = LocalizedText(
            Locale.JAPANESE to "非整地ツール",
            Locale.ENGLISH to "Not seichi tool"
    )


    val UPPER_BLOCK = LocalizedText(
            Locale.JAPANESE to "上方ブロック",
            Locale.ENGLISH to "Upper block"
    )

    val FOOTHOLD_BLOCK = LocalizedText(
            Locale.JAPANESE to "足場ブロック",
            Locale.ENGLISH to "Foothold block"
    )

    val NO_BLOCK = LocalizedText(
            Locale.JAPANESE to "空",
            Locale.ENGLISH to "null"
    )

    val NO_MANA = LocalizedText(
            Locale.JAPANESE to "マナ不足",
            Locale.ENGLISH to "Not enough mana"
    )

    val NO_DURABILITY = LocalizedText(
            Locale.JAPANESE to "耐久不足",
            Locale.ENGLISH to "Not enough durability"
    )

}