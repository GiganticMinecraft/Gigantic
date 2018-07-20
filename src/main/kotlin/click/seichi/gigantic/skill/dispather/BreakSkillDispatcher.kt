package click.seichi.gigantic.skill.dispather

import click.seichi.gigantic.event.events.BlockBreakSkillEvent
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.cardinalDirection
import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.skill.SkillState
import click.seichi.gigantic.skill.breakskill.BreakBox
import click.seichi.gigantic.skill.breakskill.BreakSkill
import click.seichi.gigantic.sound.sounds.PlayerSounds
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
        val numOfBreak = targetSet.size

        val consumeMana = skill.calcConsumeMana(numOfBreak).toLong()
        val playerMana = gPlayer.mana.current

        if (playerMana < consumeMana) {
            sendErrorMessage(SkillState.NO_MANA)
            return false
        }

        gPlayer.mana.decrease(consumeMana)
        PlayerMessages.MANA_DISPLAY(gPlayer.manaBar, gPlayer.mana)


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
        ChatMessage(ChatMessageProtocol.CHAT, state.LocalizedText).sendTo(player)
    }

    private fun fire(): SkillState {
        // TODO coolTime invoke
        gPlayer.run {
            mineBlock.add(targetSet.size.toLong(), mineBlockReason)
            val player = player

            val level = player.gPlayer?.level ?: return@run
            val isLevelUp = level.updateLevel(ExpProducer.calcExp(player)) {
                Bukkit.getPluginManager().callEvent(LevelUpEvent(it, player))
            }
            PlayerMessages.LEVEL_DISPLAY(level).sendTo(player)
            if (isLevelUp) {
                PlayerSounds.LEVEL_UP.play(player.location)
            } else {
                PlayerSounds.OBTAIN_EXP.playOnly(player)
            }
        }

        targetSet.forEach { block ->
            Bukkit.getPluginManager().callEvent(BlockBreakSkillEvent(player, block))
            //TODO removeMetadata
            block.type = Material.AIR
        }
        return SkillState.FIRE_COMPLETED
    }


}