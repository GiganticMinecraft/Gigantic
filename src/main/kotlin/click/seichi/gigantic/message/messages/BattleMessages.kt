package click.seichi.gigantic.message.messages

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.relic.Relic
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object BattleMessages {
    val GET_RELIC = { relic: Relic ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let {
                    it to
                            "${ChatColor.GRAY}${ChatColor.BOLD}" +
                            "${relic.localizedName.asSafety(it)}を手に入れた!!"
                }
        ))
    }
    val DEFEAT_BOSS = { boss: Boss ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let {
                    it to
                            "${ChatColor.RED}${ChatColor.BOLD}" +
                            "${boss.localizedName.asSafety(it)}を倒した!!"
                }
        ))
    }
}