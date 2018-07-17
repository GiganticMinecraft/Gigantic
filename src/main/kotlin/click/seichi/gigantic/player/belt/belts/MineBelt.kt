package click.seichi.gigantic.player.belt.belts

import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.language.messages.HookedItemMessages
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.player.belt.Belt
import click.seichi.gigantic.sound.SkillSounds
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

/**
 * @author tar0ss
 */
object MineBelt : Belt() {
    enum class ItemSlot(val slot: Int) {
        PICKEL(0),
        MINE_BURST(1),
        ;
    }


    override val hookedItemMap: Map<Int, HookedItem> = mapOf(
            ItemSlot.PICKEL.slot to object : HookedItem {
                override fun getItemStack(player: Player): ItemStack? {
                    return ItemStack(Material.DIAMOND_PICKAXE).apply {
                        setDisplayName(HookedItemMessages.PICKEL.asSafety(player.wrappedLocale))
                        itemMeta = itemMeta.apply {
                            isUnbreakable = true
                            addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                            addItemFlags(ItemFlag.HIDE_ENCHANTS)
                            addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                        }

                        val gPlayer = player.gPlayer ?: return@apply
                        if (!LockedFunction.MINE_BURST.isUnlocked(gPlayer.level)) return@apply
                        val mineBurst = gPlayer.mineBurst
                        if (mineBurst.duringFire()) {
                            addEnchantment(Enchantment.DIG_SPEED, 5)
                        }
                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent) {
//                    player.sendMessage("hello world!!")
                }

                override fun onInteract(player: Player, event: PlayerInteractEvent) {
//                    player.sendMessage("hello world!!")
                }
            },
            ItemSlot.MINE_BURST.slot to object : HookedItem {
                override fun getItemStack(player: Player): ItemStack? {
                    val gPlayer = player.gPlayer ?: return null
                    if (!LockedFunction.MINE_BURST.isUnlocked(gPlayer.level)) return null
                    val mineBurst = gPlayer.mineBurst
                    return when {
                        mineBurst.duringCoolTime() -> ItemStack(Material.FLINT_AND_STEEL).apply {
                            mineBurst.run {
                                amount = remainTimeToFire.toInt() + 1
                            }
                        }
                        mineBurst.duringFire() -> ItemStack(Material.ENCHANTED_BOOK).apply {
                            mineBurst.run {
                                amount = remainTimeToCool.toInt() + 1
                            }
                        }
                        else -> ItemStack(Material.ENCHANTED_BOOK)
                    }.apply {
                        setDisplayName(HookedItemMessages.MINE_BURST.asSafety(player.wrappedLocale))
                        setLore(*HookedItemMessages.MINE_BURST_LORE(mineBurst)
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray()
                        )
                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent) {
                    event.isCancelled = true
                }

                override fun onInteract(player: Player, event: PlayerInteractEvent) {
                    event.isCancelled = true
                    val gPlayer = player.gPlayer ?: return
                    if (!LockedFunction.MINE_BURST.isUnlocked(gPlayer.level)) return
                    gPlayer.run {
                        mineBurst.run {
                            if (canFire()) {
                                fire({
                                    player.removePotionEffect(PotionEffectType.FAST_DIGGING)
                                    player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 100, 2, true, false))
                                    SkillSounds.MINE_BURST_ON_FIRE.play(player.location)
                                    belt.apply(player)
                                }, {
                                    belt.apply(player, MineBelt.ItemSlot.MINE_BURST.slot)
                                }, {
                                    belt.apply(player)
                                })
                            }
                        }
                    }
                    player.inventory.heldItemSlot = ItemSlot.PICKEL.slot
                }

            }
    )

}