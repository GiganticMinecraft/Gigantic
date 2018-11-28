package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setEnchanted
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.player.skill.Skill
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object SkillButtons {

    val SKILL: (Skill) -> Button = { skill: Skill ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                if (!skill.isGranted(player)) return null
                return skill.getIcon(player).apply {
                    setDisplayName(skill.getName(player.wrappedLocale))
                    skill.getLore(player.wrappedLocale)?.let {
                        setLore(*it.toTypedArray())
                    }
                    setEnchanted(true)
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
            }

        }
    }
}