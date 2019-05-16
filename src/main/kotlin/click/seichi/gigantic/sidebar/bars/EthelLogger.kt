package click.seichi.gigantic.sidebar.bars

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.ethel
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.sidebar.Log
import click.seichi.gigantic.sidebar.Logger
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object EthelLogger : Logger("ethel") {

    override fun canShow(player: Player): Boolean {
        return Achievement.FIRST_WILL.isGranted(player)
    }

    override fun getDeque(player: Player): Deque<Log> {
        return player.getOrPut(Keys.ETHEL_DEQUE)
    }

    override fun getTitle(player: Player): String {
        return "${ChatColor.GREEN}${ChatColor.BOLD}" +
                "エーテルログ"
    }

    fun add(player: Player, will: Will, amount: Long) {
        val all = player.ethel(will)
        log(player, "${ChatColor.GREEN}" +
                "" +
                "${will.chatColor}${ChatColor.BOLD}" +
                will.getName(player.wrappedLocale) +
                "${ChatColor.GREEN}" +
                "+$amount" +
                "${ChatColor.DARK_GREEN}" +
                "(" +
                "${all.coerceAtMost(999)}" +
                ")"
        )
    }

    fun use(player: Player, will: Will, amount: Long) {
        val all = player.ethel(will)
        log(player, "${ChatColor.RED}" +
                "" +
                "${will.chatColor}${ChatColor.BOLD}" +
                will.getName(player.wrappedLocale) +
                "${ChatColor.RED}" +
                "-$amount" +
                "${ChatColor.DARK_RED}" +
                "(" +
                "${all.coerceAtMost(999)}" +
                ")"
        )
    }
}