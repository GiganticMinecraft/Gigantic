package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LinedChatMessage
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.TitleMessage
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object LoginMessages {

    val LOGIN_CHAT = LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            (1..53).joinToString("") { "-" } +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "整地鯖(春)" +
                            "${ChatColor.WHITE}" +
                            "へようこそ!" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.AQUA}${ChatColor.BOLD}" +
                            "オープンβテスト" +
                            "${ChatColor.WHITE}" +
                            "中の為、" +
                            "${ChatColor.RED}${ChatColor.BOLD}" +
                            "レベルのリセット" +
                            "${ChatColor.WHITE}" +
                            "等が" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            "起こる可能性がありますことを予めご了承ください" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            "※" +
                            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "整地鯖(春)" +
                            "${ChatColor.WHITE}" +
                            "に関するお問い合わせを" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.GREEN}${ChatColor.BOLD}" +
                            "ギガンティック整地鯖" +
                            "${ChatColor.WHITE}" +
                            "運営チームに行わないでください" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "整地鯖(春)" +
                            "${ChatColor.WHITE}" +
                            "に関するご質問は" +
                            "${ChatColor.DARK_PURPLE}${ChatColor.BOLD}" +
                            "公式ディスコード" +
                            "${ChatColor.WHITE}" +
                            "で承ります" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            "公式ディスコード: " +
                            "${ChatColor.YELLOW}" +
                            "https://discord.gg/nmhjtC5" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            "寄付受付: " +
                            "${ChatColor.YELLOW}" +
                            "https://goo.gl/forms/8ZR3MJwtSeTDkGST2" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            "リソースパック手動ダウンロードリンク↓" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.YELLOW}" +
                            "https://red.minecraftserver.jp/attachments/download/872/Spring_Texture_ver1.1.zip" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            (1..53).joinToString("") { "-" }

            ))

    val LOGIN_TITLE = TitleMessage(
            title = LocalizedText(
                    Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                            "整地鯖(春)"
            ), subTitle = null)

    val EVENT_SAKURA = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                    (1..53).joinToString("") { "-" } +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "初イベント " +
                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                    "春の整地祭り" +
                    "${ChatColor.WHITE}" +
                    " を開催中!!" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "期間限定意志" +
                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                    " 桜の意志 " +
                    "${ChatColor.WHITE}" +
                    "を獲得して、特別なレリックを手に入れよう!!" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.WHITE}" +
                    "詳しくは" +
                    "${ChatColor.DARK_PURPLE}" +
                    "公式ディスコード" +
                    "${ChatColor.WHITE}" +
                    "をチェック!!" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.LIGHT_PURPLE}" +
                    (1..53).joinToString("") { "-" }
    ), 0L)

}