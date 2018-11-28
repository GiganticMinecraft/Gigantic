package click.seichi.gigantic.spirit


import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.spirit.SpiritManager.spawn
import click.seichi.gigantic.spirit.spawnreason.MonsterSpawnReason
import click.seichi.gigantic.spirit.spirits.QuestMonsterSpirit
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
                if (!event.block.isCrust && !event.block.isTree) return@RandomSummonCase
                return@RandomSummonCase
                // TODO implements
                /*val aptitudeSet = player.find(CatalogPlayerCache.APTITUDE)?.copySet() ?: return@RandomSummonCase
                val will = aptitudeSet.shuffled().firstOrNull() ?: return@RandomSummonCase
                spawn(WillSpirit(WillSpawnReason.AWAKE, event.block.centralLocation, will, player))*/
            }
    ),
    MONSTER(
            RandomSummonCase(0.2, BlockBreakEvent::class.java) { event ->
                val player = event.player ?: return@RandomSummonCase
                if (!event.block.isCrust && !event.block.isTree) return@RandomSummonCase
                val chunk = event.block.chunk ?: return@RandomSummonCase
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