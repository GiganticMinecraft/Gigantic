package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object UnlockMessages {

    val UNLOCK_MINE_BURST = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.AQUA}${ChatColor.BOLD}" +
                        "初めてのスキル･マインバーストを習得した!!\n" +
                        "${ChatColor.GRAY}" +
                        "ショートカットキー: 2\n"
            }
    ))

    val UNLOCK_RAID_BATTLE = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.RED}${ChatColor.BOLD}" +
                        "レイドバトル解禁!!敵を倒してレアアイテムをゲット!!\n" +
                        "${ChatColor.GRAY}" +
                        "インベントリから敵を選択しよう\n" +
                        "ブロックを破壊することで敵を攻撃しよう\n"
            }
    ))
}