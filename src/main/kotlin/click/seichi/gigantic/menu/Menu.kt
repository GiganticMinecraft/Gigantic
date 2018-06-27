package click.seichi.gigantic.menu

import click.seichi.gigantic.sound.MenuSound
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
abstract class Menu : InventoryHolder {

    private val buttonMap: MutableMap<Int, Button> = mutableMapOf()

    open val type = InventoryType.CHEST

    open val size by lazy {
        type.defaultSize
    }

    protected fun getButtonMap() = buttonMap.toMap()

    /**
     * Menuのタイトルを取得します
     *
     * @param player Menuを開いているplayer
     */
    abstract fun getTitle(player: Player): String

    /**
     * MenuにButtonを登録します
     *
     * @param slot Buttonを登録するスロット番号
     * @param button 登録するButton
     */
    protected fun registerButton(slot: Int, button: Button) {
        buttonMap[slot] = button
    }

    /**
     * スロット番号のButtonを取得します
     *
     * @param slot 取得するスロット番号
     */
    open fun getButton(player: Player, slot: Int): Button? {
        return buttonMap[slot]
    }

    /**
     * Menuを開きます
     *
     * @param player Menuを開くPlayer
     * @param playSound 音を流す時true
     *
     */
    fun open(player: Player, playSound: Boolean = true) {
        player.openInventory(getInventory(player))
        if (playSound) MenuSound.MENU_OPEN.play(player)
    }

    protected open fun getInventory(player: Player): Inventory {
        val inventory = if (type == InventoryType.CHEST)
            Bukkit.createInventory(this, size, getTitle(player))
        else
            Bukkit.createInventory(this, type, getTitle(player))

        (0..inventory.size).forEach { slot ->
            val itemStack = getButton(player, slot)?.getItemStack(player) ?: return@forEach
            inventory.setItem(slot, itemStack)
        }
        return inventory
    }

    /**
     * Menu を開きなおします．
     *
     * @param player Menuを開くPlayer
     *
     */
    fun reopen(player: Player) {
        open(player, playSound = false)
    }


    /**
     * 指定されたMenu に戻ります
     *
     * @param menu 戻りたいメニュー
     * @param playSound 音を流す時true
     *
     */
    fun back(menu: Menu, player: Player, playSound: Boolean = true) {
        menu.open(player, playSound = false)
        if (playSound) MenuSound.MENU_CLOSE.play(player)
    }

    /**
     * Menuを閉じます
     *
     * @param player Menuを開くPlayer
     * @param playSound 音を流す時true
     */
    fun close(player: Player, playSound: Boolean = true) {
        player.closeInventory()
        if (playSound) MenuSound.MENU_CLOSE.play(player)
    }

    override fun getInventory(): Inventory? {
        return null
    }

    interface Button {

        /**
         * Menuに表示されるItemStackを取得します
         *
         * @param player Menuを開いているPlayer
         */
        fun getItemStack(player: Player): ItemStack?

        /**
         * Buttonクリック時に実行されます
         *
         * @param event クリック時のInventoryClickEvent
         */
        fun onClick(player: Player, event: InventoryClickEvent)
    }

}