package click.seichi.gigantic.skill.skills

import click.seichi.gigantic.message.lang.skill.BreakSkillLang
import click.seichi.gigantic.skill.BreakSkill
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class Explosion : BreakSkill() {

    override val shortName = BreakSkillLang.EXPLOSION_SHORT_NAME

    override val displayName = BreakSkillLang.EXPLOSION_LONG_NAME

    override fun fire(player: Player, baseBlock: Block): Boolean {
        // TODO implements
        return false
    }

}