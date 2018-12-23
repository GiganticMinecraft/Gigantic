package click.seichi.gigantic.item.items

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.enchantment.ToolEnchantment
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.HandItem
import click.seichi.gigantic.message.messages.HookedItemMessages
import click.seichi.gigantic.player.skill.Skill
import click.seichi.gigantic.sound.sounds.SpellSounds
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object HandItems {

    val PICKEL = object : HandItem {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_PICKAXE).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.ITALIC}" +
                        HookedItemMessages.PICKEL.asSafety(player.wrappedLocale))
                modifyItemMeta(this@apply, player)
                addLore(*HookedItemMessages.PICKEL_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent) {
        }
    }

    val SHOVEL = object : HandItem {
        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_SHOVEL).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.ITALIC}" +
                        HookedItemMessages.SHOVEL.asSafety(player.wrappedLocale))
                modifyItemMeta(this@apply, player)
                addLore(*HookedItemMessages.SHOVEL_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent) {
        }
    }

    val AXE = object : HandItem {
        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_AXE).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.ITALIC}" +
                        HookedItemMessages.AXE.asSafety(player.wrappedLocale))
                modifyItemMeta(this@apply, player)
                addLore(*HookedItemMessages.AXE_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent) {
        }
    }

    private fun modifyItemMeta(itemStack: ItemStack, player: Player) {
        itemStack.run {
            itemMeta = itemMeta.apply {
                isUnbreakable = true
                addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                addItemFlags(ItemFlag.HIDE_ENCHANTS)
                addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
            }
            ToolEnchantment.values().forEach { it.addIfMatch(player, itemStack) }
        }
    }

    val MANA_STONE = object : HandItem {
        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.MANA_STONE.isGranted(player)) return null
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
            if (!Achievement.MANA_STONE.isGranted(player)) return
            val action = event.action ?: return
            if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return
            val coolTime = !player.getOrPut(Keys.IS_MANA_STONE_TOGGLE_COOLDOWN)
            if (coolTime) return
            player.offer(Keys.IS_MANA_STONE_TOGGLE_COOLDOWN, false)
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                if (!player.isValid) return@runTaskLater
                player.offer(Keys.IS_MANA_STONE_TOGGLE_COOLDOWN, true)
            }, 5L)
            player.transform(Keys.SPELL_TOGGLE) { spellToggle ->
                val next = !spellToggle
                if (next) SpellSounds.TOGGLE_ON.playOnly(player)
                else SpellSounds.TOGGLE_OFF.playOnly(player)
                next
            }
            player.updateBelt(false, true)
        }

    }


    val MINE_BURST = object : HandItem {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.SKILL_MINE_BURST.isGranted(player)) return null
            val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return null
            return when {
                mineBurst.duringCoolTime() -> ItemStack(Material.FLINT_AND_STEEL).apply {
                    mineBurst.run {
                        amount = remainTimeToFire.toInt()
                    }
                }
                mineBurst.duringFire() -> ItemStack(Material.BLAZE_POWDER).apply {
                    setEnchanted(true)
                    mineBurst.run {
                        amount = remainTimeToCool.toInt()
                    }
                }
                else -> ItemStack(Material.BLAZE_POWDER)
            }.apply {
                setDisplayName(HookedItemMessages.MINE_BURST.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.MINE_BURST_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent) {
            Skill.MINE_BURST.tryCast(player)
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val FLASH = object : HandItem {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.SKILL_FLASH.isGranted(player)) return null
            val flash = player.find(CatalogPlayerCache.FLASH) ?: return null
            return when {
                flash.duringCoolTime() -> ItemStack(Material.FLINT_AND_STEEL).apply {
                    flash.run {
                        amount = remainTimeToFire.toInt()
                    }
                }
                else -> ItemStack(Material.FEATHER)
            }.apply {
                setDisplayName(HookedItemMessages.FLASH.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.FLASH_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent) {
            Skill.FLASH.tryCast(player)
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }


}