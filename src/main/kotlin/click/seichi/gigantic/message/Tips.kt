package click.seichi.gigantic.message

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.extension.wrappedLevel
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
                            "整地鯖(春)のDiscordに参加してみんなで会話を楽しもう！" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.AQUA}" +
                            "https://discord.gg/nmhjtC5"
            ), 60L)),
    COMBO(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "コンボは" +
                            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "10秒" +
                            "${ChatColor.WHITE}" +
                            "経つと減少していっちゃうよ！気を付けよう！"
            ), 60L), { Achievement.SKILL_MINE_COMBO.isGranted(it) }),
    ETHEL(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "エーテルは" +
                            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "100個" +
                            "${ChatColor.WHITE}" +
                            "集めるとレリックに変換できるよ！" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "たくさん変換して経験値効率を上げよう！"
            ), 60L), { Achievement.FIRST_WILL.isGranted(it) }),
    SERVER_MAP(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "みんな大好きサーバマップ" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.AQUA}" +
                            "http://map.spring.seichi.click/"
            ), 60L)),
    CAUTION_1(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "整地鯖(春)に関するお問い合わせを" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ギガンティック☆整地鯖運営チームに行わないでください。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "整地鯖(春)に関するご質問は" +
                            "${ChatColor.DARK_PURPLE}${ChatColor.BOLD}" +
                            "公式Discord" +
                            "${ChatColor.WHITE}" +
                            "で受け付けております。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.AQUA}" +
                            "https://discord.gg/nmhjtC5"
            ), 60L)),
    VOTE(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "投票すると投票ポイントをGETできます。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "溜まった投票ポイントはお好みのエフェクトと交換しよう！" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ギガンティック☆整地鯖のJMSページより投票してください。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ギガンティック☆整地鯖,整地鯖(春)双方で投票特典が利用可能です。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.AQUA}" +
                            "https://minecraft.jp/servers/54d3529e4ddda180780041a7"
            ), 60L)),
    OPTIFINE(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "OptiFineを導入すると大体の環境で動作が軽くなります。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "（お友達と一緒に近くを整地する時は必須カモ）" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.AQUA}" +
                            "https://optifine.net/downloads"
            ), 60L)),
    WIKI(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "有志の方がWikiを作成してくださいました。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ゲームプレイのお供にいかがですか？" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "※非公式Wikiの内容は整地鯖(春)運営チームに承認されたものではなく，" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "記載内容の正確性も保証しておりませんのでご留意ください。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.AQUA}" +
                            "https://springseichi.sokuhou.wiki/"
            ), 60L)),
    TWITTER(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "公式Twitterアカウントをフォローしてください!!" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "最新のアップデート内容やお知らせを見ることができます。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.AQUA}" +
                            "https://twitter.com/springseichi"
            ), 60L)),
    DONATION(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "当サーバでは、寄付を受け付けております。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.AQUA}" +
                            "https://goo.gl/forms/8ZR3MJwtSeTDkGST2"
            ), 60L)),
    HOME(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ホーム機能を使ってみよう！" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "お気に入りの場所を登録できるぞ！"
            ), 60L), { Achievement.TELEPORT_HOME.isGranted(it) }),
    ADMIN_CAUTION(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "運営チームのなりすましにご注意ください。" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "運営チームのMinecraftIDは\n" +
                            Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "公式Discord #はじめに に全て公開されています。"
            ), 60L)),
    FRIEND(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "友達と一緒に掘りたいときは，フォロー機能を使おう！！" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "相互フォローになれば，近くのブロックも掘れるようになるぞ!"
            ), 60L)),
    MUTE(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "暴言やスパムを行ったり，執拗に付き纏ってくる人には\n" +
                            Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ミュート機能を使おう!!" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ミュートすると，自分へのテレポートを拒否できて，\n" +
                            Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "その人のチャットを非表示にできるぞ!!"
            ), 60L)),
    SETTINGS(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "メニューの詳細設定→表示設定からいくつかの表示を切り替えられる!!"
            ), 60L)),
    STUCK(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "地形にハマって出られないときは，テレポートメニューから\n" +
                            Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "初期スポーンに戻ろう!!"
            ), 60L)),
    VOTE_ON_NOT_LOGIN(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ログインせずに投票しても特典は配布されます。"
            ), 60L)),
    LOBBY(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ロビーサーバへの移動は" +
                            "${ChatColor.AQUA}" +
                            "/hub" +
                            "${ChatColor.WHITE}" +
                            "を使おう!!"
            ), 60L)),
    MULTI_BREAK(LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "魔法 " +
                            "${ChatColor.LIGHT_PURPLE}" +
                            "マルチ・ブレイク" +
                            "${ChatColor.WHITE}" +
                            "が使えない?" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "☆マークが赤くなっていたらマナが不足しているよ!!" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "マナストーンは手に持ってるかな？\n" +
                            Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "ツールを持ちながら右クリックしてみよう!!" +
                            LinedChatMessage.NEW_LINE_SYMBOL + Defaults.TIPS_PREFIX +
                            "${ChatColor.WHITE}" +
                            "破壊範囲の設定はしたかな？魔法メニューから設定しよう!!"
            ), 60L), { Achievement.SPELL_MULTI_BREAK.isGranted(it) && it.wrappedLevel < 30 }),

    ;

    fun sendTo(player: Player) {
        if (sendingCondition(player))
            linedMessage.sendTo(player)
    }
}