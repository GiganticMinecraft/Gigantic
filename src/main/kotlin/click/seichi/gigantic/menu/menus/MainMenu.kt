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
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
object MainMenu : Menu() {

    override val size: Int = 4 * 9

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
//            override fun getItemStack(gPlayer: CraftPlayer): ItemStack {
//                return gPlayer.gPlayer.getHeadItem().apply {
//                    setTitle(gPlayer.name)
//                    setLore("総整地量:${gPlayer.statistic.mineBlock.all}", prefix = "$AQUA")
//                }
//            }
//
//            override fun onClick(gPlayer: CraftPlayer, event: InventoryClickEvent) {
//            }
//        })
//        registerButton(1, object : Button {
//            override fun getItemStack(gPlayer: CraftPlayer): ItemStack {
//                return ItemStack(Material.TRIPWIRE_HOOK, 1).apply {
//                    setTitle("設定")
//                }
//            }
//
//            override fun onClick(gPlayer: CraftPlayer, event: InventoryClickEvent) {
//                SettingMenu.open(gPlayer)
//            }
//        })
    }


    override fun getTitle(player: Player): LocalizedText {
        return LocalizedText(Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}メインメニュー")
    }
}