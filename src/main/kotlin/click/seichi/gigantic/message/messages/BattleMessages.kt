package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.TitleMessage
import click.seichi.gigantic.monster.SoulMonster
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object BattleMessages {

    val WIN = { monster: SoulMonster ->
        ChatMessage(ChatMessageProtocol.ACTION_BAR,
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.YELLOW}" +
                                    monster.getName(it) +
                                    "を倒した!"
                        }
                ))
    }

    val SPAWN = { monster: SoulMonster ->
        ChatMessage(ChatMessageProtocol.ACTION_BAR,
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                                    monster.getName(it) +
                                    " が出現!!"
                        }
                ))
    }

    val DAMAGE = { monster: SoulMonster, damage: Long ->
        ChatMessage(ChatMessageProtocol.ACTION_BAR,
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                                    monster.getName(it) +
                                    " から $damage のダメージを受けた"
                        }
                ))
    }

    val FIRST_SPAWN = TitleMessage(null,
            LocalizedText(
                    Locale.JAPANESE to "近付いてバトル開始"
            ), 0, 60, 10)

    val FIRST_AWAKE = TitleMessage(null,
            LocalizedText(
                    Locale.JAPANESE to "ブロックを掘って攻撃"
            ), 0, 60, 10)

    val FIRST_DAMAGE = TitleMessage(null,
            LocalizedText(
                    Locale.JAPANESE to "敵の攻撃ブロックに触れて攻撃を防ごう"
            ), 0, 60, 10)

    val FIRST_BREAK_OTHER_CHUNK = TitleMessage(null,
            LocalizedText(
                    Locale.JAPANESE to "F3+Gでチャンクの境界を表示"
            ), 0, 60, 10)


}