package click.seichi.gigantic.player.belt.belts

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.HookedItemMessages
import click.seichi.gigantic.player.belt.SkillBelt
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object PickelBelt : SkillBelt() {

    override val hookedTool = object : HookedItem {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_PICKAXE).apply {
                setDisplayName(HookedItemMessages.PICKEL.asSafety(player.wrappedLocale))
                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }

//                val gPlayer = player.gPlayer ?: return@apply
//                if (!LockedFunction.MINE_BURST.isUnlocked(gPlayer)) return@apply
//                val mineBurst = gPlayer.mineBurst
//                if (mineBurst.duringFire()) {
//                    addEnchantment(Enchantment.DIG_SPEED, 5)
//                }
            }
        }

        override fun onItemHeld(player: Player, event: PlayerItemHeldEvent) {
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }
    }

}