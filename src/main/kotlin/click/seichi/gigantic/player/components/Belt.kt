package click.seichi.gigantic.player.components

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

sealed class Belt {
    object MINE_BELT : Belt()


    fun forEachIndexed(player: Player, action: (Int, ItemStack?) -> Unit) {

    }
}
