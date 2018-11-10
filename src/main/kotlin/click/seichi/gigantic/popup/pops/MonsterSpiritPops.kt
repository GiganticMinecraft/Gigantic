package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.popup.PopUp
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object MonsterSpiritPops {

    val SPAWN = { head: ItemStack ->
        PopUp(
                duration = 60L * 20L,
                popping = { armorStand ->
                    armorStand.helmet = head
                }
        )
    }

}