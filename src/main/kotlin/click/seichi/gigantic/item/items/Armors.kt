package click.seichi.gigantic.item.items

import click.seichi.gigantic.extension.addLore
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Armor
import click.seichi.gigantic.message.messages.ArmorMessages
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object Armors {

    val HELMET = object : Armor {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_HELMET).apply {
                setDisplayName(ArmorMessages.HELMET.asSafety(player.wrappedLocale))

                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }

                addLore(*ArmorMessages.HELMET_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val ELYTRA = object : Armor {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.ELYTRA).apply {
                setDisplayName(ArmorMessages.ELYTRA.asSafety(player.wrappedLocale))

                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }

                addLore(*ArmorMessages.ELYTRA_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val LEGGINGS = object : Armor {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_LEGGINGS).apply {
                setDisplayName(ArmorMessages.LEGGINGS.asSafety(player.wrappedLocale))

                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }

                addLore(*ArmorMessages.LEGGINGS_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val BOOTS = object : Armor {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_BOOTS).apply {
                setDisplayName(ArmorMessages.BOOTS.asSafety(player.wrappedLocale))

                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }

                addLore(*ArmorMessages.BOOTS_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

}