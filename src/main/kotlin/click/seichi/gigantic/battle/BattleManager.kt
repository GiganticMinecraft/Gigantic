package click.seichi.gigantic.battle

import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.quest.Quest
import org.bukkit.Chunk
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object BattleManager {

    private val battleSet: MutableSet<Battle> = mutableSetOf()

    fun newBattle(chunk: Chunk, spawner: Player, players: Set<Player>, soulMonster: SoulMonster, quest: Quest? = null): Battle {
        return Battle(chunk, spawner, players, soulMonster, quest).also { battleSet.add(it) }
    }

    fun findBattle(player: Player) = battleSet.find { it.isJoined(player) }

    fun endBattle(battle: Battle) {
        battleSet.remove(battle)
    }

    fun findBattle(chunk: Chunk) = battleSet.find { it.chunk == chunk }

}