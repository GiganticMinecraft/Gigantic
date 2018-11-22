package click.seichi.gigantic.battle

import click.seichi.gigantic.battle.status.PlayerStatus
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class BattlePlayer(
        val player: Player,
        val isSpawner: Boolean = false
) {
    private val statuses: MutableSet<PlayerStatus> = mutableSetOf()

    fun equals(player: Player): Boolean {
        return this.player == player
    }

}