package click.seichi.gigantic.spirit


import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.spirit.SpiritManager.spawn
import click.seichi.gigantic.spirit.spawnreason.WillSpawnReason
import click.seichi.gigantic.spirit.spirits.WillSpirit
import click.seichi.gigantic.spirit.summoncase.RandomSummonCase
import click.seichi.gigantic.spirit.summoncase.SummonCase
import org.bukkit.event.Event
import org.bukkit.event.block.BlockBreakEvent

/**
 * @author unicroak
 * @author tar0ss
 *
 * if you want to summon spirit on a event,you need to listen that event yourself.
 * ex)
 *   @EventHandler
 *   fun onBlockBreak(event: BlockBreakEvent) {
 *      event.summonSpirit()
 *   }
 */
enum class SpiritType(vararg summonCases: SummonCase<*>) {

    WILL(
            RandomSummonCase(0.05, BlockBreakEvent::class.java) { event ->
                if (event.isCancelled) return@RandomSummonCase
                if (event.player.gameMode != org.bukkit.GameMode.SURVIVAL) return@RandomSummonCase
                val player = event.player ?: return@RandomSummonCase
                val aptitudeSet = player.find(CatalogPlayerCache.APTITUDE)?.copySet() ?: return@RandomSummonCase
                val will = aptitudeSet.shuffled().firstOrNull() ?: return@RandomSummonCase
                spawn(WillSpirit(WillSpawnReason.AWAKE, event.block.centralLocation, will, player))
            }
    )
    ;

    private val summonCaseSet = setOf(*summonCases)

    // TODO Bukkitを亡き者にし, ジェネリクスを蘇らせる
    fun <T : Event> summon(event: T) = summonCaseSet
            .firstOrNull { it.clazz == event::class.java }
            ?.summon(event)
            ?: Unit

}