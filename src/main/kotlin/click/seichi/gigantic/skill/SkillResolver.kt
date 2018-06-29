package click.seichi.gigantic.skill

import click.seichi.gigantic.skill.skills.Explosion
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class SkillResolver(val player: Player) {

    fun fireExplosion(block: Block) {
        var state: SkillState
        Explosion(block).run {
            state = load(player)
            if (state.canFire) {
                state = fire(player)
            }
        }
    }

}