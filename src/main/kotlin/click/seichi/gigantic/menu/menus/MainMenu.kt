package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.getHead
import click.seichi.gigantic.extension.setTitle
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.language.LocalizedText
import click.seichi.gigantic.menu.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.sound.MenuSounds
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * player がEキーで開けるインベントリにはOpenEventが反応しない。
 * Listenerでinventoryの全てのclickをキャンセルすることで、
 * player inventory を menu として扱う。 最終更新日 2018/07/12
 *
 * @author tar0ss
 */
object MainMenu : Menu() {

    override val type: InventoryType = InventoryType.PLAYER

    init {
        registerButton(9, object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                return player.getHead().apply {
                    setTitle(
                            LocalizedText(
                                    Locale.JAPANESE to "プロフィール"
                            ).asSafety(player.wrappedLocale)
                    )
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                player.sendMessage("hello world!!")
            }
        })
    }

    override fun open(player: Player, playSound: Boolean) {
        player.openInventory(player.inventory)
        if (playSound) MenuSounds.MENU_OPEN.play(player)
    }

    override fun getTitle(player: Player): LocalizedText? {
        return null
    }
}