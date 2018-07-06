package click.seichi.gigantic.spirit.summoncase

import click.seichi.gigantic.event.events.ScheduleSummoningEvent
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.isCrust
import click.seichi.gigantic.extension.isSurface
import click.seichi.gigantic.util.Random
import org.bukkit.Bukkit
import org.bukkit.Location

/**
 * @author unicroak
 */
class ScheduledSummonCase(
        private val summonRate: Double,
        private val ratedSummoning: (Location) -> Unit
) : SummonCase<ScheduleSummoningEvent>(ScheduleSummoningEvent::class.java) {

    companion object {
        private const val TOP = 256
        private const val BOTTOM = 5
        private const val WIDTH = 16
    }

    override val summoning: (ScheduleSummoningEvent) -> Unit = {
        Bukkit.getWorlds()
                .mapNotNull { it.loadedChunks }
                .flatMap { it.toList() }
                .filter { Random.nextDouble() < summonRate }
                .mapNotNull { chunk ->
                    (BOTTOM until TOP).mapNotNull { y ->
                        chunk.getBlock(Random.nextInt(WIDTH), y, Random.nextInt(WIDTH)).takeIf { it.isSurface && it.isCrust }
                    }
                }.flatMap { it.toList() }
                .map { it.centralLocation.add(0.0, Random.nextDouble(1.5, 3.5), 0.0) }
                .shuffled()
                .firstOrNull()
                ?.let { ratedSummoning(it) }
    }

}