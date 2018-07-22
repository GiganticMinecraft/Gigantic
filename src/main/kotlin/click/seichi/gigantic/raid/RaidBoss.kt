package click.seichi.gigantic.raid

import click.seichi.gigantic.boss.Boss

/**
 * @author tar0ss
 */
class RaidBoss(private val boss: Boss) {

    var health = boss.maxHealth

    fun damage(amount: Double) {
        health -= amount.coerceAtLeast(0.0)
        health.coerceAtLeast(0.0)
    }

    fun isDead() = health <= 0.0

}