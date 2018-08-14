package click.seichi.gigantic.message.messages

import click.seichi.gigantic.cache.manipulator.manipulators.Flash
import click.seichi.gigantic.cache.manipulator.manipulators.MineBurst
import click.seichi.gigantic.message.LocalizedText
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

    val MINE_BURST_LORE = { mineburst: MineBurst ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.GRAY}" +
                                "${mineburst.duration}秒間破壊速度上昇"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.AQUA}" +
                                "クールタイム : ${mineburst.coolTime}秒"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.DARK_GRAY}" +
                                "\"3\" キー を押して発動"
                )
        )
    }

    val FLASH = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}" +
                    "フラッシュ"
    )

    val FLASH_LORE = { flash: Flash ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.GRAY}" +
                                "ブロックに向けて発動すると"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.GRAY}" +
                                "そのブロックに向けてテレポート"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.AQUA}" +
                                "クールタイム : ${flash.coolTime}秒"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.DARK_GRAY}" +
                                "\"2\" キー を押して発動"
                )
        )
    }

}