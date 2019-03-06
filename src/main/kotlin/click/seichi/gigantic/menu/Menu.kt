package click.seichi.gigantic.menu

import click.seichi.gigantic.extension.setItemAsync
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.sound.sounds.MenuSounds
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * @author Mon_chi
 * @author tar0ss
 */
abstract class Menu : InventoryHolder {

    private val buttonMap: MutableMap<Int, Button> = mutableMapOf()

    /**
     * 1.13.2 現在　CHEST以外使用不可なためoverride禁止
     *
     */
    val type = InventoryType.CHEST

    open val size by lazy {
        type.defaultSize
    }

    open val openSound = MenuSounds.MENU_OPEN

    open val closeSound = MenuSounds.MENU_CLOSE

    open val titleColor = ChatColor.BLACK

    protected fun getButtonMap() = buttonMap.toMap()

    /**
     *
     * Menuのタイトルを取得します
     * ここで色コードを入れないで下さい．
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
     * @param slot 取得するスロット番号
     */
    fun getButton(slot: Int): Button? {
        return buttonMap[slot]
    }

    /**
     * Menuを開きます
     *
     * @param player Menuを開くPlayer
     * @param isFirst このメニューを開いた時に呼び出される
     * @param playSound 音を流す時true
     *
     */
    open fun open(player: Player, isFirst: Boolean = true, playSound: Boolean = true) {
        onOpen(player, isFirst)
        player.openInventory(createInventory(player))
        if (playSound) openSound.playOnly(player)
    }

    /**
     * Menuを開いたときに初期化処理を実行します
     *
     * @param player Menuを開いたPlayer
     *
     */
    protected open fun onOpen(player: Player, isFirst: Boolean) {}


    protected open fun createInventory(player: Player): Inventory {
        val title = titleColor.toString() + getTitle(player)
        val inventory = when (type) {
            InventoryType.CHEST -> {
                Bukkit.createInventory(this, size, title)
            }
            else -> {
                Bukkit.createInventory(this, type, title)
            }
        }
        (0 until size).forEach { slot ->
            inventory.setItemAsync(player, slot, buttonMap[slot] ?: return@forEach)
        }
        return inventory
    }

    /**
     * Menu を開きなおします．
     *
     * @param player Menuを開くPlayer
     *
     */
    open fun reopen(player: Player) {
        open(player, isFirst = false, playSound = false)
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
        if (playSound) closeSound.playOnly(player)
    }

    /**
     * Menuを閉じます
     *
     * @param player Menuを開くPlayer
     * @param playSound 音を流す時true
     */
    fun close(player: Player, playSound: Boolean = true) {
        player.closeInventory()
        if (playSound) closeSound.playOnly(player)
    }

    override fun getInventory(): Inventory? {
        return null
    }

}