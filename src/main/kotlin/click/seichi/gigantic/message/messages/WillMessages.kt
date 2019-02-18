package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.spirit.spirits.WillSpirit
import click.seichi.gigantic.will.Will
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

    val SAKURA = LocalizedText(
            Locale.JAPANESE to "桜"
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

        ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
                Locale.JAPANESE.let {
                    it to "${ChatColor.GREEN}" +
                            "${willSpirit.will.chatColor}${ChatColor.BOLD}" +
                            sizePrefix.asSafety(it) +
                            willSpirit.will.getName(it) +
                            "の意志" +
                            "${ChatColor.RESET}${ChatColor.WHITE}" +
                            "から" +
                            "${willSpirit.will.chatColor}${ChatColor.BOLD}" +
                            willSpirit.will.getName(it) +
                            "のエーテル($memory)" +
                            "${ChatColor.RESET}${ChatColor.WHITE}" +
                            "を獲得"
                }
        ))
    }

    val GET_ETHEL_TEXT = { will: Will, ethel: Long ->
        LocalizedText(
                Locale.JAPANESE.let {
                    it to "${will.chatColor}${ChatColor.BOLD}" +
                            will.getName(it) +
                            "のエーテル($ethel)" +
                            "${ChatColor.RESET}${ChatColor.WHITE}" +
                            "を獲得"
                }
        )
    }

    val NEXT_RELATIONSHIP = { will: Will, relation: String ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let {
                    it to "${will.chatColor}${ChatColor.BOLD}" +
                            will.getName(it) +
                            "の意志" +
                            "${ChatColor.RESET}${ChatColor.WHITE}" +
                            "と" +
                            "${will.chatColor}${ChatColor.BOLD}" +
                            relation +
                            "${ChatColor.RESET}${ChatColor.WHITE}" +
                            "になった\n" +
                            "交感できる距離が伸びた"
                }
        ))
    }

    val FRESH = LocalizedText(
            Locale.JAPANESE to "新友"
    )

    val HOMIE = LocalizedText(
            Locale.JAPANESE to "普友"
    )

    val FRIEND = LocalizedText(
            Locale.JAPANESE to "深友"
    )

    val BESTIE = LocalizedText(
            Locale.JAPANESE to "親友"
    )

    val BFF = LocalizedText(
            Locale.JAPANESE to "臣友"
    )

    val SOULMATE = LocalizedText(
            Locale.JAPANESE to "心友"
    )

    val PARTNER = LocalizedText(
            Locale.JAPANESE to "神友"
    )

}