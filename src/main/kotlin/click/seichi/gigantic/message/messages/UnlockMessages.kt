package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.skill.SkillParameters
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object UnlockMessages {

    val UNLOCK_MINE_BURST = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "バフスキル: マインバースト を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: 少しの間だけ掘る速度が上昇!!\n" +
                        "\"${SkillParameters.MINE_BURST_KEY}\" キー を押して発動!!\n"
            }
    ))

    val UNLOCK_RAID_BATTLE = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.RED}" +
                        "レイドバトル解禁!!敵を倒してレアアイテムをゲット!!\n" +
                        "${ChatColor.GRAY}" +
                        "インベントリから敵を選択しよう\n" +
                        "ブロックを破壊することで敵を攻撃!!\n"
            }
    ))

    val UNLOCK_FLASH = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "移動スキル: フラッシュ を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: ブロックに向けて発動するとそのブロックの上にワープ!!\n" +
                        "\"${SkillParameters.FLASH_KEY}\" キー を押して発動!!\n"
            }
    ))

    val UNLOCK_HEAL = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "回復スキル: ヒール を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: ブロックを破壊で最大体力の${SkillParameters.HEAL_AMOUNT_PERCENT}%を回復!!\n" +
                        "通常破壊時に一定確率で発動する\n"
            }
    ))

    val UNLOCK_SWITCH = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "切り替えスキル: スイッチ を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: 持ち物を入れ替える\n" +
                        "\"${SkillParameters.SWITCH_KEY}\" キー を押して発動!!\n" +
                        "\"${SkillParameters.SWITCH_SETTING_KEY}\" キー を押して詳細設定を変更できる\n"
            }
    ))

    val UNLOCK_TERRA_DRAIN = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "回復魔法: テラドレイン を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: 木を倒し、自身の体力を回復する\n" +
                        "原木を破壊して発動!!\n" +
                        "${ChatColor.BLUE}" +
                        "---スニークで通常破壊"
            }
    ))


    val UNLOCK_WILL_O_THE_WISP = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "スキル: ウィルオウィスプ を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: ブロックを破壊すると、稀に遺志が現れるぞ!!\n" +
                        "遺志と交感することで記憶を獲得しよう\n" +
                        "${ChatColor.GRAY}" +
                        "遺志は${Will.values().size}種類存在する\n" +
                        "レベルを上げてたくさんの遺志と交感しよう!!\n"
            }
    ))

    val UNLOCK_MANA = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.AQUA}" +
                        "マナを使えるようになった!!"
            }
    ))

    val UNLOCK_STELLA_CLAIR = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "回復魔法: ステラクレア を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: ブロックを破壊で最大マナの${SkillParameters.HEAL_AMOUNT_PERCENT}% を回復!!\n" +
                        "通常破壊時に一定確率で発動する\n"
            }
    ))

    val UNLOCK_IGNIS_VOLCANO = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "破壊魔法: イグニス・ヴォルケーノ を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: 地表が焦土と化す\n" +
                        "芝生又はキノコブロックを通常破壊時に発動\n" +
                        "${ChatColor.BLUE}" +
                        "---スニークで通常破壊"
            }
    ))

    val UNLOCK_AQUA_LINEA = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "破壊魔法: アクア・リネーア を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: ブロックが泡となって消える\n" +
                        "通常破壊時に発動\n" +
                        "${ChatColor.BLUE}" +
                        "---スニークで通常破壊"
            }
    ))
}