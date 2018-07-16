package click.seichi.gigantic.player.components

import click.seichi.gigantic.Gigantic
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.ArmorStand

/**
 * @author tar0ss
 */
class MineCombo {

    var currentCombo: Long = 0L
        private set

    private var lastComboTime = System.currentTimeMillis()

    companion object {
        const val COMBO_CONTINUATION_SECONDS = 3L
    }

    fun combo(count: Long): Long {
        if (canContinue()) {
            currentCombo += count
        } else {
            currentCombo = count
        }
        updateComboTime()
        return currentCombo
    }

    private fun updateComboTime() {
        lastComboTime = System.currentTimeMillis()
    }

    private fun canContinue(): Boolean {
        val now = System.currentTimeMillis()
        val diff = now - lastComboTime
        return COMBO_CONTINUATION_SECONDS > diff.div(1000)
    }

    fun display(location: Location) {
        location.world.spawn(location, ArmorStand::class.java).run {
            setBasePlate(false)
            setArms(true)
            isVisible = false
            isInvulnerable = true
            canPickupItems = false
            setGravity(false)
            isCustomNameVisible = true
            customName = "${calcColor()}${ChatColor.BOLD}$currentCombo Combo"
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                this.remove()
            }, 10L)
        }
    }

    private fun calcColor() = when (currentCombo) {
        in 0..9 -> ChatColor.WHITE
        in 10..29 -> ChatColor.GREEN
        in 30..69 -> ChatColor.AQUA
        in 70..149 -> ChatColor.BLUE
        in 150..349 -> ChatColor.YELLOW
        in 350..799 -> ChatColor.DARK_AQUA
        in 800..1199 -> ChatColor.LIGHT_PURPLE
        else -> ChatColor.GOLD
    }
}