package click.seichi.gigantic.button.buttons.menu

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.menus.SpellMenu
import click.seichi.gigantic.message.messages.menu.SpellMenuMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object SpellButtons {

    val STELLA_CLAIR = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!Achievement.SPELL_STELLA_CLAIR.isGranted(player)) return null
            return ItemStack(Material.LAPIS_LAZULI).apply {
                setDisplayName(SpellMenuMessages.STELLA_CLAIR_TITLE.asSafety(player.wrappedLocale))
                setLore(*SpellMenuMessages.STELLA_CLAIR
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val TERRA_DRAIN = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!Achievement.SPELL_TERRA_DRAIN.isGranted(player)) return null
            val toggle = player.getOrPut(Keys.TERRA_DRAIN_TOGGLE)
            return ItemStack(Material.WHEAT_SEEDS).apply {
                if (toggle)
                    setDisplayName(SpellMenuMessages.TERRA_DRAIN_TITLE_ON.asSafety(player.wrappedLocale))
                else
                    setDisplayName(SpellMenuMessages.TERRA_DRAIN_TITLE_OFF.asSafety(player.wrappedLocale))
                setLore(*SpellMenuMessages.TERRA_DRAIN
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

}