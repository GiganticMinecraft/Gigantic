package click.seichi.gigantic.raid

import click.seichi.gigantic.boss.Boss

/**
 * @author tar0ss
 */
class RaidBoss(private val boss: Boss) {

    var health = boss.maxHealth

    fun damage(amount: Long) {
        health -= amount.coerceAtLeast(0)
        health.coerceAtLeast(0L)
    }

    fun isDead() = health <= 0L

}