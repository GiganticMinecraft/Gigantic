package click.seichi.gigantic.item.items

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.enchantment.ToolEnchantment
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.HandItem
import click.seichi.gigantic.message.messages.HookedItemMessages
import click.seichi.gigantic.player.skill.Skill
import click.seichi.gigantic.player.spell.Spell
import click.seichi.gigantic.sound.sounds.PlayerSounds
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
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

/**
 * @author tar0ss
 */
object HandItems {

    val PICKEL = object : HandItem {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_PICKAXE).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.ITALIC}" +
                        HookedItemMessages.PICKEL.asSafety(player.wrappedLocale))
                addLore(*HookedItemMessages.PICKEL_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                modifyItemMeta(this@apply, player)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent): Boolean {
            return false
        }
    }

    val SHOVEL = object : HandItem {
        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_SHOVEL).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.ITALIC}" +
                        HookedItemMessages.SHOVEL.asSafety(player.wrappedLocale))
                addLore(*HookedItemMessages.SHOVEL_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
                modifyItemMeta(this@apply, player)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent): Boolean {
            return false
        }
    }

    val AXE = object : HandItem {
        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_AXE).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.ITALIC}" +
                        HookedItemMessages.AXE.asSafety(player.wrappedLocale))
                addLore(*HookedItemMessages.AXE_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
                modifyItemMeta(this@apply, player)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent): Boolean {
            return false
        }
    }
    val SWORD = object : HandItem {
        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_SWORD).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.ITALIC}" +
                        HookedItemMessages.SWORD.asSafety(player.wrappedLocale))
                addLore(*HookedItemMessages.SWORD_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
                modifyItemMeta(this@apply, player)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent): Boolean {
            return false
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

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent): Boolean {
            if (!Achievement.MANA_STONE.isGranted(player)) return false
            val action = event.action ?: return false
            if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return false
            val coolTime = !player.getOrPut(Keys.IS_MANA_STONE_TOGGLE_COOLDOWN)
            if (coolTime) return false
            player.offer(Keys.IS_MANA_STONE_TOGGLE_COOLDOWN, false)
            Bukkit.getScheduler().scheduleSyncDelayedTask(Gigantic.PLUGIN, {
                if (!player.isValid) return@scheduleSyncDelayedTask
                player.offer(Keys.IS_MANA_STONE_TOGGLE_COOLDOWN, true)
            }, 5L)
            player.transform(Keys.SPELL_TOGGLE) { spellToggle ->
                val next = !spellToggle
                if (next) SpellSounds.TOGGLE_ON.playOnly(player)
                else SpellSounds.TOGGLE_OFF.playOnly(player)
                next
            }
            player.updateBelt(false, true)
            return true
        }

    }


    val MINE_BURST = object : HandItem {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Skill.MINE_BURST.isGranted(player)) return null
            val mineBurst = player.getOrPut(Keys.SKILL_MINE_BURST)
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

        override fun onInteract(player: Player, event: PlayerInteractEvent): Boolean {
            return Skill.MINE_BURST.tryCast(player)
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }

    }

    val FLASH = object : HandItem {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Skill.FLASH.isGranted(player)) return null
            val flash = player.getOrPut(Keys.SKILL_FLASH)
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

        override fun onInteract(player: Player, event: PlayerInteractEvent): Boolean {
            return Skill.FLASH.tryCast(player)
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }

    }

    val JUMP = object : HandItem {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.JUMP.isGranted(player)) return null
            return ItemStack(Material.PHANTOM_MEMBRANE).apply {
                setDisplayName(HookedItemMessages.JUMP.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.JUMP_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent): Boolean {
            return false
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }

    }

    val SKY_WALK = object : HandItem {

        private val coolMap = mutableMapOf<UUID, Boolean>()

        override fun findItemStack(player: Player): ItemStack? {
            if (!Spell.SKY_WALK.isGranted(player)) return null
            return ItemStack(Material.SUGAR).apply {
                val toggle = player.getOrPut(Keys.SPELL_SKY_WALK_TOGGLE)
                setDisplayName(
                        HookedItemMessages.SKY_WALK.asSafety(player.wrappedLocale) +
                                ": " +
                                if (toggle) "ON" else "OFF"
                )
                if (toggle) {
                    setEnchanted(true)
                }
            }
        }

        override fun onInteract(player: Player, event: PlayerInteractEvent): Boolean {
            if (!Spell.SKY_WALK.isGranted(player)) return true
            if (coolMap.getOrDefault(player.uniqueId, false)) return true
            coolMap[player.uniqueId] = true
            object : BukkitRunnable() {
                override fun run() {
                    if (!player.isValid) return
                    coolMap[player.uniqueId] = false
                }
            }.runTaskLater(Gigantic.PLUGIN, 5L)
            player.transform(Keys.SPELL_SKY_WALK_TOGGLE) { !it }
            PlayerSounds.TOGGLE.playOnly(player)
            player.updateBelt(false, false)
            return true
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }

    }


}