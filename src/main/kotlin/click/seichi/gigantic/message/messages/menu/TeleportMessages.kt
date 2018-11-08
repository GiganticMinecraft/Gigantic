package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object TeleportMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "テレポート対象を選択"
    )

    val TELEPORT_TO_PLAYER = LocalizedText(
            Locale.JAPANESE to "プレイヤーを選択"
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
}