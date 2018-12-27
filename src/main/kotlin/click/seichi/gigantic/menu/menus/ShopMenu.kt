package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.ShopMenuMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object ShopMenu : Menu() {

    override val size: Int
        get() = 54

    override fun getTitle(player: Player): String {
        return ShopMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        /*GiganticEffect.values().forEach { effect ->
            registerButton(effect.slot, object : click.seichi.gigantic.item.Button {
                override fun findItemStack(player: Player): ItemStack? {

                }

                override fun onClick(player: Player, event: InventoryClickEvent) {
                }

            }
            )
        }*/
    }

}