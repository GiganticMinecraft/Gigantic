package click.seichi.gigantic.player.level

import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.util.Polynomial
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class SeichiLevel(
        override val max: Int,
        private val mineBlockPolynomial: Polynomial,
        private val manaPolynomial: Polynomial
) : Level() {

    private val mineBlockMap = levelRange.map { it to mineBlockPolynomial.calculation(it) }.toMap()

    private val manaMap = levelRange.map {
        it to
                when (it) {
                    in 1 until 10 -> 0L
                    else -> manaPolynomial.calculation(it)
                }
    }.toMap()

    override fun canLevelUp(currentLevel: Int, player: Player) = player.gPlayer?.mineBlock ?: 0 >= mineBlockMap[currentLevel + 1] ?: Long.MAX_VALUE

    override fun calcLevel(player: Player) = levelRange.firstOrNull { !canLevelUp(it + 1, player) } ?: max

    fun getMaxMana(currentLevel: Int) = manaMap[currentLevel] ?: 0
}