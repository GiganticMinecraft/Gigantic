package click.seichi.gigantic.monster.parameter

import org.bukkit.Material

/**
 * @author tar0ss
 */
data class SoulMonsterParameter(
        val health: Long,
        // one block damage
        val attackDamage: Long,
        // distance per tick
        val speed: Double,
        // blocks per attack
        val attackTimes: Int,
        // ticks to attack
        val tickToAttack: Long,
        val attackMaterial: Material
) {
}