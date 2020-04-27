package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.Defaults
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object BagMessages {

    val PROFILE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.UNDERLINE}プロフィール"
    )

    val REST = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}${ChatColor.UNDERLINE}休憩する"
    )

    val BACK_FROM_REST = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.UNDERLINE}戻る"
    )

    val NORMAL_TEXTURE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}" +
                    "軽量化リソースパック: " +
                    "${ChatColor.YELLOW}" +
                    "OFF"
    )

    val LIGHT_TEXTURE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}" +
                    "軽量化リソースパック: " +
                    "${ChatColor.RED}" +
                    "ON"
    )

    val SERVER_RESOURCE_PACK = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.DARK_GRAY}" +
                            "ブロック破壊時のパーティクルを"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.DARK_GRAY}" +
                            "消します"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.DARK_GRAY}" +
                            "サーバーリソースパックを"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.YELLOW}" +
                            "有効" +
                            "${ChatColor.DARK_GRAY}" +
                            "にして下さい"
            )
    )

    val SPECIAL_THANKS_TITLE = LocalizedText(
            Locale.JAPANESE to "Special Thanks"
    )

    val QUEST = LocalizedText(
            Locale.JAPANESE to "クエスト"
    )

    val NO_QUEST = LocalizedText(
            Locale.JAPANESE to "クエストがありません"
    )

    val WILL = LocalizedText(
            Locale.JAPANESE to "意志"
    )

    val RELIC = LocalizedText(
            Locale.JAPANESE to "レリック"
    )

    val TELEPORT = LocalizedText(
            Locale.JAPANESE to "テレポート"
    )

    val SWITCH_DETAIL = LocalizedText(
            Locale.JAPANESE to "ツール切り替え設定"
    )

    val SWITCH_DETAIL_LORE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                    "\"f\" キー を押してツールを変更"
    )

    val VOTE_BONUS = LocalizedText(
            Locale.JAPANESE to "投票特典"
    )

    val TAKE_BONUS = LocalizedText(
            Locale.JAPANESE to "クリックして特典を受け取る"
    )

    val NO_BONUS = LocalizedText(
            Locale.JAPANESE to "受け取れる特典がありません"
    )


    val TO_TAKE_BONUS = LocalizedText(
            Locale.JAPANESE to "反映されない場合はプロフィールを更新して下さい"
    )

    val VOTE_PAGE_DESCRIPTION = LocalizedText(
            Locale.JAPANESE to "JMS投票ページにアクセス"
    )

    val VOTE_PAGE_MESSAGE = LocalizedText(
            Locale.JAPANESE to "JMS投票ページのURL↓ " + "${ChatColor.RED}${ChatColor.UNDERLINE}" + "https://minecraft.jp/servers/play.seichi.click"
    )

    val VOTE_BONUS_DESCRIPTION = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "JMSで投票すると以下の特典が受け取れます"
    )


    val VOTE_BONUS_FOR_ALL_PLAYER = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "- ${ChatColor.WHITE}${ChatColor.BOLD}" +
                            "${Defaults.VOTE_POINT_PER_VOTE}投票p"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "(エフェクトの購入に使用可能)"
            )
    )

    val VOTE_BONUS_FOR_BASIC_WILL = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "- ${ChatColor.YELLOW}${ChatColor.BOLD}" +
                            "${Defaults.VOTE_BONUS_ETHEL}エーテル x ${Defaults.VOTE_BONUS_BASIC_WILL_NUM}"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "(土･水･火･自然･空の中から${Defaults.VOTE_BONUS_BASIC_WILL_NUM}種類)"
            )
    )

    val VOTE_BONUS_FOR_BASIC_WILL_HIDE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "- ${ChatColor.YELLOW}" +
                    "未解禁特典1(一定条件を満たすと解禁)"
    )

    val VOTE_BONUS_FOR_ADVANCED_WILL = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "- ${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "${Defaults.VOTE_BONUS_ETHEL}エーテル x ${Defaults.VOTE_BONUS_ADVANCED_WILL_NUM}"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "(氷･地･風･光･闇の中から${Defaults.VOTE_BONUS_ADVANCED_WILL_NUM}種類)"
            )
    )

    val VOTE_BONUS_FOR_ADVANCED_WILL_HIDE = LocalizedText(
            Locale.JAPANESE to "- ${ChatColor.LIGHT_PURPLE}" +
                    "未解禁特典2(一定条件を満たすと解禁)"
    )

    val VOTE_BONUS_CAUTION = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "※エーテルは交感できる意志の中から"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "ランダムに選択されます"
            )
    )

    val VOTE_BONUS_CONFIRM = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}" +
                            "受け取りますか？"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "(受け取る場合はもう一度クリック)"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "※5秒間待つと中断します"
            )
    )

    val VOTE_BONUS_RECONFIRM = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}" +
                            "受け取りました。もう一回受け取りますか？"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "(受け取る場合はもう一度クリック)"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "※5秒間待つと中断します"
            )
    )

    val VOTE_BONUS_NOTFOUND =  LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "受け取れる特典はありません"
    )


    val VOTE_BONUS_RECIVE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "受け取りました。"
    )

    val WIKI_DESCRIPTION = LocalizedText(
            Locale.JAPANESE to "Wikiにアクセス"
    )

    val WIKI_MESSAGE_OFFICIAL = LocalizedText(
            Locale.JAPANESE to "公式WikiのURL: " + "${ChatColor.RED}${ChatColor.UNDERLINE}" + "https://www.seichi.network/spring"
    )

    val WIKI_MESSAGE_UNOFFICIAL = LocalizedText(
            Locale.JAPANESE to "非公式WikiのURL: " + "${ChatColor.RED}${ChatColor.UNDERLINE}" + "https://springseichi.sokuhou.wiki"
    )


    val RANKING = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GOLD}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                    "ランキング"
    )

}