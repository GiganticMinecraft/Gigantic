package click.seichi.gigantic.spirit


import click.seichi.gigantic.spirit.summoncase.SummonCase
import org.bukkit.event.Event

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
//            RandomSummonCase(0.05, BlockBreakEvent::class.java) { event ->
//                if (event.isCancelled) return@RandomSummonCase
//                val player = event.player ?: return@RandomSummonCase
//                val gPlayer = player.gPlayer ?: return@RandomSummonCase
//                val aptitudeSet = gPlayer.aptitude.copySet()
//                val will = aptitudeSet.shuffled().firstOrNull() ?: return@RandomSummonCase
//                spawn(WillSpirit(WillSpawnReason.AWAKE, event.block.centralLocation, will, player))
//            },
//            RandomSummonCase(0.08, EntityDeathEvent::class.java) { event ->
//                val player = event.entity.killer ?: return@RandomSummonCase
//                val gPlayer = player.gPlayer ?: return@RandomSummonCase
//                val aptitudeSet = gPlayer.aptitude.copySet()
//                val will = aptitudeSet.shuffled().firstOrNull() ?: return@RandomSummonCase
//                spawn(WillSpirit(WillSpawnReason.RELEASE, event.entity.eyeLocation, will, player))
//            }
    )
    ;

    private val summonCaseSet = setOf(*summonCases)

    // TODO Bukkitを亡き者にし, ジェネリクスを蘇らせる
    fun <T : Event> summon(event: T) = summonCaseSet
            .firstOrNull { it.clazz == event::class.java }
            ?.summon(event)
            ?: Unit

}