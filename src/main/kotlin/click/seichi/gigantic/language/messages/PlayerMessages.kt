package click.seichi.gigantic.language.messages

import click.seichi.gigantic.language.ChatMessage
import click.seichi.gigantic.language.ChatMessageProtocol
import click.seichi.gigantic.language.LocalizedText
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object PlayerMessages {

    val FIRST_JOIN = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            // TODO
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}" +
                    "ブロックを破壊しましょう"
    ))

    val OBTAIN_WILL_APTITUDE = { will: Will ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let { it to "${ChatColor.AQUA}${ChatColor.BOLD}新しく${will.LocalizedText.asSafety(it)}の遺志と交感できるようになった" }
        ))
    }

    val FIRST_OBTAIN_WILL_APTITUDE = { will: Will ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let {
                    it to "${ChatColor.GRAY}" +
                            "ブロックを破壊すると、稀に遺志が現れます\n" +
                            "遺志と交感することで記憶を獲得できます\n" +
                            "${ChatColor.AQUA}${ChatColor.BOLD}" +
                            "${will.LocalizedText.asSafety(it)}の遺志と交感できるようになりました\n" +
                            "${ChatColor.GRAY}" +
                            "遺志は${Will.values().size}種類存在します\n" +
                            "レベルを上げてたくさんの遺志と交感しましょう\n"
                }
        ))
    }

}