package click.seichi.gigantic.sidebar.bars

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.ethel
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.sidebar.Log
import click.seichi.gigantic.sidebar.Logger
import click.seichi.gigantic.sidebar.SideBarType
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
class EthelLogger : Logger("ethel") {

    override val type: SideBarType
        get() = SideBarType.Ethel

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

/*
    override fun getMessageMap(player: Player): Map<SideBarRow, String> {
        val willMap = Will.values()
                .filter { player.hasAptitude(it) }
                .map { it to player.ethel(it) }.toMap()
        return willMap.keys.filter {
            willMap.getValue(it) > 0
        }.filter {
            // TODO ここはこの実装だと新意志実装時に追記し忘れる可能性あり
            // イベントは特殊な形でフィルター
            when (it) {
                Will.SAKURA -> GiganticEvent.SAKURA.isActive()
                Will.MIO -> GiganticEvent.MIO.isActive()
                else -> true
            }
        }.map { will ->
            val row = when (will) {
                Will.AQUA -> SideBarRow.TWO
                Will.IGNIS -> SideBarRow.THREE
                Will.AER -> SideBarRow.FIVE
                Will.TERRA -> SideBarRow.ONE
                Will.NATURA -> SideBarRow.FOUR
                Will.GLACIES -> SideBarRow.SIX
                Will.LUX -> SideBarRow.NINE
                Will.SOLUM -> SideBarRow.SEVEN
                Will.UMBRA -> SideBarRow.TEN
                Will.VENTUS -> SideBarRow.EIGHT
                else -> SideBarRow.FOURTEEN
            }
            row to "${will.chatColor}${ChatColor.BOLD}" +
                    "${will.getName(player.wrappedLocale).let {
                        if (it.length == 2) it
                        else " $it "
                    }}:" +
                    "${ChatColor.RESET}" +
                    "${will.chatColor}" +
                    "${willMap.getValue(will).coerceAtMost(999)}".padStart(4, ' ')

        }.toMap()
    }*/
}