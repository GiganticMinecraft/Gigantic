package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.getHead
import click.seichi.gigantic.extension.setTitle
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.language.LocalizedText
import click.seichi.gigantic.menu.Button
import click.seichi.gigantic.menu.Menu
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
object MainMenu : Menu() {

    override val type: InventoryType = InventoryType.PLAYER

    init {
        registerButton(0, object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                return player.getHead().apply {
                    setTitle(
                            LocalizedText(
                                    Locale.JAPANESE to "プロフィール"
                            ).asSafety(player.wrappedLocale)
                    )
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {}
        })
    }


    override fun getTitle(player: Player): LocalizedText {
        return LocalizedText(Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}メインメニュー")
    }
}