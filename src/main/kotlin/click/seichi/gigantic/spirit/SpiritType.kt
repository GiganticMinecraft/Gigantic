package click.seichi.gigantic.spirit


import click.seichi.gigantic.event.events.BlockBreakSkillEvent
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.spirit.SpiritManager.spawn
import click.seichi.gigantic.spirit.spawnreason.WillSpawnReason
import click.seichi.gigantic.spirit.spirits.WillSpirit
import click.seichi.gigantic.spirit.summoncase.RandomSummonCase
import click.seichi.gigantic.spirit.summoncase.ScheduledSummonCase
import click.seichi.gigantic.spirit.summoncase.SummonCase
import click.seichi.gigantic.util.Random
import org.bukkit.event.Event
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent

/**
 * @author unicroak
 * @author tar0ss
 */
enum class SpiritType(vararg summonCases: SummonCase<*>) {

    WILL(
            ScheduledSummonCase(1.0/*0.0625*/) {
                spawn(WillSpirit(WillSpawnReason.APPEARANCE, it, Random.nextWill()))
            },
            RandomSummonCase(1.0/*0.05*/, BlockBreakEvent::class.java) { event ->
                spawn(WillSpirit(WillSpawnReason.AWAKE, event.block.centralLocation, Random.nextWill()))
            },
            RandomSummonCase(1.0/*0.01*/, BlockBreakSkillEvent::class.java) { event ->
                spawn(WillSpirit(WillSpawnReason.AWAKE, event.block.centralLocation, Random.nextWill()))
            },
            RandomSummonCase(1.0/*0.08*/, EntityDeathEvent::class.java) { event ->
                spawn(WillSpirit(WillSpawnReason.RELEASE, event.entity.eyeLocation, Random.nextWill()))
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