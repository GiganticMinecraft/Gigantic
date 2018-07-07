package click.seichi.gigantic.skill.dispather

import click.seichi.gigantic.event.events.BlockBreakSkillEvent
import click.seichi.gigantic.extension.cardinalDirection
import click.seichi.gigantic.message.MessageProtocol
import click.seichi.gigantic.message.messages.Message
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.skill.SkillState
import click.seichi.gigantic.skill.breakskill.BreakBox
import click.seichi.gigantic.skill.breakskill.BreakSkill
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block

/**
 * @author tar0ss
 */
class BreakSkillDispatcher(
        override val skill: BreakSkill,
        override val gPlayer: GiganticPlayer,
        private val mineBlockReason: MineBlockReason,
        block: Block
) : SkillDispatcher {

    val player = gPlayer.player

    private val breakBox = BreakBox(
            skill.calcBox(gPlayer),
            skill.getStyle(gPlayer),
            block,
            player.cardinalDirection
    )

    private val targetSet = breakBox.blockSet
            .filter { !it.isEmpty }
            .filter { skill.getState(it).canFire }.toSet()


    override fun dispatch(): Boolean {
        skill.getState(gPlayer).let {
            if (!it.canFire) {
                sendErrorMessage(it)
                return false
            }
        }

        if (targetSet.isEmpty()) {
            (breakBox.blockSet
                    .firstOrNull()
                    ?.let { skill.getState(it) } ?: SkillState.NO_BLOCK
                    ).let {
                if (!it.canFire) {
                    sendErrorMessage(it)
                    return false
                }
            }
        }

        fire().let {
            if (!it.canFire) {
                sendErrorMessage(it)
                return false
            }
        }

        return true
    }

    private fun sendErrorMessage(state: SkillState) {
        // TODO implements
        if (state.canFire) return
        Message(MessageProtocol.CHAT, state.localizedName).sendTo(player)
    }

    private fun fire(): SkillState {
        // TODO　mana and durability decrease
        // TODO skilllevel update
        // TODO coolTime invoke
        gPlayer.status.run {
            mineBlock.add(targetSet.size.toLong(), mineBlockReason)
        }

        targetSet.forEach { block ->
            Bukkit.getPluginManager().callEvent(BlockBreakSkillEvent(player, block))
            //TODO removeMetadata
            block.type = Material.AIR
        }
        return SkillState.FIRE_COMPLETED
    }


}