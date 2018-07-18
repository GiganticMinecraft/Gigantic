package click.seichi.gigantic.extension

import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.player.PlayerRepository
import click.seichi.gigantic.util.CardinalDirection
import click.seichi.gigantic.util.NoiseData
import click.seichi.gigantic.util.Random
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.chat.ComponentSerializer
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

/**
 * @author tar0ss
 */

fun Player.getHead() = ItemStack(Material.SKULL_ITEM, 1, 3).apply {
    itemMeta = (itemMeta as SkullMeta).also { meta ->
        meta.owningPlayer = this@getHead
    }
}

val Player.gPlayer: GiganticPlayer?
    get() = PlayerRepository.find(uniqueId)

val Player.cardinalDirection
    get() = CardinalDirection.getCardinalDirection(this)

val Player.wrappedLocale: Locale
    get() = gPlayer?.locale ?: Locale.JAPANESE

fun Player.sendActionBar(message: String) = spigot().sendMessage(ChatMessageType.ACTION_BAR, ComponentSerializer.parse("{\"text\": \"$message\"}")[0])

fun Player.spawnColoredParticle(
        location: Location,
        color: Color,
        count: Int = 1,
        noiseData: NoiseData = NoiseData()
) = (0 until count).forEach { _ ->
    player.spawnParticle(
            Particle.REDSTONE,
            location.noised(noiseData),
            0,
            color.red.toDouble() / 255.0 + Float.MIN_VALUE,
            color.green.toDouble() / 255.0,
            color.blue.toDouble() / 255.0,
            1.0
    )
}

fun Player.spawnColoredParticleSpherically(
        location: Location,
        color: Color,
        count: Int = 1,
        radius: Double
) = spawnColoredParticle(location, color, count, NoiseData(radius, { Random.nextGaussian(variance = it / 2) }))

fun Player.colorizePlayerListName(color: ChatColor) {
    playerListName = "$color$name"
}