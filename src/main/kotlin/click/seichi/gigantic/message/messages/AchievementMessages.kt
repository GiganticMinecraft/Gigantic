package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.skill.SkillParameters
import click.seichi.gigantic.quest.Quest
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object AchievementMessages {

    val FIRST_JOIN = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}" +
                    "ブロックを壊そう!!\n" +
                    "${ChatColor.GRAY}" +
                    "メニューからランダムテレポートができるぞ!!\n"
    ))

    val FIRST_LEVEL_UP = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "\"f\" キー を押してツールを入れ替えよう"
            }
    ))

    val MINE_COMBO = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "マインコンボ解禁!!\n" +
                        "${ChatColor.GRAY}" +
                        "ブロックを連続破壊してコンボが切れないようにしよう！\n"
            }
    ))


    val SKILL_MINE_BURST = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "バフスキル: マインバースト を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: 少しの間だけ掘る速度が上昇!!\n"
            }
    ))

    val UNLOCK_FLASH = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "移動スキル: フラッシュ を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: ブロックに向けて発動するとそのブロックの上にワープ!!\n"
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


    val UNLOCK_KODAMA_DRAIN = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "回復スキル: コダマ・ドレイン を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: 木を破壊して自身の体力を割合回復!!\n" +
                        "原木を通常破壊時に発動!!\n"
            }
    ))


    val UNLOCK_WILL_BASIC_1 = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "意志と交感できるようになった!!\n" +
                        "${ChatColor.GRAY}" +
                        "ブロックを破壊すると、稀に遺志が現れるぞ!!\n" +
                        "遺志と交感することで記憶を獲得しよう\n"
            }
    ))

    val MANA_STONE = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.AQUA}" +
                        "マナストーンを見つけた!!\n" +
                        "${ChatColor.GRAY}" +
                        "マナストーンを持った状態で魔法が使えるようになった!!\n" +
                        "右クリックでマナストーンを持ってみよう!!"
            }
    ))

    val UNLOCK_STELLA_CLAIR = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "回復魔法: ステラ・クレア を覚えた!!\n" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: ブロックを破壊で最大マナの${SkillParameters.HEAL_AMOUNT_PERCENT}% を回復!!\n" +
                        "通常破壊時に一定確率で発動する\n"
            }
    ))

    val UNLOCK_GRAND_NATURA = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "破壊魔法: グランド・ナトラ を覚えた!!\n" +
                        "\"スキル\"メニューで有効化しよう!!" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: 植物を吸収する\n" +
                        "芝生又はキノコブロックを通常破壊時に発動\n" +
                        "${ChatColor.BLUE}" +
                        "---スニークで通常破壊"
            }
    ))

    val UNLOCK_AQUA_LINEA = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "破壊魔法: アクア・リネーア を覚えた!!\n" +
                        "\"スキル\"メニューで有効化しよう!!" +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: ブロックが泡となって消える\n" +
                        "通常破壊時に発動\n" +
                        "${ChatColor.BLUE}" +
                        "---スニークで通常破壊"
            }
    ))

    val TELEPORT_PLAYER = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "プレイヤーテレポートが使えるようになった!!\n" +
                        "${ChatColor.GRAY}" +
                        "メニューからテレポート先を選択!!\n"
            }
    ))

    val TELEPORT_LAST_DEATH = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "ラストデステレポートが使えるようになった!!\n" +
                        "${ChatColor.GRAY}" +
                        "最後に死亡した場所に戻ることができる!!\n" +
                        "メニューから選択!!\n"
            }
    ))

    val QUEST_ORDER = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${Quest.COLOR}" +
                        "新しいクエストが届いた\n"
            }
    ))

    val QUEST_ORDER_FIRST = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${Quest.COLOR}" +
                        "初めてのクエストが届いた\n" +
                        "メニューからクエストを受注しよう!!"
            }
    ))

}