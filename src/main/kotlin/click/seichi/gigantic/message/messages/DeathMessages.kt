package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.raid.boss.Boss
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object DeathMessages {

    val PLAYER_DEATH_RAID_BATTLE = { player: Player, boss: Boss ->
        LocalizedText(
                Locale.JAPANESE.let { it to "${ChatColor.WHITE}${player.name}は${boss.localizedName.asSafety(it)}に倒された..." }
        )
    }

}