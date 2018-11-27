package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setEnchanted
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.player.spell.Spell
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object SpellButtons {

    val SPELL: (Spell) -> Button = { spell: Spell ->
        object : Button {

            override fun getItemStack(player: Player): ItemStack? {
                if (!spell.isGranted(player)) return null
                return spell.getIcon(player).apply {
                    setDisplayName(spell.getName(player.wrappedLocale))
                    spell.getLore(player.wrappedLocale)?.let {
                        setLore(*it.toTypedArray())
                    }
                    setEnchanted(true)
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
            }

        }
    }
/*

    val KODAMA_DRAIN = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!Achievement.SPELL_TERRA_DRAIN.isGranted(player)) return null
            val toggle = player.getOrPut(Keys.TERRA_DRAIN_TOGGLE)
            return ItemStack(Material.WHEAT_SEEDS).apply {
                if (toggle)
                    setDisplayName(SpellMenuMessages.TERRA_DRAIN_TITLE_ON.asSafety(player.wrappedLocale))
                else
                    setDisplayName(SpellMenuMessages.TERRA_DRAIN_TITLE_OFF.asSafety(player.wrappedLocale))
                setLore(*SpellMenuMessages.KODAMA_DRAIN
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                if (toggle)
                    setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (!Achievement.SPELL_TERRA_DRAIN.isGranted(player)) return
            player.transform(Keys.TERRA_DRAIN_TOGGLE) {
                if (it)
                    PlayerSounds.SPELL_TOGGLE_OFF.playOnly(player)
                else
                    PlayerSounds.SPELL_TOGGLE_ON.playOnly(player)
                !it
            }
            SpellMenu.reopen(player)
        }

    }

    val GRAND_NATURA = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!Achievement.SPELL_GRAND_NATURA.isGranted(player)) return null
            val toggle = player.getOrPut(Keys.GRAND_NATURA_TOGGLE)
            return ItemStack(Material.SEA_PICKLE).apply {
                if (toggle)
                    setDisplayName(SpellMenuMessages.GRAND_NATURA_TITLE_ON.asSafety(player.wrappedLocale))
                else
                    setDisplayName(SpellMenuMessages.GRAND_NATURA_TITLE_OFF.asSafety(player.wrappedLocale))
                setLore(*SpellMenuMessages.GRAND_NATURA
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                if (toggle)
                    setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (!Achievement.SPELL_GRAND_NATURA.isGranted(player)) return
            player.transform(Keys.GRAND_NATURA_TOGGLE) {
                if (it)
                    PlayerSounds.SPELL_TOGGLE_OFF.playOnly(player)
                else
                    PlayerSounds.SPELL_TOGGLE_ON.playOnly(player)
                !it
            }
            SpellMenu.reopen(player)
        }

    }

    val AQUA_LINEA = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!Achievement.SPELL_AQUA_LINEA.isGranted(player)) return null
            val toggle = player.getOrPut(Keys.AQUA_LINEA_TOGGLE)
            return ItemStack(Material.PRISMARINE_CRYSTALS).apply {
                if (toggle)
                    setDisplayName(SpellMenuMessages.AQUA_LINEA_TITLE_ON.asSafety(player.wrappedLocale))
                else
                    setDisplayName(SpellMenuMessages.AQUA_LINEA_TITLE_OFF.asSafety(player.wrappedLocale))
                setLore(*SpellMenuMessages.AQUA_LINEA
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                if (toggle)
                    setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (!Achievement.SPELL_AQUA_LINEA.isGranted(player)) return
            player.transform(Keys.AQUA_LINEA_TOGGLE) {
                if (it)
                    PlayerSounds.SPELL_TOGGLE_OFF.playOnly(player)
                else
                    PlayerSounds.SPELL_TOGGLE_ON.playOnly(player)

                !it
            }
            SpellMenu.reopen(player)
        }

    }
*/

}