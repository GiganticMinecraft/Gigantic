package click.seichi.gigantic.belt

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.button.HotButton
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
abstract class Belt {

    private val buttonMap: MutableMap<Int, Button> = mutableMapOf()
    // 手に固定されたスロット番号
    private var fixedSlot: Int? = null

    protected fun registerFixedButton(slot: Int, button: Button) {
        fixedSlot = slot
        buttonMap[slot] = button
    }


    protected fun registerHotButton(slot: Int, button: HotButton) {
        if (slot == fixedSlot) {
            fixedSlot = null
        }
        buttonMap[slot] = button
    }

    /**
     * ベルトを身に着ける
     *
     * @param applyFixed 固定しているアイテムも更新するかどうか
     */
    fun wear(player: Player, applyFixed: Boolean = true) {
        player.inventory?.run {
            fixedSlot?.let { heldItemSlot = it }
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

    fun getFixedButton(): Button? {
        return buttonMap[fixedSlot ?: return null] ?: return null
    }

    fun getButton(slot: Int): Button? {
        return if (isFixed(slot)) getFixedButton()
        else getHotButton(slot)
    }

    fun isFixed(slot: Int) = when {
        fixedSlot == null -> false
        fixedSlot != slot -> false
        buttonMap[fixedSlot!!] == null -> false
        else -> true
    }

    fun hasFixedSlot() = fixedSlot != null && buttonMap[fixedSlot!!] != null

    fun getFixedSlot(): Int? {
        buttonMap[fixedSlot ?: return null] ?: return null
        return fixedSlot
    }
}