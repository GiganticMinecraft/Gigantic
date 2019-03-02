package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LinedChatMessage
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.math.RoundingMode
import java.util.*

/**
 * @author tar0ss
 */
object AchievementMessages {

    val TUTORIAL_ALL = { player: Player ->
        ChatMessage(ChatMessageProtocol.CHAT,
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.BOLD}" +
                                "【速報】" +
                                "${ChatColor.YELLOW}" +
                                "${player.name}さん" +
                                "${ChatColor.WHITE}" +
                                "が" +
                                "${ChatColor.LIGHT_PURPLE}" +
                                " Lv200 " +
                                "${ChatColor.WHITE}" +
                                "を達成しました。" +
                                "おめでとうございます!"
                ))
    }

    val FIRST_JOIN = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}" +
                    "ブロックを壊そう" +
                    LinedChatMessage.NEW_LINE_SYMBOL +
                    "${ChatColor.GRAY}" +
                    "メニューからランダムテレポートができるぞ!!\n"
    ), 45L)

    val FIRST_JOIN_ALL = { player: Player ->
        ChatMessage(ChatMessageProtocol.CHAT,
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                                "${player.name}さん" +
                                "${ChatColor.WHITE}" +
                                "がサーバーに初参加です"
                ))
    }

    val FIRST_LEVEL_UP = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "ツールは破壊するブロックによって自動で切り替えられる" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.YELLOW}" +
                        "手動で切り替えたいときは\"f\" キー を押してみよう"
            }
    ), 45L)

    val UNLOCK_SKILL_MINE_COMBO = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "バフスキル: マインコンボ解禁" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: ブロックを連続破壊してコンボが切れないようにしよう！" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "コンボ数によって採掘速度が上昇するぞ！"
            }
    ), 45L)


    val UNLOCK_SKILL_MINE_BURST = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "バフスキル: マインバースト を覚えた" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: 少しの間だけ掘る速度が上昇"
            }
    ), 45L)

    val UNLOCK_FLASH = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "移動スキル: フラッシュ を覚えた" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: ブロックに向けて発動するとそのブロックの上にワープ"
            }
    ), 45L)

    val MANA_STONE = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.AQUA}" +
                        "マナストーンを見つけた" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "マナストーンを持った状態で魔法が使えるようになった" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.LIGHT_PURPLE}" +
                        "ツールを持ちながら右クリックしてみよう"
            }
    ), 45L)

    val UNLOCK_SPELL_STELLA_CLAIR = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "回復魔法: ステラ・クレア を覚えた" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: ブロックを破壊で最大マナの${Config.SKILL_HEAL_RATIO.toBigDecimal().setScale(1, RoundingMode.HALF_UP)}% を回復" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "通常破壊時に一定確率で発動する"
            }
    ), 45L)

    val UNLOCK_SPELL_MULTI_BREAK = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "破壊魔法: マルチ・ブレイク を覚えた" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "アクティブ効果: ブロック破壊時に，周囲のブロックも同時に破壊する" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "通常破壊時に発動する" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.AQUA}" +
                        "破壊範囲は魔法メニューから設定しよう"
            }
    ), 45L)

    val UNLOCK_SPELL_SKY_WALK = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "補助魔法: スカイ・ウォーク を覚えた" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: プレイヤーの移動を補助する足場を生成" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "スニークやジャンプを上手く使おう"
            }
    ), 45L)

    val UNLOCK_SPELL_LUNA_FLEX = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "補助魔法: ルナ・フレックス を覚えた" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "パッシブ効果: 自由に移動速度を変更" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "移動速度は魔法メニューから設定しよう"
            }
    ), 45L)

    val TELEPORT_PLAYER = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "プレイヤーテレポートが使えるようになった" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "メニューからテレポート先を選択"
            }
    ), 45L)

    val TELEPORT_HOME = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "好きな場所をテレポート先として登録できるようになった" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "テレポートメニューからホームを選択"
            }
    ), 45L)

    val TELEPORT_LAST_DEATH = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "ラストデステレポートが使えるようになった" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "最後に死亡した場所に戻ることができる" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "メニューから選択"
            }
    ), 45L)

    val QUEST_ORDER = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${Quest.COLOR}" +
                        "新しいクエストが届いた"
            }
    ), 45L)

    val QUEST_ORDER_FIRST = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${Quest.COLOR}" +
                        "初めてのクエストが届いた" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${Quest.COLOR}" +
                        "メニューからクエストを受注しよう"
            }
    ), 45L)

    val JUMP = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "大きくジャンプできるようになった" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "ジャンプアイテムを持ってスニークしてみよう"
            }
    ), 45L)


    val FOCUT_TOTEM = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "補助スキル: フォーカス・トーテム を覚えた!!" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "最大体力の半分以上のダメージを受けて体力が0になった時に、死から守ってくれる!!"
            }
    ), 45L)

    val SWORD = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.YELLOW}" +
                        "剣を扱えるようになった"
            }
    ), 45L)

    val WILL = { will: Will ->
        LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let {
                    it to "" + will.chatColor +
                            "${ChatColor.BOLD}" +
                            will.getName(it) +
                            "の意志 " +
                            "${ChatColor.RESET}${ChatColor.YELLOW}" +
                            "と交感できるようになった"
                }
        ), 45L)
    }
    val FIRST_PRE_SENSE = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.GRAY}" +
                        "初めての意志に出会った" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "ブロックを破壊すると，稀に意志が出現するようだ" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "たくさんの意志と出会って、意志との友好度を上げてみよう"
            }
    ), 45L)

    val FIRST_WILL = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.GRAY}" +
                        "友好度がMAXとなり、意志と交感できるようになった" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "意志が出現したら近付いてエーテルを獲得してみよう" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "エーテルを100個集めたら，メニューにあるレリック生成器を使って" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.GRAY}" +
                        "レリックを獲得できるぞ" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.YELLOW}" +
                        "投票特典が増えた"
            }
    ), 45L)

    val FIRST_ADVANCED_WILL = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.GRAY}" +
                        "より高度な意志に出会った" +
                        LinedChatMessage.NEW_LINE_SYMBOL +
                        "${ChatColor.YELLOW}" +
                        "投票特典が増えた"
            }
    ), 45L)

    val FIRST_RELIC = LinedChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE.let {
                it to "${ChatColor.AQUA}" +
                        "メニューから手に入れたレリックを確認しよう"
            }
    ), 45L)

}