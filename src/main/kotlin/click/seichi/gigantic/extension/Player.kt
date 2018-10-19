package click.seichi.gigantic.extension

import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Key
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
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

fun Player.getHead() = ItemStack(Material.PLAYER_HEAD).apply {
    itemMeta = (itemMeta as SkullMeta).also { meta ->
        meta.owningPlayer = this@getHead
    }
}

fun <V : Any> Player.find(key: Key<PlayerCache, out V>) = PlayerCacheMemory.get(uniqueId).find(key)

fun <V : Any> Player.offer(key: Key<PlayerCache, V>, value: V) = PlayerCacheMemory.get(uniqueId).offer(key, value)

fun <V : Any> Player.transform(key: Key<PlayerCache, V>, transforming: (V) -> V) = PlayerCacheMemory.get(uniqueId).transform(key, transforming)

fun <M : Manipulator<M, PlayerCache>> Player.find(clazz: Class<M>) = PlayerCacheMemory.get(uniqueId).find(clazz)

fun <M : Manipulator<M, PlayerCache>> Player.offer(manipulator: M) = PlayerCacheMemory.get(uniqueId).offer(manipulator)

fun <M : Manipulator<M, PlayerCache>> Player.manipulate(clazz: Class<M>, transforming: (M) -> Unit) = PlayerCacheMemory.get(uniqueId).manipulate(clazz, transforming)

val Player.wrappedLocale: Locale
    get() = find(Keys.LOCALE) ?: Locale.JAPANESE

val Player.cardinalDirection
    get() = CardinalDirection.getCardinalDirection(this)

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
            Particle.DustOptions(color, 1.0F)
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