package click.seichi.gigantic.spirit


import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.battle.BattleManager
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.findBattle
import click.seichi.gigantic.extension.isBattled
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.spirit.SpiritManager.spawn
import click.seichi.gigantic.spirit.spawnreason.MonsterSpawnReason
import click.seichi.gigantic.spirit.spawnreason.WillSpawnReason
import click.seichi.gigantic.spirit.spirits.MonsterSpirit
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
                val player = event.player ?: return@RandomSummonCase
                if (!Achievement.WILL_BASIC_1.isGranted(player)) return@RandomSummonCase
                val aptitudeSet = player.find(CatalogPlayerCache.APTITUDE)?.copySet() ?: return@RandomSummonCase
                val will = aptitudeSet.shuffled().firstOrNull() ?: return@RandomSummonCase
                spawn(WillSpirit(WillSpawnReason.AWAKE, event.block.centralLocation, will, player))
            }
    ),
    MONSTER(
            // TODO rebase to 0.02
            RandomSummonCase(1.00, BlockBreakEvent::class.java) { event ->
                if (event.isCancelled) return@RandomSummonCase
                val player = event.player ?: return@RandomSummonCase
                val chunk = event.block.chunk ?: return@RandomSummonCase
                if (!Achievement.QUEST.isGranted(player)) return@RandomSummonCase
                // 他のバトルに参加している場合は終了
                if (player.findBattle() != null) return@RandomSummonCase
                // チャンクがバトル中なら終了
                if (chunk.isBattled) return@RandomSummonCase
                val quest = Quest.getOrderedList(player)
                        .filter { it.monsterList.isNotEmpty() }
                        .shuffled()
                        .firstOrNull() ?: return@RandomSummonCase
                val client = quest.getClient(player) ?: return@RandomSummonCase
                val monster = quest.monsterList.getOrNull(client.processedDegree) ?: return@RandomSummonCase
                val spirit = MonsterSpirit(MonsterSpawnReason.AWAKE, BattleManager.newBattle(chunk, player, monster))
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