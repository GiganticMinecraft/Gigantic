package click.seichi.gigantic.button.buttons.menu

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setEnchanted
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.menu.SkillMenuMessages
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object SkillButtons {

    val MINE_BURST = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!Achievement.SKILL_MINE_BURST.isGranted(player)) return null
            return ItemStack(Material.BLAZE_POWDER).apply {
                setDisplayName(SkillMenuMessages.MINE_BURST_TITLE.asSafety(player.wrappedLocale))
                setLore(*SkillMenuMessages.MINE_BURST
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val FLASH = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!Achievement.SKILL_FLASH.isGranted(player)) return null
            return ItemStack(Material.FEATHER).apply {
                setDisplayName(SkillMenuMessages.FLASH_TITLE.asSafety(player.wrappedLocale))
                setLore(*SkillMenuMessages.FLASH
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val HEAL = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.APPLE).apply {
                setDisplayName(SkillMenuMessages.HEAL_TITLE.asSafety(player.wrappedLocale))
                setLore(*SkillMenuMessages.HEAL
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }


}