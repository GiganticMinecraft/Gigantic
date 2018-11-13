package click.seichi.gigantic.battle

import click.seichi.gigantic.monster.SoulMonster
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object BattleManager {

    private val battleSet: MutableSet<Battle> = mutableSetOf()

    fun newBattle(player: Player, soulMonster: SoulMonster): Battle {
        return Battle(player, soulMonster).also { battleSet.add(it) }
    }

    fun findBattle(player: Player) = battleSet.find { it.isJoined(player) }

    fun endBattle(player: Player) {
        findBattle(player)?.let {
            battleSet.remove(it)
            it.end()
        }
    }


}