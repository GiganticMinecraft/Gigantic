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
                        "バフスキル: マインバースト を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: 少しの間だけ掘る速度が上昇!!\n" +
                        "\"3\" キー を押して発動!!\n"
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
                        "移動スキル: フラッシュ を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: ブロックに向けて発動するとそのブロックの上にワープ!!\n" +
                        "\"2\" キー を押して発動!!\n"
            }
    ))

    val UNLOCK_HEAL = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                        "回復スキル: ヒール を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: ブロックを破壊で最大体力の1%を回復!!\n" +
                        "通常破壊時に自動的に発動する\n"
            }
    ))

    val UNLOCK_SWITCH = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                        "切り替えスキル: スイッチ を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: 持ち物を入れ替える\n" +
                        "\"f\" キー を押して発動!!\n" +
                        "\"9\" キー を押して詳細設定を変更できる\n"
            }
    ))

    // TODO
    val UNLOCK_EXPLOSION = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                        "切り替えスキル: スイッチ を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: 持ち物を入れ替える\n" +
                        "\"f\" キー を押して発動!!\n" +
                        "\"9\" キー を押して詳細設定を変更できる\n"
            }
    ))


}