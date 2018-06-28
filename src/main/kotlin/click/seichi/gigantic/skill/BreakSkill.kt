package click.seichi.gigantic.skill

import click.seichi.gigantic.message.LocalizedString
import click.seichi.gigantic.util.Box
import org.bukkit.entity.Player
import org.bukkit.event.Event

/**
 * @author tar0ss
 */
class BreakSkill(
        override val displayName: LocalizedString,
        override val shortName: LocalizedString,
        val box: Box,
        val breakStyle: BreakStyle
) : Skill() {

    override fun fire(player: Player, event: Event): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}