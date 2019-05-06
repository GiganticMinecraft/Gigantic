package click.seichi.gigantic.spirit


import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.config.DebugConfig
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.spirit.SpiritManager.spawn
import click.seichi.gigantic.spirit.spawnreason.MonsterSpawnReason
import click.seichi.gigantic.spirit.spawnreason.WillSpawnReason
import click.seichi.gigantic.spirit.spirits.QuestMonsterSpirit
import click.seichi.gigantic.spirit.spirits.WillSpirit
import click.seichi.gigantic.spirit.summoncase.RandomSummonCase
import click.seichi.gigantic.spirit.summoncase.SummonCase
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import org.bukkit.event.Event
import org.bukkit.event.block.BlockBreakEvent
import kotlin.random.asKotlinRandom

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
            RandomSummonCase(
                    if (Config.DEBUG_MODE && DebugConfig.WILL_SPIRIT) 1.00
                    else Defaults.WILL_SPAWN_PROBABILITY,
                    BlockBreakEvent::class.java
            ) { event ->
                val player = event.player
                val block = event.block
                if (!block.isCrust && !block.isTree) return@RandomSummonCase
                val will = Will.values()
                        .filter { player.hasAptitude(it) }
                        .filter { it.canSpawn(player, block) }
                        .toSet().apply {
                            if (isEmpty()) return@RandomSummonCase
                        }.random(Random.generator.asKotlinRandom())
                val spawnLocation = event.block.centralLocation.add(0.0, 0.3, 0.0)
                spawn(WillSpirit(WillSpawnReason.AWAKE, spawnLocation, will, player))
            }
    ),
    MONSTER(
            RandomSummonCase(0.2, BlockBreakEvent::class.java) { event ->
                val player = event.player
                if (!event.block.isCrust && !event.block.isTree) return@RandomSummonCase
                val chunk = event.block.chunk
                if (!Achievement.QUEST.isGranted(player)) return@RandomSummonCase
                // 他のバトルに参加している場合は終了
                if (player.findBattle() != null) return@RandomSummonCase
                // 他のバトルスピリットが出現している場合は終了
                if (player.getOrPut(Keys.BATTLE_SPIRIT) != null) return@RandomSummonCase
                // チャンクがバトル中なら終了
                if (chunk.isBattled) return@RandomSummonCase
                val quest = Quest.getProcessedList(player).firstOrNull { it.monsterList.isNotEmpty() }
                        ?: return@RandomSummonCase
                val client = quest.getClient(player) ?: return@RandomSummonCase
                val monster = quest.monsterList.getOrNull(client.processedDegree) ?: return@RandomSummonCase
                val spirit = QuestMonsterSpirit(MonsterSpawnReason.AWAKE, chunk, player, monster, quest)
                spawn(spirit)
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