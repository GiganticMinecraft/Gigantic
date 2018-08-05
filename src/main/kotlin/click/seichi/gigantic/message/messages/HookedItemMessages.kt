package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.skill.SkillTimer
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object HookedItemMessages {

    val PICKEL = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.WHITE}" +
                    "ピッケル"
    )

    val SPADE = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.WHITE}" +
                    "シャベル"
    )

    val AXE = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.WHITE}" +
                    "斧"
    )

    val MINE_BURST = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}" +
                    "マインバースト"
    )

    val MINE_BURST_LORE = { mineBurstTimer: SkillTimer ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.GRAY}" +
                                "${mineBurstTimer.duration}秒間破壊速度上昇"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.AQUA}" +
                                "クールタイム : ${mineBurstTimer.coolTime}秒"
                )
        )
    }
}