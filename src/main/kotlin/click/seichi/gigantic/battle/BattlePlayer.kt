package click.seichi.gigantic.battle

import click.seichi.gigantic.battle.status.PlayerStatus
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class BattlePlayer(
        val player: Player,
        val isSpawner: Boolean = false
) {
    private val statuses: MutableSet<PlayerStatus> = mutableSetOf()

    var health: Long = player.getOrPut(Keys.HEALTH)
        set(value) {

        }

    fun equals(player: Player): Boolean {
        return this.player == player
    }

}