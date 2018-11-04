package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.button.HandButton
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.HookedItemMessages
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.sound.sounds.SpellSounds
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object HandButtons {

    val PICKEL = object : HandButton {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_PICKAXE).apply {
                setDisplayName(HookedItemMessages.PICKEL.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.PICKEL_LORE(player)
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
                val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return@apply
                if (mineBurst.duringFire()) {
                    addEnchantment(Enchantment.DIG_SPEED, 5)
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent) {
        }
    }

    val SHOVEL = object : HandButton {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_SHOVEL).apply {
                setDisplayName(HookedItemMessages.SHOVEL.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.SHOVEL_LORE(player)
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
                val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return@apply
                if (mineBurst.duringFire()) {
                    addEnchantment(Enchantment.DIG_SPEED, 5)
                }

            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent) {
        }
    }

    val AXE = object : HandButton {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_AXE).apply {
                setDisplayName(HookedItemMessages.AXE.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.AXE_LORE(player)
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
                val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return@apply
                if (mineBurst.duringFire()) {
                    addEnchantment(Enchantment.DIG_SPEED, 5)
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent) {
        }
    }


    val BUCKET = object : HandButton {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.BUCKET).apply {
                setDisplayName(HookedItemMessages.BUCKET.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.BUCKET_LORE(player)
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent) {
        }
    }

    val MANA_STONE = object : HandButton {
        override fun getItemStack(player: Player): ItemStack? {
            if (!LockedFunction.MANA_STONE.isUnlocked(player)) return null
            val spellToggle = player.getOrPut(Keys.SPELL_TOGGLE)
            return if (spellToggle) ItemStack(Material.NETHER_STAR).apply {
                setDisplayName(HookedItemMessages.MANA_STONE.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.MANA_STONE_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
            }
            else ItemStack(Material.AIR)
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent) {
            if (!LockedFunction.MANA_STONE.isUnlocked(player)) return
            val action = event.action ?: return
            if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return
            val coolTime = !player.getOrPut(Keys.MANA_STONE_CAN_TOGGLE)
            if (coolTime) return
            player.offer(Keys.MANA_STONE_CAN_TOGGLE, false)
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                if (!player.isValid) return@runTaskLater
                player.offer(Keys.MANA_STONE_CAN_TOGGLE, true)
            }, 5L)
            player.transform(Keys.SPELL_TOGGLE) { spellToggle ->
                val next = !spellToggle
                if (next) SpellSounds.TOGGLE_ON.playOnly(player)
                else SpellSounds.TOGGLE_OFF.playOnly(player)
                next
            }
            player.getOrPut(Keys.BELT).wear(player, false, true)
        }

    }

}