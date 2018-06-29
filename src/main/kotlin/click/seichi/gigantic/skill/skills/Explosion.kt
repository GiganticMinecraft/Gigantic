package click.seichi.gigantic.skill.skills

import click.seichi.gigantic.extension.isRoot
import click.seichi.gigantic.extension.isSeichiTool
import click.seichi.gigantic.extension.isSeichiWorld
import click.seichi.gigantic.message.lang.skill.BreakSkillLang
import click.seichi.gigantic.skill.BreakBox
import click.seichi.gigantic.skill.BreakSkill
import click.seichi.gigantic.skill.SkillState
import org.bukkit.GameMode
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class Explosion(val block: Block) : BreakSkill() {

    override val shortName = BreakSkillLang.EXPLOSION_SHORT_NAME

    override val displayName = BreakSkillLang.EXPLOSION_LONG_NAME

    override fun load(player: Player): SkillState {
        return when {
            !isUnlocked(player) -> SkillState.LOCKED
            !isCooldown(player) -> SkillState.COOLDOWN
            player.gameMode != GameMode.SURVIVAL -> SkillState.NOT_SURVIVAL
            !player.world.isSeichiWorld -> SkillState.NOT_SEICHI_WORLD
            player.isFlying -> SkillState.FLYING
            !player.inventory.itemInMainHand.isSeichiTool -> SkillState.NOT_SEICHI_TOOL
            !getToggle(player) -> SkillState.NOT_ACTIVATE
            else -> SkillState.ACTIVATE
        }
    }

    override fun fire(player: Player): SkillState {
        // 破壊範囲のクラス生成
        val breakBox = getBreakBox(player)

        // 上方にブロックを検知したとき終了
        if (breakBox.upperBlockSet.firstOrNull { it.isRoot } == null) {
            return SkillState.UPPER_BLOCK
        }

        // 破壊ブロックを取得
        val breakBlockSet = breakBox.blockSet
                .filter { getBreakBlockState(player, it).canFire }
                .toSet()

        // 破壊ブロックが空の時、原因を探して終了
        if (breakBlockSet.isEmpty()) {
            return breakBox.blockSet
                    .map { getBreakBlockState(player, it) }
                    .firstOrNull { !it.canFire } ?: SkillState.NO_BLOCK
        }

        // 破壊可能ブロックが割り出せたのでここから破壊の前処理に入る

        return SkillState.FIRE_COMPLETED
    }

    override fun getBreakBlockState(player: Player, block: Block) = when {
    // TODO WorldGuard
    // TODO METADATA
        canBreakUnderPlayer(player) -> SkillState.UNDER_PLAYER
        else -> SkillState.ACTIVATE
    }

    override fun getBreakBox(player: Player): BreakBox {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isUnlocked(player: Player): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCooldown(player: Player): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getToggle(player: Player): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canBreakUnderPlayer(player: Player): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}