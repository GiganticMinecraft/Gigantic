package click.seichi.gigantic.skill

import click.seichi.gigantic.message.MessageProtocol
import click.seichi.gigantic.message.messages.Message
import click.seichi.gigantic.skill.skills.Explosion
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class SkillResolver(val player: Player) {

    fun fireExplosion(block: Block): Boolean {
        var state: SkillState = SkillState.FAILED_TO_LOAD
        Explosion(player, block).run {
            state = load()
            if (state.canFire) {
                state = fire()
            }
        }
        // TODO remove
        Message(MessageProtocol.SUB_TITLE, state.localizedName).sendTo(player)
        return state.canFire
    }

}