package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setEnchanted
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.SkillMenuMessages
import click.seichi.gigantic.player.skill.Skill
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object SkillMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return SkillMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        Skill.values().forEach { skill ->
            registerButton(skill.slot, object : Button {
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

                override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                    return true
                }

            }
            )
        }
    }

}