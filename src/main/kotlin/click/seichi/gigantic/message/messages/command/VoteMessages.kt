package click.seichi.gigantic.message.messages.command

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object VoteMessages {

    val NO_USER = { uuid: UUID ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RED}" +
                        "指定されたユーザーは見つかりません．\n" +
                        "uuid:$uuid"
        )
    }

    val COMPLETE = { uuid: UUID, inc: Int ->

        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}" +
                        "投票完了\n" +
                        "uuid:$uuid\n" +
                        "num:$inc"
        )
    }

}