package click.seichi.gigantic.battle

import click.seichi.gigantic.monster.SoulMonster
import org.bukkit.Chunk
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object BattleManager {

    private val battleSet: MutableSet<Battle> = mutableSetOf()

    fun newBattle(chunk: Chunk, player: Player, soulMonster: SoulMonster): Battle {
        return Battle(chunk, player, soulMonster).also { battleSet.add(it) }
    }

    fun findBattle(player: Player) = battleSet.find { it.isJoined(player) || it.spawner == player }

    fun endBattle(battle: Battle) {
        battleSet.remove(battle)
    }

    fun getBattleSet() = battleSet.toSet()


}