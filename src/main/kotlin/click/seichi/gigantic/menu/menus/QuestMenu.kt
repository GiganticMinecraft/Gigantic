package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.menu.Menu
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object QuestMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        // TODO implements
        return "${ChatColor.BLACK}" +
                "[ここにクエスト名]"
    }

    init {
    }

}