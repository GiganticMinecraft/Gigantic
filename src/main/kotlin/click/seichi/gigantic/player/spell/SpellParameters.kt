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

    // 最大同時破壊数 = maxMana * (5/100) / 2
    fun calcLimitOfBreakNumOfApostol(maxMana: BigDecimal): Int {
        return maxMana
                .setScale(5)
                .divide(100.toBigDecimal(), 10, RoundingMode.HALF_UP)
                .times(5.toBigDecimal())
                .divide(APOSTOL_MANA.toBigDecimal(), 10, RoundingMode.HALF_UP)
                .coerceAtMost(Integer.MAX_VALUE.toBigDecimal())
                .toInt()
    }

}