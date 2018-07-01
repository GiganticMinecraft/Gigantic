package click.seichi.gigantic.skill

import click.seichi.gigantic.extension.profile
import click.seichi.gigantic.message.LocalizedString
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
abstract class BreakSkill(player: Player) : Skill(player) {
    // プレイヤーに表示される簡略名（1文字）
    abstract val shortName: LocalizedString

    abstract val breakBox: BreakBox

    abstract val targetSet: Set<Block>

    abstract val skillLevel: Int

    abstract val toggle: Boolean

    abstract val consumeMana: Long

    abstract val consumeDurability: Long

    abstract val coolTime: Long

    val tool: ItemStack = player.inventory.itemInMainHand

    val mana = player.profile?.mana ?: 0

    abstract fun canBreakUnderPlayer(block: Block): Boolean

    abstract fun getBlockState(block: Block): SkillState
}