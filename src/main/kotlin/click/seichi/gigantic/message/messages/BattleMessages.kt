package click.seichi.gigantic.message.messages

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.cache.manipulator.manipulators.Health
import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.SideBarMessage
import click.seichi.gigantic.raid.RaidBattle
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.util.SideBarRow
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object BattleMessages {

    val DEFEAT_BOSS = { boss: Boss ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let {
                    it to
                            "${ChatColor.RED}${ChatColor.BOLD}" +
                            "${boss.localizedName.asSafety(it)}を倒した!!"
                }
        ))
    }

    val GET_RELIC = { relic: Relic ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let {
                    it to
                            "${ChatColor.RED}${ChatColor.BOLD}" +
                            "${relic.localizedName.asSafety(it)}を手に入れた!!"
                }
        ))
    }

    val BONUS_EXP = { bonus: Long ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE to "${ChatColor.RED}${ChatColor.BOLD}" +
                        "${bonus}のボーナス経験値を得た!!"

        ))
    }

    val BATTLE_INFO = { raidBattle: RaidBattle, health: Health, remainTime: Long, isForced: Boolean ->
        val afterHealth = health.current.minus(raidBattle.boss.attackDamage).coerceAtLeast(0L)
        SideBarMessage(
                "battle",
                MenuMessages.BATTLE_BUTTON_TITLE(raidBattle.boss),
                mutableMapOf(
                        SideBarRow.TWO to LocalizedText(
                                Locale.JAPANESE to "${ChatColor.RED}" +
                                        "敵の攻撃力 " +
                                        "${ChatColor.WHITE}" +
                                        ": ${raidBattle.boss.attackDamage}"
                        ),
                        SideBarRow.THREE to LocalizedText(
                                Locale.JAPANESE to "${ChatColor.RED}" +
                                        "次の攻撃まで " +
                                        "${ChatColor.WHITE}" +
                                        ": ${remainTime}秒"
                        )
                ).apply {
                    if (afterHealth == 0L) {
                        put(SideBarRow.FOUR,
                                LocalizedText(
                                        Locale.JAPANESE to "${ChatColor.RED}${ChatColor.BOLD}" +
                                                "警告!!次の攻撃で死亡!!"
                                )
                        )
                    }
                }
                , isForced
        )
    }

}