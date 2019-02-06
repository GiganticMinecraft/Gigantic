package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object TeleportMessages {

    val CANT_TELEPORT = ChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(Locale.JAPANESE to "${ChatColor.RED}" +
                    "テレポートできる場所がありません"))

    val TITLE = LocalizedText(
            Locale.JAPANESE to "テレポート先を選択"
    )

    val TELEPORT_TO_PLAYER = LocalizedText(
            Locale.JAPANESE to "プレイヤーを選択"
    )

    val TELEPORT_TO_HOME = LocalizedText(
            Locale.JAPANESE to "ホームを選択"
    )

    val REGISTER_HOME = LocalizedText(
            Locale.JAPANESE to "ホームを登録する"
    )

    val CLICK_TO_TELEPORT_HOME = LocalizedText(
            Locale.JAPANESE to "クリックしてテレポート"
    )

    val CHANGE_NAME_LORE = LocalizedText(
            Locale.JAPANESE to "で名前を変更可能"
    )

    val HOME_DELETE_LORE = LocalizedText(
            Locale.JAPANESE to "右クリック2回で削除"
    )

    val HOME_NOT_SURVIVAL = LocalizedText(
            Locale.JAPANESE to "休憩中は操作不可"
    )

    val HOME_DELETE = LocalizedText(
            Locale.JAPANESE to "もう一度右クリックで削除"
    )

    val TELEPORT_TO_RANDOM_CHUNK = LocalizedText(
            Locale.JAPANESE to "ランダムな場所へ"
    )

    val TELEPORT_TO_SPAWN = LocalizedText(
            Locale.JAPANESE to "初期地点に戻る"
    )

    val TELEPORT_TO_LAST_DEATH = LocalizedText(
            Locale.JAPANESE to "最後に死亡した場所へ"
    )

    val TELEPORT_TO_LAST_BREAK = LocalizedText(
            Locale.JAPANESE to "最後に整地した場所へ"
    )

    val TELEPORT_TO_PLAYER_TITLE = LocalizedText(
            Locale.JAPANESE to "テレポートするプレイヤーを選ぶ"
    )

    val TELEPORT_PLAYER_INVALID_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED} プレイヤーが存在しません"
            )
    )

    val TELEPORT_PLAYER_AFK_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED} 休憩中のためテレポート不可"
            )
    )

    val TELEPORT_PLAYER_NOT_SURVIVAL_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED} プレイヤーがサバイバルモードではありません"
            )
    )

    val TELEPORT_PLAYER_INVALID_WORLD_LORE = { world: World ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.RED} 別のワールド(${world.name})にいます"
                )
        )
    }

    val TELEPORT_PLAYER_FLYING_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED} Fly中のためテレポート不可"
            )
    )

    val TELEPORT_PLAYER_TOGGLE_OFF_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED} テレポートが許可されていません"
            )
    )

    val TELEPORT_PLAYER_LORE = { to: Player ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.WHITE} ワールド: ${to.world.name}"
                )
        )
    }

    val TELEPORT_TOGGLE_ON = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}他プレイヤーのテレポートを常に許可"
    )

    val TELEPORT_TOGGLE_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}----------------"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
            )
    )

    val TELEPORT_TOGGLE_OFF = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}他プレイヤーのテレポートを常に拒否"
    )

    val RANDOM_TELEPORT_IN_BREAK_TIME = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "休憩中はランダムテレポートできません"
    )

}