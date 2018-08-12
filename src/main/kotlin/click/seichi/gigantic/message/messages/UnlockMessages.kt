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
                it to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                        "スキル:マインバースト を習得した!!\n" +
                        "${ChatColor.GRAY}" +
                        "効果: 少しの間だけ掘る速度が上昇\n" +
                        "\"2\" キー を押して発動!!\n"
            }
    ))

    val UNLOCK_RAID_BATTLE = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.RED}${ChatColor.BOLD}" +
                        "レイドバトル解禁!!敵を倒してレアアイテムをゲット!!\n" +
                        "${ChatColor.GRAY}" +
                        "インベントリから敵を選択しよう\n" +
                        "ブロックを破壊することで敵を攻撃!!\n"
            }
    ))

    val UNLOCK_FLASH = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                        "初めてのスキル:フラッシュ を習得した!!\n" +
                        "${ChatColor.GRAY}" +
                        "効果: ブロックに向けて発動すると\n" +
                        "そのブロックの上にワープする\n" +
                        "\"3\" キー を押して発動!!\n"
            }
    ))

}