package click.seichi.gigantic.language.messages

import click.seichi.gigantic.language.ChatMessage
import click.seichi.gigantic.language.ChatMessageProtocol
import click.seichi.gigantic.language.LocalizedText
import click.seichi.gigantic.spirit.spirits.WillSpirit
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object WillMessages {

    val AER = LocalizedText(
            Locale.JAPANESE to "空"
    )

    val AQUA = LocalizedText(
            Locale.JAPANESE to "水"
    )

    val IGNIS = LocalizedText(
            Locale.JAPANESE to "火"
    )

    val NATURA = LocalizedText(
            Locale.JAPANESE to "自然"
    )

    val TERRA = LocalizedText(
            Locale.JAPANESE to "土"
    )

    val GLACIES = LocalizedText(
            Locale.JAPANESE to "氷"
    )

    val LUX = LocalizedText(
            Locale.JAPANESE to "光"
    )

    val SOLUM = LocalizedText(
            Locale.JAPANESE to "地"
    )

    val UMBRA = LocalizedText(
            Locale.JAPANESE to "闇"
    )

    val VENTUS = LocalizedText(
            Locale.JAPANESE to "風"
    )

    val PREFIX_TINY_WILL = LocalizedText(
            Locale.JAPANESE to "僅かな"
    )

    val PREFIX_SMALL_WILL = LocalizedText(
            Locale.JAPANESE to "小さな"
    )

    val PREFIX_MEDIUM_WILL = LocalizedText(
            Locale.JAPANESE to ""
    )

    val PREFIX_LARGE_WILL = LocalizedText(
            Locale.JAPANESE to "大きな"
    )

    val PREFIX_HUGE_WILL = LocalizedText(
            Locale.JAPANESE to "巨大な"
    )

    val SENSED_WILL = { willSpirit: WillSpirit ->
        val sizePrefix = willSpirit.willSize.prefix
        val memory = willSpirit.willSize.memory
        val name = willSpirit.will.LocalizedText

        ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
                Locale.JAPANESE.let { it to "${ChatColor.GREEN}${sizePrefix.asSafety(it)}${name.asSafety(it)}のウィルから${memory}の記憶を得ました" },
                Locale.ENGLISH.let { it to "${ChatColor.GREEN}$memory memory from ${sizePrefix.asSafety(it)}${name.asSafety(it)} will " }
        ))
    }

//  使用しないため抜いておく
//    val SENSE_NO_APTITUDE = { will: Will ->
//
//        ChatMessage(ChatMessageProtocol.ACTION_BAR,
//                LocalizedText(
//                        Locale.JAPANESE to "${ChatColor.RED}${will}のウィルとの適正がありません"
//                )
//        )
//    }
}