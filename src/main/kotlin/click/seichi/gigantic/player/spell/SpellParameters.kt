package click.seichi.gigantic.player.spell

import org.bukkit.Material

/**
 * @author tar0ss
 */
object SpellParameters {

    const val STELLA_CLAIR_AMOUNT_PERCENT = 7

    const val STELLA_CLAIR_PROBABILITY_PERCENT = 15

    const val GRAND_NATURA_MAX_RADIUS = 5

    const val EXPLOSION_MANA = 2.0

    val GRAND_NATURA_RELATIONAL_BLOCKS = setOf(
            Material.GRASS_BLOCK,
            Material.BROWN_MUSHROOM_BLOCK,
            Material.RED_MUSHROOM_BLOCK,
            Material.MUSHROOM_STEM
    )

    // 破壊の初期点の探索範囲
    const val AQUA_LINEA_MAX_DEPTH = 2

    const val AQUA_LINEA_MAX_COUNT = 5

    const val AQUA_LINEA_MAX_DISTANCE = 5

    const val AQUA_LINEA_MANA_PER_BLOCK = 3.0

    const val AQUA_LINEA_LINED_BREAK_INTERVAL = 5L

    const val AQUA_LINEA_RESTART_BREAK_INTERVAL = 25L

}