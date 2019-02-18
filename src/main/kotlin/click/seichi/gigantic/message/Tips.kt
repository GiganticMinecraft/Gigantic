package click.seichi.gigantic.message

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.player.Defaults
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
enum class Tips(
        private val linedMessage: LinedChatMessage,
        private val sendingCondition: (Player) -> Boolean = { true }
) {
    DISCORD(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "整地鯖(春)Discord->" +
                            "${ChatColor.AQUA}" +
                            "https://discord.gg/nmhjtC5"
            ), 2L)),
    COMBO(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "コンボは" +
                            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "10秒" +
                            "${ChatColor.WHITE}" +
                            "経つと減少していっちゃうよ！気を付けよう！"
            ), 2L), { Achievement.SKILL_MINE_COMBO.isGranted(it) }),
    ETHEL(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "エーテルは" +
                            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "100個" +
                            "${ChatColor.WHITE}" +
                            "集めるとレリックに変換できるよ！"
            ), 2L), { Achievement.FIRST_WILL.isGranted(it) }),
    SERVER_MAP(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "みんな大好きサーバマップ->" +
                            "${ChatColor.AQUA}" +
                            "http://map.spring.seichi.click/"
            ), 2L)),
    CAUTION_1(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "整地鯖(春)に関するご質問->" +
                            "${ChatColor.AQUA}" +
                            "https://discord.gg/nmhjtC5"
            ), 2L)),
    VOTE(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "投票ポイントを貯めて、お好みのエフェクトと交換しよう！" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.AQUA}" +
                            "https://minecraft.jp/servers/54d3529e4ddda180780041a7"
            ), 2L)),
    OPTIFINE(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "OptiFineを導入すると大体の環境で動作が軽くなります。" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "ダウンロード→${ChatColor.AQUA}" +
                            "https://optifine.net/downloads"
            ), 2L)),
    WIKI(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "非公式Wikiあります→" +
                            "${ChatColor.AQUA}" +
                            "https://springseichi.sokuhou.wiki/"
            ), 2L)),
    TWITTER(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "公式Twitterアカウント->" +
                            "${ChatColor.AQUA}" +
                            "https://twitter.com/springseichi"
            ), 2L)),
    DONATION(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "当サーバでは、寄付を受け付けております。" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "寄付はこちらから→${ChatColor.AQUA}" +
                            "https://goo.gl/forms/8ZR3MJwtSeTDkGST2"
            ), 2L)),
    HOME(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ホーム機能でお気に入りの場所を登録!!(テレポートメニューから)"
            ), 2L), { Achievement.TELEPORT_HOME.isGranted(it) }),
    ADMIN_CAUTION(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "運営チームのなりすましにご注意ください。"
            ), 2L)),
    FRIEND(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "相互フォローになれば，近くのブロックも掘れるようになるぞ!"
            ), 2L)),
    MUTE(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ミュート機能で快適整地ライフ!!フォロー設定から利用できるぞ!!"
            ), 2L)),
    SETTINGS(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "メニューの詳細設定→表示設定からいくつかの表示を切り替えられる!!"
            ), 2L)),
    STUCK(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "地形にハマったときは，テレポートメニューから初期スポーンへGO!!"
            ), 2L)),
    VOTE_ON_NOT_LOGIN(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ログインせずに投票しても特典は配布されます。"
            ), 2L)),
    LOBBY(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ロビーサーバへの移動は" +
                            "${ChatColor.AQUA}" +
                            "/hub" +
                            "${ChatColor.WHITE}" +
                            "を使おう!!"
            ), 2L)),

    ;

    fun sendTo(player: Player) {
        if (sendingCondition(player))
            linedMessage.sendTo(player)
    }
}