package click.seichi.gigantic.skill

import click.seichi.gigantic.message.LocalizedString
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
abstract class BreakSkill : Skill() {
    // プレイヤーに表示される簡略名（1文字）
    abstract val shortName: LocalizedString

    protected abstract fun getBreakBox(player: Player): BreakBox

    protected abstract fun getToggle(player: Player): Boolean

    protected abstract fun canBreakUnderPlayer(player: Player): Boolean

    protected abstract fun getBreakBlockState(player: Player, block: Block): SkillState
}