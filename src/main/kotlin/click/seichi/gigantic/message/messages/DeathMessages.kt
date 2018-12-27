package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.monster.SoulMonster
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object DeathMessages {

    val BY_MONSTER = { name: String, monster: SoulMonster ->
        LocalizedText(
                Locale.JAPANESE.let { it to "$name は ${monster.getName(it)} に倒された..." }
        )
    }

    val DEATH_TELEPORT = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.BLUE}" +
                        "テレポートメニューから最後に死亡した場所に戻れる\n"
            }
    ))

}