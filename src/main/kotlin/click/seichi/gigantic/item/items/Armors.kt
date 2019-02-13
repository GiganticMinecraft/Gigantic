package click.seichi.gigantic.item.items

import click.seichi.gigantic.item.Armor
import click.seichi.gigantic.message.messages.ArmorMessages
import org.bukkit.Material

/**
 * @author tar0ss
 */
object Armors {

    val HELMET = Armor(Material.DIAMOND_HELMET, ArmorMessages.HELMET, ArmorMessages.HELMET_LORE)

    val ELYTRA = Armor(Material.ELYTRA, ArmorMessages.ELYTRA, ArmorMessages.ELYTRA_LORE)

    val LEGGINGS = Armor(Material.DIAMOND_LEGGINGS, ArmorMessages.LEGGINGS, ArmorMessages.LEGGINGS_LORE)

    val BOOTS = Armor(Material.DIAMOND_BOOTS, ArmorMessages.BOOTS, ArmorMessages.BOOTS_LORE)

}