package click.seichi.gigantic.message.messages

import click.seichi.gigantic.extension.hasAptitude
import click.seichi.gigantic.extension.memory
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.SideBarMessage
import click.seichi.gigantic.util.SideBarRow
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object SideBarMessages {

    val MEMORY_SIDEBAR = { player: Player, isForced: Boolean ->
        val willMap = Will.values()
                .filter { player.hasAptitude(it) }
                .map { it to player.memory(it) }.toMap()
        SideBarMessage(
                "memory",
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.DARK_GREEN}" +
                                "遺志の記憶"
                ),
                willMap.keys.map { will ->
                    SideBarRow.getRowById(will.id) to LocalizedText(
                            Locale.JAPANESE.let {
                                it to "${ChatColor.GREEN}" +
                                        "${will.localizedName.asSafety(it)} : " +
                                        "${ChatColor.RESET}${ChatColor.WHITE}" +
                                        "${willMap[will] ?: 0}個${ChatColor.RESET}"
                            }
                    )
                }.toMap()
                , isForced
        )
    }
}