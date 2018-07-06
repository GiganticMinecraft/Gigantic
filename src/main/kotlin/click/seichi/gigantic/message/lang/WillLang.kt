package click.seichi.gigantic.message.lang

import click.seichi.gigantic.message.LocalizedString
import click.seichi.gigantic.message.MessageProtocol
import click.seichi.gigantic.message.messages.Message
import click.seichi.gigantic.spirit.spirits.WillSpirit
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object WillLang {

    val AER = LocalizedString(
            Locale.JAPANESE to "空"
    )

    val AQUA = LocalizedString(
            Locale.JAPANESE to "水"
    )

    val IGNIS = LocalizedString(
            Locale.JAPANESE to "火"
    )

    val NATURA = LocalizedString(
            Locale.JAPANESE to "自然"
    )

    val TERRA = LocalizedString(
            Locale.JAPANESE to "土"
    )

    val GLACIES = LocalizedString(
            Locale.JAPANESE to "氷"
    )

    val LUX = LocalizedString(
            Locale.JAPANESE to "光"
    )

    val SOLUM = LocalizedString(
            Locale.JAPANESE to "地"
    )

    val UMBRA = LocalizedString(
            Locale.JAPANESE to "闇"
    )

    val VENTUS = LocalizedString(
            Locale.JAPANESE to "風"
    )

    val PREFIX_TINY_WILL = LocalizedString(
            Locale.JAPANESE to "僅かな"
    )

    val PREFIX_SMALL_WILL = LocalizedString(
            Locale.JAPANESE to "小さな"
    )

    val PREFIX_MEDIUM_WILL = LocalizedString(
            Locale.JAPANESE to ""
    )

    val PREFIX_LARGE_WILL = LocalizedString(
            Locale.JAPANESE to "大きな"
    )

    val PREFIX_HUGE_WILL = LocalizedString(
            Locale.JAPANESE to "巨大な"
    )

    val SENSED_WILL = Message(MessageProtocol.ACTION_BAR) { argumentList ->
        val willSpirit = argumentList[0] as WillSpirit
        val sizePrefix = willSpirit.willSize.prefix
        val memory = willSpirit.willSize.memory
        val name = willSpirit.willType.localizedName

        LocalizedString(
                Locale.JAPANESE.let { it to "${ChatColor.GREEN}${sizePrefix.asSafety(it)}${name.asSafety(it)}のウィルから${memory}の記憶を得ました" },
                Locale.ENGLISH.let { it to "${ChatColor.GREEN}$memory memory from ${sizePrefix.asSafety(it)}${name.asSafety(it)} will " }
        )
    }

    val SENSE_NO_APTITUDE = Message(MessageProtocol.ACTION_BAR) {
        LocalizedString(
                Locale.JAPANESE to "${ChatColor.RED}${it[0]}のウィルとの適正がありません"
        )
    }
}