package click.seichi.gigantic.message.messages

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object DeathMessages {

    val PLAYER_DEATH_RAID_BATTLE = { boss: Boss ->
        LocalizedText(
                Locale.JAPANESE.let { it to "${ChatColor.GRAY}${boss.localizedName.asSafety(it)}に倒された..." }
        )
    }

}