package click.seichi.gigantic.belt

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.button.HandButton
import click.seichi.gigantic.button.HotButton
import click.seichi.gigantic.button.buttons.HandButtons
import click.seichi.gigantic.button.buttons.HotButtons
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.BeltMessages
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
enum class Belt(
        val id: Int,
        val localizedName: LocalizedText,
        fixedButton: Pair<Int, HandButton>,
        val offHandButton: HandButton?,
        vararg hotButtons: Pair<Int, HotButton>
) {
    DIG(
            1,
            BeltMessages.DIG,
            0 to HandButtons.SHOVEL,
            HandButtons.MANA_STONE,
            1 to HotButtons.FLASH,
            2 to HotButtons.MINE_BURST,
            7 to HotButtons.TELEPORT_DOOR,
            8 to HotButtons.BELT_SWITCHER_SETTING
    ),
    MINE(
            2,
            BeltMessages.MINE,
            0 to HandButtons.PICKEL,
            HandButtons.MANA_STONE,
            1 to HotButtons.FLASH,
            2 to HotButtons.MINE_BURST,
            7 to HotButtons.TELEPORT_DOOR,
            8 to HotButtons.BELT_SWITCHER_SETTING
    ),
    CUT(
            3,
            BeltMessages.CUT,
            0 to HandButtons.AXE,
            HandButtons.MANA_STONE,
            1 to HotButtons.FLASH,
            2 to HotButtons.MINE_BURST,
            7 to HotButtons.TELEPORT_DOOR,
            8 to HotButtons.BELT_SWITCHER_SETTING
    ),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }

    // 手に固定されたスロット番号
    private var fixedSlot = fixedButton.first

    private val buttonMap: MutableMap<Int, Button> = mutableMapOf(
            *hotButtons,
            fixedButton
    )

    /**
     * ベルトを身に着ける
     *
     * @param applyFixedItem 固定しているアイテムも更新するかどうか
     * @param applyOffHandItem オフハンドも更新するかどうか
     */
    fun wear(player: Player, applyFixedItem: Boolean = true, applyOffHandItem: Boolean = true) {
        player.inventory?.run {
            heldItemSlot = fixedSlot
            (0..8).forEach { slot ->
                if (!applyFixedItem && slot == fixedSlot) return@forEach
                setItem(slot,
                        buttonMap[slot]?.getItemStack(player) ?: ItemStack(Material.AIR)
                )
            }
            if (!applyOffHandItem) return@run
            itemInOffHand = offHandButton?.getItemStack(player) ?: ItemStack(Material.AIR)
        }
        if (applyFixedItem)
            player.updateInventory()
    }

    fun getHotButton(slot: Int): HotButton? {
        if (isFixed(slot)) return null
        return buttonMap[slot] as HotButton?
    }

    fun findFixedButton(): HandButton? {
        return buttonMap[fixedSlot] as HandButton?
    }

    fun getButton(slot: Int): Button? {
        return if (isFixed(slot)) findFixedButton()
        else getHotButton(slot)
    }

    fun isFixed(slot: Int) = when {
        fixedSlot != slot -> false
        buttonMap[fixedSlot] == null -> false
        else -> true
    }

    fun hasFixedSlot() = buttonMap[fixedSlot] != null

    fun getFixedSlot(): Int? {
        buttonMap[fixedSlot] ?: return null
        return fixedSlot
    }

}