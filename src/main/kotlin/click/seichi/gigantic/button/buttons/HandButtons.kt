package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.button.HandButton
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.HookedItemMessages
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.sound.sounds.SpellSounds
import org.bukkit.Bukkit
import org.bukkit.ChatColor
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
                addEnchantment(this@apply, player)
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

    val SHOVEL = object : HandButton {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_SHOVEL).apply {
                setDisplayName(HookedItemMessages.SHOVEL.asSafety(player.wrappedLocale))
                addEnchantment(this@apply, player)
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

    val AXE = object : HandButton {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_AXE).apply {
                setDisplayName(HookedItemMessages.AXE.asSafety(player.wrappedLocale))
                addEnchantment(this@apply, player)
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

    private fun addEnchantment(itemStack: ItemStack, player: Player) {
        itemStack.run {
            itemMeta = itemMeta.apply {
                isUnbreakable = true
                addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                addItemFlags(ItemFlag.HIDE_ENCHANTS)
                addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
            }
            val mineBurst = player.find(CatalogPlayerCache.MINE_BURST)

            val digSpeedLevel = when {
                mineBurst?.duringFire() == true -> 10
                Relic.FADING_ENDER_PEARL.has(player) -> 2
                Relic.MOISTENED_SLIME_BOLL.has(player) -> 1
                else -> 0
            }
            if (digSpeedLevel > 0) {
                addUnsafeEnchantment(Enchantment.DIG_SPEED, digSpeedLevel)
                addLore("${ChatColor.GRAY}" +
                        HookedItemMessages.SEICHI_SPEED_ENCHANT(digSpeedLevel).asSafety(player.wrappedLocale))
            }

            val comboLevel = when {
                Relic.GHOST_KING_CROWN.has(player) -> 7
                Relic.ORC_KING_CROWN.has(player) -> 6
                Relic.SKELETON_KING_CROWN.has(player) -> 5
                Relic.ZOMBIE_KING_CROWN.has(player) -> 4
                Relic.SPIDER_KING_CROWN.has(player) -> 3
                Relic.TURTLE_KING_CROWN.has(player) -> 2
                Relic.CHICKEN_KING_CROWN.has(player) -> 1
                else -> 0
            }

            if (comboLevel > 0) {
                addLore("${ChatColor.GRAY}" +
                        HookedItemMessages.COMBO_ATTACK_ENCHANT(comboLevel).asSafety(player.wrappedLocale))
            }

            addLore("${ChatColor.GRAY}" +
                    HookedItemMessages.CONDENSE_WATER_ENCHANT.asSafety(player.wrappedLocale))
            addLore("${ChatColor.GRAY}" +
                    HookedItemMessages.CONDENSE_LAVA_ENCHANT.asSafety(player.wrappedLocale))
            addLore("${ChatColor.GRAY}" +
                    HookedItemMessages.CUT_ENCHANT.asSafety(player.wrappedLocale))
        }
    }


    val BUCKET = object : HandButton {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.BUCKET).apply {
                setDisplayName(HookedItemMessages.BUCKET.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.BUCKET_LORE
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
            player.getOrPut(Keys.BELT).wear(player, false, true)
        }

    }

}