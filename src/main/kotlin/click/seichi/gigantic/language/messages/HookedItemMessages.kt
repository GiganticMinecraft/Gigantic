package click.seichi.gigantic.language.messages

import click.seichi.gigantic.language.LocalizedText
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