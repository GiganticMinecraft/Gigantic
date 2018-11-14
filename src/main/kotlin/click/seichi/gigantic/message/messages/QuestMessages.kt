package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.quest.Quest
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object QuestMessages {

    val QUEST_PROCEED = { quest: Quest, degree: Int ->
        ChatMessage(ChatMessageProtocol.CHAT,
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.YELLOW}" +
                                    "\"${quest.getTitle(it)}\"" +
                                    " クエストが進行しました" +
                                    "( $degree / ${quest.maxDegree} )"
                        }
                ))
    }

    val QUEST_COMPLETE = { quest: Quest ->
        ChatMessage(ChatMessageProtocol.CHAT,
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.LIGHT_PURPLE}" +
                                    "\"${quest.getTitle(it)}\"" +
                                    " クエストを完了しました"
                        }
                ))
    }


    val LADON = LocalizedText(
            Locale.JAPANESE to "金の森を守りしもの"
    )

    val UNDINE = LocalizedText(
            Locale.JAPANESE to "水を操りしもの"
    )

    val SALAMANDRA = LocalizedText(
            Locale.JAPANESE to "火を操りしもの"
    )

    val SYLPHID = LocalizedText(
            Locale.JAPANESE to "風を操りしもの"
    )

    val NOMOS = LocalizedText(
            Locale.JAPANESE to "土を操りしもの"
    )

    val LOA = LocalizedText(
            Locale.JAPANESE to "自然を操りしもの"
    )

    val PIG = LocalizedText(
            Locale.JAPANESE to "飛ばねぇ豚はただの豚だ"
    )

}