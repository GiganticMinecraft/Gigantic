package click.seichi.gigantic.player.belt

/**
 * @author tar0ss
 */
abstract class SkillBelt : Belt() {
    enum class SkillSlot(val slot: Int) {
        MINE_BURST(1),
        ;
    }

    override val hookedItemMap: Map<Int, HookedItem> = mapOf(
//            SkillSlot.MINE_BURST.slot to object : HookedItem {
//                override fun getItemStack(player: Player): ItemStack? {
//                    val gPlayer = player.gPlayer ?: return null
//                    if (!LockedFunction.MINE_BURST.isUnlocked(gPlayer)) return null
//                    val mineBurst = gPlayer.mineBurst
//                    return when {
//                        mineBurst.duringCoolTime() -> ItemStack(Material.FLINT_AND_STEEL).apply {
//                            mineBurst.run {
//                                amount = remainTimeToFire.toInt() + 1
//                            }
//                        }
//                        mineBurst.duringFire() -> ItemStack(Material.ENCHANTED_BOOK).apply {
//                            mineBurst.run {
//                                amount = remainTimeToCool.toInt() + 1
//                            }
//                        }
//                        else -> ItemStack(Material.ENCHANTED_BOOK)
//                    }.apply {
//                        setDisplayName(HookedItemMessages.MINE_BURST.asSafety(player.wrappedLocale))
//                        setLore(*HookedItemMessages.MINE_BURST_LORE(mineBurst)
//                                .map { it.asSafety(player.wrappedLocale) }
//                                .toTypedArray()
//                        )
//                    }
//                }
//
//                override fun onClick(player: Player, event: InventoryClickEvent) {
//                }
//
//                override fun onItemHeld(player: Player, event: PlayerItemHeldEvent) {
//                    val gPlayer = player.gPlayer ?: return
//                    if (!LockedFunction.MINE_BURST.isUnlocked(gPlayer)) return
//                    gPlayer.run {
//                        mineBurst.run {
//                            if (canFire()) {
//                                fire({
//                                    player.removePotionEffect(PotionEffectType.FAST_DIGGING)
//                                    player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 100, 2, true, false))
//                                    SkillSounds.MINE_BURST_ON_FIRE.play(player.location)
//                                    belt.update(player)
//                                }, {
//                                    belt.update(player, SkillSlot.MINE_BURST.slot)
//                                }, {
//                                    belt.update(player)
//                                })
//                            }
//                        }
//                    }
//                }
//
//}
    )
}