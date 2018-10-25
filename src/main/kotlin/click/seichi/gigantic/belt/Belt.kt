package click.seichi.gigantic.belt

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.button.HotButton
import click.seichi.gigantic.button.buttons.FixedButtons
import click.seichi.gigantic.button.buttons.HotButtons
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.BeltMessages
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
enum class Belt(val id: Int, val localizedName: LocalizedText, fixedButton: Pair<Int, Button>, vararg hotButtons: Pair<Int, HotButton>) {
    DIG(
            1,
            BeltMessages.DIG,
            0 to FixedButtons.SHOVEL,
            1 to HotButtons.FLASH,
            2 to HotButtons.MINE_BURST,
            8 to HotButtons.BELT_SWITCHER_SETTING
    ),
    MINE(
            2,
            BeltMessages.MINE,
            0 to FixedButtons.PICKEL,
            1 to HotButtons.FLASH,
            2 to HotButtons.MINE_BURST,
            8 to HotButtons.BELT_SWITCHER_SETTING
    ),
    CUT(
            3,
            BeltMessages.CUT,
            0 to FixedButtons.AXE,
            1 to HotButtons.FLASH,
            2 to HotButtons.MINE_BURST,
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
     * @param applyFixed 固定しているアイテムも更新するかどうか
     */
    fun wear(player: Player, applyFixed: Boolean = true) {
        player.inventory?.run {
            heldItemSlot = fixedSlot
            (0..8).forEach { slot ->
                if (!applyFixed && slot == fixedSlot) return@forEach
                setItem(slot,
                        buttonMap[slot]?.getItemStack(player) ?: ItemStack(Material.AIR)
                )
            }
        }
        if (applyFixed)
            player.updateInventory()
    }

    fun getHotButton(slot: Int): HotButton? {
        if (isFixed(slot)) return null
        return buttonMap[slot] as HotButton?
    }

    fun getFixedButton(): Button {
        return buttonMap[fixedSlot]!!
    }

    fun getButton(slot: Int): Button? {
        return if (isFixed(slot)) getFixedButton()
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