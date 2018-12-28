package click.seichi.gigantic.player.spell

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * @author tar0ss
 */
object SpellParameters {

    const val STELLA_CLAIR_AMOUNT_PERCENT = 7

    const val STELLA_CLAIR_PROBABILITY_PERCENT = 15

    // 1ブロック当たりのマナ消費量
    const val APOSTOL_MANA = 2.0

    // 幅，高さ，奥行それぞれの最大値
    const val APOSTOL_LIMIT_SIZE = 5

    // 最大同時破壊数 = maxMana * (5/100) / 2
    fun calcLimitOfBreakNumOfApostol(maxMana: BigDecimal): Int {
        return maxMana
                .divide(100.toBigDecimal(), RoundingMode.HALF_UP)
                .times(5.toBigDecimal())
                .divide(APOSTOL_MANA.toBigDecimal(), 10, RoundingMode.HALF_UP)
                .coerceAtMost(Math.pow(APOSTOL_LIMIT_SIZE.toDouble(), 3.0).toBigDecimal())
                .toInt()
    }

}