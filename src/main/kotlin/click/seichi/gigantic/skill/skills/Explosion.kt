package click.seichi.gigantic.skill.skills

import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.lang.skill.BreakSkillLang
import click.seichi.gigantic.skill.BreakBox
import click.seichi.gigantic.skill.BreakSkill
import click.seichi.gigantic.skill.BreakStyle
import click.seichi.gigantic.skill.SkillState
import click.seichi.gigantic.util.Box
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class Explosion(player: Player, private val block: Block) : BreakSkill(player) {

    override val shortName = BreakSkillLang.EXPLOSION_SHORT_NAME

    override val displayName = BreakSkillLang.EXPLOSION_LONG_NAME

    // TODO implements
    override val isUnlocked: Boolean = true // player.profile.seichiLevel > 10

    // TODO implements
    override val isCooldown: Boolean = false

    // TODO implements
    override val toggle: Boolean = true

    // TODO implements
    override val skillLevel: Int = 3

    // TODO implements
    override val coolTime: Long = 0

    // TODO implements
    override val consumeMana: Long = 0

    // TODO implements
    override val consumeDurability: Long = 0


    private val box = when (skillLevel) {
        1 -> Box(1, 2, 1)
        2 -> Box(3, 2, 1)
        3 -> Box(3, 3, 3)
        in 4..9 -> Box(
                (skillLevel - 2) * 2 + 1,
                (skillLevel - 3) * 2 + 1,
                (skillLevel - 2) * 2 + 1
        )
        else -> throw IllegalArgumentException()
    }

    private val style = BreakStyle.NORMAL

    override val breakBox by lazy {
        BreakBox(box, style, block, player.cardinalDirection)
    }

    override val targetSet by lazy {
        breakBox.blockSet
                .filter { getBlockState(it).canFire }
                .toSet()
    }

    override fun load(): SkillState {
        return when {
            !isUnlocked -> SkillState.LOCKED
            isCooldown -> SkillState.COOLDOWN
            player.gameMode != GameMode.SURVIVAL -> SkillState.NOT_SURVIVAL
            !player.world.isSeichiWorld -> SkillState.NOT_SEICHI_WORLD
            player.isFlying -> SkillState.FLYING
            !tool.isSeichiTool -> SkillState.NOT_SEICHI_TOOL
            !toggle -> SkillState.NOT_ACTIVATE
            breakBox.upperBlockSet.firstOrNull { it.isRoot } == null -> SkillState.UPPER_BLOCK
            targetSet.isEmpty() -> breakBox.blockSet
                    .firstOrNull()?.let { getBlockState(it) } ?: SkillState.NO_BLOCK
            !tool.canConsumeDurability(consumeDurability) -> SkillState.NO_DURABILITY
            mana < consumeMana -> SkillState.NO_MANA
            else -> SkillState.ACTIVATE
        }
    }

    override fun fire(): SkillState {
        // TODO　mana and durability decrease
        // TODO skilllevel update
        // TODO coolTime invoke
        targetSet.forEach { block ->
            //TODO setMetadata
            //TODO minestack add
            //TODO add Statistic
            block.type = Material.AIR
        }
        return SkillState.FIRE_COMPLETED
    }

    override fun getBlockState(block: Block) = when {
    // TODO WorldGuard
    // TODO METADATA
        !canBreakUnderPlayer(block) -> SkillState.UNDER_PLAYER
        else -> SkillState.ACTIVATE
    }

    override fun canBreakUnderPlayer(block: Block): Boolean {
        return true
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}