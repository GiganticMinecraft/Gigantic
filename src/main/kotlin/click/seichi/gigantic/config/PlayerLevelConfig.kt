package click.seichi.gigantic.config

import java.math.BigDecimal

/**
 * @author tar0ss
 */
object PlayerLevelConfig : SimpleConfiguration("level") {

    val MAX by lazy { getInt("max") }

    val LEVEL_MAP by lazy {
        (1..(MAX + 1)).map {
            it to BigDecimal(getString("level_to_exp.$it"))
        }.toMap()
    }

}