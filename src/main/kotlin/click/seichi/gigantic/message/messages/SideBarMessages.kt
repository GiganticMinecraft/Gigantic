package click.seichi.gigantic.message.messages

import click.seichi.gigantic.cache.manipulator.manipulators.Memory
import click.seichi.gigantic.cache.manipulator.manipulators.WillAptitude
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.SideBarMessage
import click.seichi.gigantic.util.SideBarRow
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object SideBarMessages {

    val MEMORY_SIDEBAR = { memory: Memory, aptitude: WillAptitude, isForced: Boolean ->
        val willMap = Will.values()
                .filter { aptitude.has(it) }
                .map { it to (memory.get(it)) }.toMap()
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