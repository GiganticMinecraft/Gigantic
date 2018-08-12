package click.seichi.gigantic.raid

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.util.Random
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
class RaidBoss(boss: Boss) {

    var health = boss.maxHealth

    val maxHealth = boss.maxHealth

    private val totalDamageMap = mutableMapOf<UUID, Double>()

    private val dropRelicSet = boss.dropRelicSet

    fun damage(player: Player, amount: Double) {
        val trueDamage = amount.coerceAtLeast(0.0)
        val prevTotal = totalDamageMap.getOrDefault(player.uniqueId, 0.0)
        val nextTotal = prevTotal + trueDamage
        totalDamageMap[player.uniqueId] = nextTotal
        health -= trueDamage
        health.coerceAtLeast(0.0)
    }

    fun getTotalDamage(player: Player) = totalDamageMap[player.uniqueId] ?: 0.0

    fun getDrop(player: Player): Relic? {
        if (!isDefeated(player)) return null
        return dropRelicSet.firstOrNull { it.probability > Random.nextDouble() }?.relic
    }

    // if player hurts ten percents of max health -> true
    fun isDefeated(player: Player): Boolean {
        val totalDamage = getTotalDamage(player)
        return totalDamage >= maxHealth.div(10.0).times(1.0)
    }

    fun isDead() = health <= 0.0

}