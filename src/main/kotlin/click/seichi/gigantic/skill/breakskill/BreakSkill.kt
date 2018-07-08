package click.seichi.gigantic.skill.breakskill

import click.seichi.gigantic.extension.isSeichiTool
import click.seichi.gigantic.extension.isSeichiWorld
import click.seichi.gigantic.language.LocalizedText
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.skill.Skill
import click.seichi.gigantic.skill.SkillState
import click.seichi.gigantic.util.Box
import org.bukkit.GameMode
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
abstract class BreakSkill : Skill() {
    // プレイヤーに表示される簡略名（1文字）
    abstract val shortName: LocalizedText

    abstract fun calcConsumeMana(n: Int): Int

    abstract fun calcConsumeDurability(n: Int): Int

    abstract fun calcCoolTime(n: Int): Long

    abstract fun calcBox(gPlayer: GiganticPlayer): Box

    abstract fun isCooldown(gPlayer: GiganticPlayer): Boolean

    abstract fun getConsumeTool(gPlayer: GiganticPlayer): ItemStack

    abstract fun isActive(gPlayer: GiganticPlayer): Boolean

    open fun getState(gPlayer: GiganticPlayer): SkillState {
        val player = gPlayer.player
        val tool = getConsumeTool(gPlayer)
        return when {
            isUnlocked(gPlayer) -> SkillState.LOCKED
            isCooldown(gPlayer) -> SkillState.COOLDOWN
            player.gameMode != GameMode.SURVIVAL -> SkillState.NOT_SURVIVAL
            !player.world.isSeichiWorld -> SkillState.NOT_SEICHI_WORLD
            player.isFlying -> SkillState.FLYING
            !tool.isSeichiTool -> SkillState.NOT_SEICHI_TOOL
            !isActive(gPlayer) -> SkillState.NOT_ACTIVATE
            else -> SkillState.ACTIVATE
        }
    }

    open fun getState(block: Block): SkillState = when {
    // TODO METADATA
        !canBreakUnderPlayer(block) -> SkillState.FOOTHOLD_BLOCK
        else -> SkillState.ACTIVATE
    }

    private fun canBreakUnderPlayer(block: Block): Boolean {
        // TODO implements
        return true
    }

    abstract fun getStyle(gPlayer: GiganticPlayer): BreakStyle
}