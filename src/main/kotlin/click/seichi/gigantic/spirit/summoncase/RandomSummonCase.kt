package click.seichi.gigantic.spirit.summoncase

import click.seichi.gigantic.util.Random
import org.bukkit.event.Event

/**
 * @author unicroak
 */
class RandomSummonCase<T : Event>(
        private val summonRate: Double,
        clazz: Class<T>,
        private val ratedSummoning: (T) -> Unit
) : SummonCase<T>(clazz) {

    override val summoning: (T) -> Unit = {
        if (Random.nextDouble() < summonRate) {
            ratedSummoning(it)
        }
    }

}