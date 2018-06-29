package click.seichi.gigantic.skill.skills

import click.seichi.gigantic.message.lang.skill.BreakSkillLang
import click.seichi.gigantic.skill.BreakSkill
import click.seichi.gigantic.skill.SkillState
import click.seichi.gigantic.util.Box
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class Explosion(val block: Block) : BreakSkill() {
    override val shortName = BreakSkillLang.EXPLOSION_SHORT_NAME

    override val displayName = BreakSkillLang.EXPLOSION_LONG_NAME

    override fun getBreakBox(player: Player): Box {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fire(player: Player): SkillState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun load(player: Player): SkillState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isUnlocked(player: Player): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCooldown(player: Player): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}