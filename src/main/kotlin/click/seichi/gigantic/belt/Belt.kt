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
    private val hotButtonMap: MutableMap<Int, HotButton> = mutableMapOf()
    private val slotMap: MutableMap<HotButton, Int> = mutableMapOf()
    // 手に固定されたスロット番号
    private var fixedSlot: Int? = null
    private var fixedButton: Button? = null

    protected fun registerFixedButton(slot: Int?, button: Button?) {
        if (slot != null && hotButtonMap.containsKey(slot)) {
            slotMap.remove(hotButtonMap.remove(slot))
        }
        fixedSlot = slot
        fixedButton = button
    }


    protected fun registerHotButton(slot: Int, button: HotButton) {
        if (slot == fixedSlot) {
            fixedSlot = null
            fixedButton = null
        }
        hotButtonMap[slot] = button
        slotMap[button] = slot
    }

    fun wear(player: Player) {
        player.inventory?.run {
            fixedSlot?.let { heldItemSlot = it }
            (0..8).forEach { slot ->
                setItem(slot,
                        if (isFixed(slot)) {
                            fixedButton
                        } else {
                            hotButtonMap[slot]
                        }?.getItemStack(player) ?: ItemStack(Material.AIR)
                )
            }
        }
        player.updateInventory()
    }

    fun updateHotButton(player: Player, button: HotButton) {
        if (!slotMap.contains(button)) return
        player.inventory?.run {
            setItem(slotMap[button]!!, button.getItemStack(player) ?: ItemStack(Material.AIR))
        }
    }

    fun updateFixedButton(player: Player, button: Button) {
        if (fixedButton != button) return
        player.inventory?.run {
            setItem(fixedSlot!!, button.getItemStack(player) ?: ItemStack(Material.AIR))
        }
    }

    fun getHotButton(slot: Int): HotButton? {
        return hotButtonMap[slot]
    }

    fun getFixedButton(): Button? {
        return fixedButton
    }

    fun getButton(slot: Int): Button? {
        return if (isFixed(slot)) getFixedButton()
        else getHotButton(slot)
    }

    fun isFixed(slot: Int) = fixedButton != null && fixedSlot == slot

}