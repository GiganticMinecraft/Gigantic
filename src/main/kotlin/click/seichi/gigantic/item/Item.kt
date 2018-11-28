package click.seichi.gigantic.item

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
interface Item {

    /**
     * ItemStackを取得します
     *
     * @param player Menuを開いているPlayer
     */
    fun findItemStack(player: Player): ItemStack?

}