package click.seichi.gigantic.player.breaker.skills

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.SkillAnimations
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.breaker.Cutter
import click.seichi.gigantic.player.breaker.RelationalBreaker
import click.seichi.gigantic.player.skill.SkillParameters
import click.seichi.gigantic.popup.SkillPops
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class TerraDrainer : Cutter(), RelationalBreaker {

    private val relationalFaceSet = SkillParameters.TERRA_DRAIN_FACE_SET

    override fun breakRelations(player: Player, block: Block) {
        if (!block.isLog) return
        SkillAnimations.TERRA_DRAIN_HEAL.start(player.location.clone().add(0.0, 1.7, 0.0))
        breakRelationalBlock(player, block, true)
    }

    private fun breakRelationalBlock(player: Player, target: Block, isBaseBlock: Boolean) {
        if (!target.isTree) return
        player.server.consoleSender.sendMessage("${target.type} ${target.x} ${target.y} ${target.z} ")

        // 原木でなければ処理しない
        if (target.isLog) {
            relationalFaceSet.map {
                Bukkit.getScheduler().runTaskLater(
                        Gigantic.PLUGIN,
                        {
                            breakRelationalBlock(player, target.getRelative(it), false)
                        },
                        when (it) {
                            BlockFace.NORTH -> 2L
                            BlockFace.EAST -> 4L
                            BlockFace.SOUTH -> 6L
                            BlockFace.WEST -> 8L
                            BlockFace.UP -> 10L
                            BlockFace.DOWN -> 12L
                            BlockFace.NORTH_EAST -> 14L
                            BlockFace.NORTH_WEST -> 16L
                            BlockFace.SOUTH_EAST -> 18L
                            BlockFace.SOUTH_WEST -> 20L
                            BlockFace.WEST_NORTH_WEST -> 22L
                            BlockFace.NORTH_NORTH_WEST -> 24L
                            BlockFace.NORTH_NORTH_EAST -> 26L
                            BlockFace.EAST_NORTH_EAST -> 28L
                            BlockFace.EAST_SOUTH_EAST -> 30L
                            BlockFace.SOUTH_SOUTH_EAST -> 32L
                            BlockFace.SOUTH_SOUTH_WEST -> 34L
                            BlockFace.WEST_SOUTH_WEST -> 36L
                            BlockFace.SELF -> 0L
                        }
                )
            }
        }
        onSkillBreak(player, target)
        if (!isBaseBlock)
            breakBlock(player, target, false, false)
    }

    private fun onSkillBreak(player: Player, block: Block) {
        SkillAnimations.TERRA_DRAIN_TREE.start(block.centralLocation)
        SkillSounds.TERRA_DRAIN.play(block.centralLocation)
        player.manipulate(CatalogPlayerCache.HEALTH) {
            if (it.isMaxHealth()) return@manipulate
            val percent = when {
                block.isLog -> SkillParameters.TERRA_DRAIN_LOG_HEAL_PERCENT
                block.isLeaves -> SkillParameters.TERRA_DRAIN_LEAVES_HEAL_PERCENT
                else -> 0.0
            }
            val wrappedAmount = it.increase(it.max.div(100.0).times(percent).toLong())
            if (wrappedAmount > 0) {
                SkillPops.HEAL(wrappedAmount).pop(block.centralLocation)
                PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
            }
        }
    }

}