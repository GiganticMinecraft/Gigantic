package click.seichi.gigantic.skill

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.SkillAnimations
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.isSurface
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.popup.SkillPops
import click.seichi.gigantic.sound.sounds.SkillSounds
import click.seichi.gigantic.util.Random
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.function.Consumer

/**
 * @author tar0ss
 */
object Skills {

    val MINE_BURST = object : Skill {

        val duration = SkillParameters.MINE_BURST_DURATION
        val coolTime = SkillParameters.MINE_BURST_COOLTIME

        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.MINE_BURST.isUnlocked(player)) return null
            val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return null
            if (!mineBurst.canStart()) return null
            return Consumer { p ->
                mineBurst.coolTime = coolTime
                mineBurst.duration = duration
                mineBurst.onStart {
                    p.removePotionEffect(PotionEffectType.FAST_DIGGING)
                    p.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 100, 2, true, false))
                    SkillSounds.MINE_BURST_ON_FIRE.play(p.location)
                    p.find(Keys.BELT)?.wear(p)
                }.onFire {
                    p.find(Keys.BELT)?.wear(p, false)
                }.onCompleteFire {
                    p.find(Keys.BELT)?.wear(p)
                }.onCooldown {
                    p.find(Keys.BELT)?.wear(p, false)
                }.onCompleteCooldown {
                    p.find(Keys.BELT)?.wear(p)
                }.start()
            }
        }
    }

    val FLASH = object : Skill {

        val transparentMaterialSet = setOf(
                Material.AIR,
                Material.WATER,
                Material.STATIONARY_WATER,
                Material.LAVA,
                Material.STATIONARY_LAVA
        )

        val maxDistance = 50

        val coolTime = SkillParameters.FLASH_COOLTIME

        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.FLASH.isUnlocked(player)) return null
            val flash = player.find(CatalogPlayerCache.FLASH) ?: return null
            if (!flash.canStart()) return null
            return Consumer { p ->
                flash.coolTime = coolTime
                flash.onStart {
                    val tpLocation = p.getTargetBlock(transparentMaterialSet, maxDistance).let { block ->
                        if (block.type == Material.AIR) return@let null
                        var nextBlock = block ?: return@let null
                        while (!nextBlock.isSurface) {
                            nextBlock = nextBlock.getRelative(BlockFace.UP)
                        }
                        nextBlock.location.clone().add(0.0, 1.75, 0.0).apply {
                            direction = p.location.direction
                        }
                    }
                    if (tpLocation != null) {
                        SkillAnimations.FLASH_BEFORE.start(p.location)
                        p.teleport(tpLocation)
                        SkillAnimations.FLASH_BEFORE.start(p.location)
                        SkillSounds.FLASH_FIRE.play(p.location)
                    } else {
                        SkillSounds.FLASH_MISS.play(p.location)
                        flash.isCancelled = true
                    }
                    p.find(Keys.BELT)?.wear(p)
                }.onCooldown {
                    p.find(Keys.BELT)?.wear(p, false)
                }.onCompleteCooldown {
                    p.find(Keys.BELT)?.wear(p)
                }.start()
            }
        }

    }

    val HEAL = object : BreakSkill {
        override fun findInvokable(player: Player, block: Block): Consumer<Player>? {
            if (!LockedFunction.HEAL.isUnlocked(player)) return null
            if (SkillParameters.HEAL_PROBABILITY < Random.nextDouble()) return null
            return Consumer { p ->
                p.manipulate(CatalogPlayerCache.HEALTH) {
                    if (it.isMaxHealth()) return@manipulate
                    val amount = it.increase(it.max.div(100L).times(SkillParameters.HEAL_PERCENT))
                    SkillAnimations.HEAL.start(p.location.clone().add(0.0, 1.7, 0.0))
                    SkillPops.HEAL(amount).pop(block.centralLocation.add(0.0, 0.3, 0.0))
                    PlayerMessages.HEALTH_DISPLAY(it).sendTo(p)
                }
            }
        }

    }

    val SWITCH = object : Skill {
        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.SWITCH.isUnlocked(player)) return null
            return Consumer { p ->
                var current: Belt? = null
                p.manipulate(CatalogPlayerCache.BELT_SWITCHER) {
                    current = it.current
                    it.switch()
                }
                val nextBelt = p.find(Keys.BELT) ?: return@Consumer
                if (current == nextBelt) return@Consumer
                nextBelt.wear(p)
                SkillSounds.SWITCH.playOnly(p)

            }
        }

    }

    val TERRA_DRAIN = object : BreakSkill {
        override fun findInvokable(player: Player, block: Block): Consumer<Player>? {
            if (player.gameMode != GameMode.SURVIVAL) return null
            if (block.type != Material.LOG && block.type != Material.LOG_2) return null
            return Consumer { p ->
                breakTree(p, block, block)
                SkillAnimations.TERRA_DRAIN_HEAL.start(p.location.clone().add(0.0, 1.7, 0.0))
            }
        }

        private fun breakTree(player: Player, tree: Block, baseBlock: Block) {
            if (tree.type != Material.LOG && tree.type != Material.LOG_2 &&
                    tree.type != Material.LEAVES && tree.type != Material.LEAVES_2) return
            if (Math.abs(tree.location.x - baseBlock.location.x) >= 5
                    || Math.abs(tree.location.z - baseBlock.location.z) >= 5) return
            if (tree != baseBlock) {
                SkillAnimations.TERRA_DRAIN_TREE.start(tree.location)
                SkillSounds.TERRA_DRAIN.play(tree.location)
                player.manipulate(CatalogPlayerCache.HEALTH) {
                    if (it.isMaxHealth()) return@manipulate
                    val percent = when (tree.type) {
                        Material.LOG, Material.LOG_2 -> SkillParameters.TERRA_DRAIN_LOG_HEAL_PERCENT
                        Material.LEAVES, Material.LEAVES_2 -> SkillParameters.TERRA_DRAIN_LEAVES_HEAL_PERCENT
                        else -> 0.0
                    }
                    val amount = it.increase(it.max.div(100.0).times(percent).toLong())
                    SkillPops.HEAL(amount).pop(tree.centralLocation)
                    PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
                }
                tree.type = Material.AIR
            }
            for (face in BlockFace.values().subtract(listOf(BlockFace.SELF, BlockFace.DOWN)))
                Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, { breakTree(player, tree.getRelative(face), baseBlock) }, 4L)
        }
    }

//    val EXPLOSION = object : Skill{
//
//        val transparentMaterialSet = setOf(
//                Material.AIR,
//                Material.WATER,
//                Material.STATIONARY_WATER,
//                Material.LAVA,
//                Material.STATIONARY_LAVA
//        )
//
//        val maxDistance = 50
//
//        fun calcCoolTime(num:Long) =  (num.let { Math.pow(it.toDouble(), 0.25) - 1 } * 20).toLong().let { if (it < 20) 0 else it }
//
//        fun calcConsumeMana(num:Long) = num.let { (it / Math.pow(it.toDouble(), 0.2) - 1).toLong() }
//
//        override fun findInvokable(player: Player): Consumer<Player>? {
//            if (!LockedFunction.EXPLOSION.isUnlocked(player)) return null
//            val explosion = player.find(CatalogPlayerCache.EXPLOSION) ?: return null
//            if (!explosion.canStart()) return null
//
//
//
//            return Consumer { p ->
//                explosion.coolTime = coolTime
//                explosion.onStart {
//                    val tpLocation = p.getTargetBlock(transparentMaterialSet, maxDistance).let { block ->
//                        if (block.type == Material.AIR) return@let null
//                        var nextBlock = block ?: return@let null
//                        while (!nextBlock.isSurface) {
//                            nextBlock = nextBlock.getRelative(BlockFace.UP)
//                        }
//                        nextBlock.location.clone().add(0.0, 1.75, 0.0).apply {
//                            direction = p.location.direction
//                        }
//                    }
//                    if (tpLocation != null) {
//                        SkillAnimations.FLASH_BEFORE.start(p.location)
//                        p.teleport(tpLocation)
//                        SkillAnimations.FLASH_BEFORE.start(p.location)
//                        SkillSounds.FLASH_FIRE.play(p.location)
//                    } else {
//                        SkillSounds.FLASH_MISS.play(p.location)
//                        explosion.isCancelled = true
//                    }
//                    p.find(Keys.BELT)?.wear(p)
//                }.onCooldown {
//                    p.find(Keys.BELT)?.wear(p, false)
//                }.onCompleteCooldown {
//                    p.find(Keys.BELT)?.wear(p)
//                }.start()
//            }
//        }
//    }

}