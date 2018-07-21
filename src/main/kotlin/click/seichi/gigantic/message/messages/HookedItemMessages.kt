package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.components.MineBurst
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

    val MINE_BURST_LORE = { mineBurst: MineBurst ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.GRAY}" +
                                "${mineBurst.duration}秒間破壊速度上昇"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.AQUA}" +
                                "クールタイム : ${mineBurst.coolTime}秒"
                )
        )
    }
}