package click.seichi.gigantic.player.spell

import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object SpellParameters {

    const val STELLA_CLAIR_AMOUNT_PERCENT = 7

    const val STELLA_CLAIR_PROBABILITY_PERCENT = 15

    const val APOSTOL_MANA = 2.0

    fun getApostolMaxBlockNum(player: Player): Int {
        val mana = player.find(CatalogPlayerCache.MANA) ?: return 1
        return mana.max.div(APOSTOL_MANA.toBigDecimal()).toInt()
    }
}