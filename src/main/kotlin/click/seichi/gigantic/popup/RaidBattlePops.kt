package click.seichi.gigantic.popup

import org.bukkit.ChatColor

/**
 * @author tar0ss
 */
object RaidBattlePops {

    val BATTLE_DAMAGE = { damage: Long ->
        val color = when (damage) {
            0L -> ChatColor.WHITE
            in 1L..3L -> ChatColor.RED
            in 4L..8L -> ChatColor.DARK_PURPLE
            in 9L..15L -> ChatColor.GOLD
            else -> ChatColor.LIGHT_PURPLE
        }
        PopUp(
                "$color$damage attack",
                PopUp.PopPattern.POP
        )
    }
}