package click.seichi.gigantic.item

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 * @author unicroak
 */
interface Icon {

    /**
     * ItemStackを取得します
     *
     * @param player Menuを開いているPlayer
     */
    fun toShownItemStack(player: Player): ItemStack? = null

}