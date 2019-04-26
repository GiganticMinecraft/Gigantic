package click.seichi.gigantic.belt

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.extension.updateTool
import click.seichi.gigantic.item.Armor
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.HandItem
import click.seichi.gigantic.item.items.Armors
import click.seichi.gigantic.item.items.HandItems
import click.seichi.gigantic.player.Defaults
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
enum class Belt(
        val id: Int,
        val toolSlot: Int,
        val offHandItem: HandItem?,
        val helmet: Armor?,
        val chestPlate: Armor?,
        val leggings: Armor?,
        val boots: Armor?,
        vararg items: Pair<Int, HandItem>
) {
    DEFAULT(
            1,
            0,
            HandItems.MANA_STONE,
            Armors.HELMET,
            Armors.ELYTRA,
            Armors.LEGGINGS,
            Armors.BOOTS,
            1 to HandItems.FLASH,
            2 to HandItems.MINE_BURST,
            3 to HandItems.SKY_WALK,
            Defaults.TOTEM_SLOT to HandItems.TOTEM,
            8 to HandItems.JUMP
    ),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }

    private val buttonMap: MutableMap<Int, Button> = mutableMapOf(
            *items
    )

    /**
     * ベルトを身に着ける
     *
     * @param applyTool ツールも更新するかどうか
     * @param applyOffHandItem オフハンドも更新するかどうか
     */
    fun wear(player: Player, applyTool: Boolean = true, applyOffHandItem: Boolean = true) {
        player.inventory?.let { inventory ->
            (0..8).forEach { slot ->
                if (slot == toolSlot) return@forEach
                inventory.setItem(slot,
                        buttonMap[slot]?.toShownItemStack(player) ?: ItemStack(Material.AIR)
                )
            }
            if (applyTool)
                player.updateTool()
            inventory.helmet = helmet?.toShownItemStack(player) ?: ItemStack(Material.AIR)
            inventory.chestplate = chestPlate?.toShownItemStack(player) ?: ItemStack(Material.AIR)
            inventory.leggings = leggings?.toShownItemStack(player) ?: ItemStack(Material.AIR)
            inventory.boots = boots?.toShownItemStack(player) ?: ItemStack(Material.AIR)

            if (!applyOffHandItem) return@let
            inventory.setItemInOffHand(offHandItem?.toShownItemStack(player) ?: ItemStack(Material.AIR))
        }
    }

    fun findItem(slot: Int): HandItem? {
        return buttonMap[slot] as HandItem?
    }

    fun grant(player: Player) {
        player.offer(Keys.BELT_UNLOCK_MAP[this]!!, true)
    }

    fun revoke(player: Player) {
        player.offer(Keys.BELT_UNLOCK_MAP[this]!!, false)
        player.offer(Keys.BELT_TOGGLE_MAP[this]!!, false)
    }

    fun canSwitch(player: Player): Boolean {
        if (!player.getOrPut(Keys.BELT_UNLOCK_MAP[this]!!)) return false

        return player.getOrPut(Keys.BELT_TOGGLE_MAP[this]!!)
    }

    fun isGranted(player: Player): Boolean {
        return player.getOrPut(Keys.BELT_UNLOCK_MAP[this]!!)
    }

    fun toggle(player: Player) {
        if (isGranted(player))
            player.transform(Keys.BELT_TOGGLE_MAP[this]!!) {
                !it
            }
    }

}