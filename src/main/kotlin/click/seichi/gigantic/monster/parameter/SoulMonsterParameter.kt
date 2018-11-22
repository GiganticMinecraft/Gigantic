package click.seichi.gigantic.monster.parameter

import org.bukkit.Material

/**
 * @author tar0ss
 */
data class SoulMonsterParameter(
        val health: Long,
        // one block damage
        val power: Long,
        val attackInterval: Long,
        val shotInterval: Long,
        // blocks per attack
        val attackTimes: Int,
        val attackMaterial: Material
) {
}