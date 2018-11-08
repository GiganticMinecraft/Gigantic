package click.seichi.gigantic.button.buttons.menu

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.menu.ProfileMessages
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object ProfileButtons {
    val PROFILE = object : Button {
        override fun getItemStack(player: Player): ItemStack? {
            return player.getHead().apply {
                setDisplayName("${ChatColor.AQUA}" + ProfileMessages.PROFILE.asSafety(player.wrappedLocale))
                val level = player.find(CatalogPlayerCache.LEVEL) ?: return@apply
                val aptitude = player.find(CatalogPlayerCache.APTITUDE) ?: return@apply
                val health = player.find(CatalogPlayerCache.HEALTH) ?: return@apply
                val mineCombo = player.find(CatalogPlayerCache.MINE_COMBO) ?: return@apply
                val mana = player.find(CatalogPlayerCache.MANA) ?: return@apply
                val lore = mutableListOf(
                        ProfileMessages.PROFILE_LEVEL(level).asSafety(player.wrappedLocale),
                        ProfileMessages.PROFILE_EXP(level).asSafety(player.wrappedLocale),
                        ProfileMessages.PROFILE_HEALTH(health).asSafety(player.wrappedLocale)
                )
                if (Achievement.MANA_STONE.isGranted(player)) {
                    lore.add(ProfileMessages.PROFILE_MANA(mana).asSafety(player.wrappedLocale))
                }

                lore.addAll(listOf(
                        ProfileMessages.PROFILE_MAX_COMBO(mineCombo).asSafety(player.wrappedLocale),
                        *ProfileMessages.PROFILE_WILL_APTITUDE(aptitude).map { it.asSafety(player.wrappedLocale) }.toTypedArray())
                )

                setLore(*lore.toTypedArray())
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }
}