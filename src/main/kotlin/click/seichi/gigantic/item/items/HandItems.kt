package click.seichi.gigantic.item.items

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.enchantment.ToolEnchantment
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.HandItem
import click.seichi.gigantic.message.messages.HandItemMessages
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.skill.Skill
import click.seichi.gigantic.player.spell.Spell
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SpellSounds
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
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

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.DIAMOND_PICKAXE) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.ITALIC}" +
                        HandItemMessages.PICKEL.asSafety(player.wrappedLocale))
                addLore(*HandItemMessages.PICKEL_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                modifyItemMeta(this, player)
            }
        }
    }

    val SHOVEL = object : HandItem {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.DIAMOND_SHOVEL) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.ITALIC}" +
                        HandItemMessages.SHOVEL.asSafety(player.wrappedLocale))
                addLore(*HandItemMessages.SHOVEL_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
                modifyItemMeta(this, player)
            }
        }
    }

    val AXE = object : HandItem {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.DIAMOND_AXE) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.ITALIC}" +
                        HandItemMessages.AXE.asSafety(player.wrappedLocale))
                addLore(*HandItemMessages.AXE_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
                modifyItemMeta(this, player)
            }
        }
    }

    private fun modifyItemMeta(itemStack: ItemStack, player: Player) {
        itemStack.run {
            itemMeta = itemMeta?.apply {
                isUnbreakable = true
                addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                addItemFlags(ItemFlag.HIDE_ENCHANTS)
                addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
            }
            ToolEnchantment.values().forEach { it.addIfMatch(player, itemStack) }
        }
    }

    val MANA_STONE = object : HandItem {
        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Achievement.MANA_STONE.isGranted(player)) return null
            val spellToggle = player.getOrPut(Keys.SPELL_TOGGLE)
            return if (spellToggle) itemStackOf(Material.NETHER_STAR) {
                setDisplayName(HandItemMessages.MANA_STONE.asSafety(player.wrappedLocale))
                setLore(*HandItemMessages.MANA_STONE_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
            }
            else ItemStack(Material.AIR)
        }

        private val coolTimeSet = mutableSetOf<UUID>()

        fun isCoolTime(player: Player) = coolTimeSet.contains(player.uniqueId)

        fun setCoolTime(uniqueId: UUID, isCoolTime: Boolean) {
            if (isCoolTime) {
                coolTimeSet.add(uniqueId)
            } else {
                coolTimeSet.remove(uniqueId)
            }
        }

        override fun tryInteract(player: Player, event: PlayerInteractEvent): Boolean {
            if (!Achievement.MANA_STONE.isGranted(player)) return false
            if (player.inventory.heldItemSlot != player.getOrPut(Keys.BELT).toolSlot) return false
            val action = event.action ?: return false
            if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return false
            if (isCoolTime(player)) return false

            val uniqueId = player.uniqueId
            setCoolTime(uniqueId, true)
            Bukkit.getScheduler().scheduleSyncDelayedTask(Gigantic.PLUGIN, {
                setCoolTime(uniqueId, false)
            }, 5L)

            // マナがない場合は強制的にOFF
            if (!player.hasMana()) {
                PlayerMessages.NO_MANA.sendTo(player)
                player.offer(Keys.SPELL_TOGGLE, false)
                PlayerSounds.FAIL.playOnly(player)
            } else {
                player.transform(Keys.SPELL_TOGGLE) { spellToggle ->
                    val next = !spellToggle
                    if (next) SpellSounds.TOGGLE_ON.playOnly(player)
                    else SpellSounds.TOGGLE_OFF.playOnly(player)
                    next
                }
            }

            player.updateBelt(false, true)
            return true
        }

    }


    val MINE_BURST = object : HandItem {

        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Skill.MINE_BURST.isGranted(player)) return null
            val mineBurst = player.getOrPut(Keys.SKILL_MINE_BURST)
            return when {
                mineBurst.duringCoolTime() -> itemStackOf(Material.FLINT_AND_STEEL) {
                    mineBurst.run {
                        amount = remainTimeToFire.toInt()
                    }
                }
                mineBurst.duringFire() -> itemStackOf(Material.BLAZE_POWDER) {
                    setEnchanted(true)
                    mineBurst.run {
                        amount = remainTimeToCool.toInt()
                    }
                }
                else -> ItemStack(Material.BLAZE_POWDER)
            }.apply {
                setDisplayName(HandItemMessages.MINE_BURST.asSafety(player.wrappedLocale))
                setLore(*HandItemMessages.MINE_BURST_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun tryInteract(player: Player, event: PlayerInteractEvent): Boolean {
            Skill.MINE_BURST.tryCast(player)
            return true
        }

    }

    val FLASH = object : HandItem {

        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Skill.FLASH.isGranted(player)) return null
            val flash = player.getOrPut(Keys.SKILL_FLASH)
            return when {
                flash.duringCoolTime() -> itemStackOf(Material.FLINT_AND_STEEL) {
                    flash.run {
                        amount = remainTimeToFire.toInt()
                    }
                }
                else -> ItemStack(Material.FEATHER)
            }.apply {
                setDisplayName(HandItemMessages.FLASH.asSafety(player.wrappedLocale))
                setLore(*HandItemMessages.FLASH_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun tryInteract(player: Player, event: PlayerInteractEvent): Boolean {
            Skill.FLASH.tryCast(player)
            return true
        }

    }

    val JUMP = object : HandItem {

        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Achievement.SKILL_JUMP.isGranted(player)) return null
            return itemStackOf(Material.PHANTOM_MEMBRANE) {
                setDisplayName(HandItemMessages.JUMP.asSafety(player.wrappedLocale))
                setLore(*HandItemMessages.JUMP_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

    }

    val SKY_WALK = object : HandItem {

        private val coolMap = mutableMapOf<UUID, Boolean>()

        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Spell.SKY_WALK.isGranted(player)) return null
            return itemStackOf(Material.PRISMARINE_CRYSTALS) {
                val toggle = player.getOrPut(Keys.SPELL_SKY_WALK_TOGGLE)
                setDisplayName(
                        HandItemMessages.SKY_WALK.asSafety(player.wrappedLocale) +
                                ": " +
                                if (toggle) "ON" else "OFF"
                )
                if (toggle) {
                    setEnchanted(true)
                }
            }
        }

        override fun tryInteract(player: Player, event: PlayerInteractEvent): Boolean {
            if (!Spell.SKY_WALK.isGranted(player)) return true
            if (coolMap.getOrDefault(player.uniqueId, false)) return true
            coolMap[player.uniqueId] = true
            object : BukkitRunnable() {
                override fun run() {
                    coolMap[player.uniqueId] = false
                }
            }.runTaskLater(Gigantic.PLUGIN, 5L)
            // マナがない場合は強制的にOFF
            if (!player.hasMana()) {
                PlayerMessages.NO_MANA.sendTo(player)
                player.offer(Keys.SPELL_SKY_WALK_TOGGLE, false)
                PlayerSounds.FAIL.playOnly(player)
            } else {
                player.transform(Keys.SPELL_SKY_WALK_TOGGLE) { !it }
                PlayerSounds.TOGGLE.playOnly(player)
            }
            player.updateBelt(false, false)
            return true
        }

    }

    val TOTEM = object : HandItem {
        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Skill.FOCUS_TOTEM.isGranted(player)) return null
            val totem = player.getOrPut(Keys.TOTEM)
            return if (totem <= 0) itemStackOf(Material.WHEAT_SEEDS) {
                val totemPiece = player.getOrPut(Keys.TOTEM_PIECE)
                setDisplayName(player, HandItemMessages.FOCUS_TOTEM_PIECE(totemPiece))
                setLore(*HandItemMessages.FOCUS_TOTEM_PIECE_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
            else itemStackOf(Material.TOTEM_OF_UNDYING) {
                amount = totem
                setDisplayName(player, HandItemMessages.FOCUS_TOTEM)
                setLore(*HandItemMessages.FOCUS_TOTEM_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }

        }
    }


}