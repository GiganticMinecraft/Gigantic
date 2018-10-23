package click.seichi.gigantic.message

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.offer
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class LostHealthMessage(
        private val damage: Double,
        private val deathMessage: LocalizedText
) : Message {
    override fun sendTo(player: Player) {
        player.offer(Keys.DEATH_MESSAGE, deathMessage)
        player.damage(damage)
    }
}