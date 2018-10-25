package click.seichi.gigantic.skill

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.event.events.RelationalBlockBreakEvent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player


/**
 * TODO ここまでめんどくさい仕様にするつもりはなかったので簡素化させる。
 *
 * @author BlackBracken
 * @author tar0ss
 */
class Miner(
        private val broken: Block,
        private val breaker: Player,
        private val materialSet: Set<Material>,
        private val relationalFaceSet: Set<BlockFace>,
        private val constructionType: ConstructionType,
        private val maxDepth: Int = 0,
        private val maxRadius: Int = 0,
        private val nextBreakDelay: Long = 1
) {
    enum class ConstructionType {
        DEPTH,
        RADIUS,
        ;
    }

    lateinit var action: (Block) -> Unit

    init {
        check(when (constructionType) {
            Miner.ConstructionType.DEPTH -> maxDepth != 0
            Miner.ConstructionType.RADIUS -> maxRadius != 0
        }) {
            when (constructionType) {
                Miner.ConstructionType.DEPTH -> "maxDepth is must not be zero"
                Miner.ConstructionType.RADIUS -> "maxRadius is must not be zero"
            }
        }
    }

    fun mineRelations(action: (Block) -> (Unit)) {
        this.action = action
        when (constructionType) {
            Miner.ConstructionType.DEPTH -> mineRelationsUnderDepth(broken, materialSet.toSet(), relationalFaceSet.toSet(), 0)
            Miner.ConstructionType.RADIUS -> mineRelationsUnderRadius(broken, materialSet.toSet(), relationalFaceSet.toSet())
        }

    }


    // type == DEPTH
    private fun mineRelationsUnderDepth(target: Block, materialSet: Set<Material>, relationalFaceSet: Set<BlockFace>, depth: Int) {
        if (!materialSet.contains(target.type)) return
        if (maxDepth <= depth) return

        val event = RelationalBlockBreakEvent(target, breaker, constructionType, depth = depth)
        Gigantic.PLUGIN.server.pluginManager.callEvent(event)
        if (event.isCancelled) return
        action(target)
//        target.world.spawnParticle(Particle.BLOCK_CRACK, target.centralLocation, 1, target.state.data)
//        target.world.playEffect(target.centralLocation, Effect.STEP_SOUND, target.type)
        target.breakNaturally(breaker.inventory.itemInMainHand)

        Bukkit.getScheduler().runTaskLater(
                Gigantic.PLUGIN,
                {
                    relationalFaceSet.forEach { relation ->
                        this.mineRelationsUnderDepth(target.getRelative(relation), materialSet, relationalFaceSet, depth + 1)
                    }
                },
                nextBreakDelay
        )
    }

    // type == RADIUS
    private fun mineRelationsUnderRadius(target: Block, materialSet: Set<Material>, relationalFaceSet: Set<BlockFace>) {
        if (!materialSet.contains(target.type)) return
        if (Math.abs(target.location.x - broken.location.x) >= maxRadius
                || Math.abs(target.location.z - broken.location.z) >= maxRadius) return

        val event = RelationalBlockBreakEvent(target, breaker, constructionType)
        Gigantic.PLUGIN.server.pluginManager.callEvent(event)
        if (event.isCancelled) return
        action(target)
//        target.world.spawnParticle(Particle.BLOCK_CRACK, target.centralLocation, 1, target.blockData)
//        target.world.playEffect(target.centralLocation, Effect.STEP_SOUND, target.type)
        target.breakNaturally(breaker.inventory.itemInMainHand)
        Bukkit.getScheduler().runTaskLater(
                Gigantic.PLUGIN,
                {
                    relationalFaceSet.forEach { relation ->
                        this.mineRelationsUnderRadius(target.getRelative(relation), materialSet, relationalFaceSet)
                    }
                },
                nextBreakDelay
        )
    }
}