package click.seichi.gigantic.menu

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.MenuMessages
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

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
    abstract fun getTitle(player: Player): LocalizedText?

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
    open fun getButton(slot: Int): Button? {
        return buttonMap[slot]
    }

    /**
     * Menuを開きます
     *
     * @param player Menuを開くPlayer
     * @param playSound 音を流す時true
     *
     */
    open fun open(player: Player, playSound: Boolean = true) {
        player.openInventory(getInventory(player))
        if (playSound) MenuMessages.MENU_OPEN.sendTo(player)
    }

    open fun getInventory(player: Player): Inventory {
        val title = getTitle(player)
        val inventory = when (type) {
            InventoryType.CHEST -> {
                if (title == null) Bukkit.createInventory(this, size)
                else Bukkit.createInventory(this, size, title.asSafety(player.wrappedLocale))
            }
            else -> {
                if (title == null) Bukkit.createInventory(this, type)
                else Bukkit.createInventory(this, type, title.asSafety(player.wrappedLocale))
            }
        }
        (0..inventory.size).forEach { slot ->
            val itemStack = getButton(slot)?.getItemStack(player) ?: return@forEach
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
        if (playSound) MenuMessages.MENU_CLOSE.sendTo(player)
    }

    /**
     * Menuを閉じます
     *
     * @param player Menuを開くPlayer
     * @param playSound 音を流す時true
     */
    fun close(player: Player, playSound: Boolean = true) {
        player.closeInventory()
        if (playSound) MenuMessages.MENU_CLOSE.sendTo(player)
    }

    override fun getInventory(): Inventory? {
        return null
    }

}