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

    val QUEST_PROCESSED = { quest: Quest, degree: Int ->
        ChatMessage(ChatMessageProtocol.CHAT,
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.YELLOW}" +
                                    "\"${quest.getTitle(it)}\"" +
                                    " クエストが進んだ" +
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
                                    " クエストを完了した"
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

    val BLAZE = LocalizedText(
            Locale.JAPANESE to "もっと熱くなれよ"
    )

    val CHICKEN = LocalizedText(
            Locale.JAPANESE to "庭には二羽鶏がいる"
    )

    val WITHER = LocalizedText(
            Locale.JAPANESE to "世界を蝕むもの"
    )

    val BEGIN = LocalizedText(
            Locale.JAPANESE to "始まり"
    )

    val TURTLE = LocalizedText(
            Locale.JAPANESE to "ヘルメスの竪琴"
    )

    val SPIDER = LocalizedText(
            Locale.JAPANESE to "宿直草"
    )

    val ZOMBIE = LocalizedText(
            Locale.JAPANESE to "ヴードゥーの司祭"
    )

    val SKELETON = LocalizedText(
            Locale.JAPANESE to "メメント・モリ"
    )

    val ORC = LocalizedText(
            Locale.JAPANESE to "グレンデル"
    )

    val GHOST = LocalizedText(
            Locale.JAPANESE to "死に装束"
    )

    val PARROT = LocalizedText(
            Locale.JAPANESE to "こだまことだま"
    )

    val SLIME = LocalizedText(
            Locale.JAPANESE to "ダイラタンシー流体"
    )

}