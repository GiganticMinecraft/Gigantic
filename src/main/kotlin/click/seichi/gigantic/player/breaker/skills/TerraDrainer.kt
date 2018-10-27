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
        breakRelationalBlock(player, block, block)
    }

    private fun breakRelationalBlock(player: Player, base: Block, target: Block) {
        if (!target.isTree) return
        onBreakBlock(player, target)
        // 原木でなければ処理しない
        if (target.isLog) {
            relationalFaceSet.map {
                Bukkit.getScheduler().runTaskLater(
                        Gigantic.PLUGIN,
                        {
                            breakRelationalBlock(player, base, target.getRelative(it))
                        },
                        when (it) {
                            BlockFace.NORTH,
                            BlockFace.EAST,
                            BlockFace.SOUTH,
                            BlockFace.WEST,
                            BlockFace.UP,
                            BlockFace.DOWN -> 10L
                            BlockFace.NORTH_EAST,
                            BlockFace.NORTH_WEST,
                            BlockFace.SOUTH_EAST,
                            BlockFace.SOUTH_WEST,
                            BlockFace.WEST_NORTH_WEST,
                            BlockFace.NORTH_NORTH_WEST,
                            BlockFace.NORTH_NORTH_EAST,
                            BlockFace.EAST_NORTH_EAST,
                            BlockFace.EAST_SOUTH_EAST,
                            BlockFace.SOUTH_SOUTH_EAST,
                            BlockFace.SOUTH_SOUTH_WEST,
                            BlockFace.WEST_SOUTH_WEST,
                            BlockFace.SELF -> 20L
                        }
                )
            }
        }

        breakBlock(player, target, base == target, false)
    }

    override fun onBreakBlock(player: Player, block: Block) {
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