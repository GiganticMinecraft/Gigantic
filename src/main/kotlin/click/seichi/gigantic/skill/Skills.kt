package click.seichi.gigantic.skill

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.SkillAnimations
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.popup.SkillPops
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.sound.sounds.PlayerSounds
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
 *
 * TODO スキルレベルの概念を追加する
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
                Material.LAVA
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
                        nextBlock.centralLocation.add(0.0, 1.75, 0.0).apply {
                            direction = p.location.direction
                        }
                    }
                    if (tpLocation != null) {
                        SkillAnimations.FLASH_FIRE.start(p.location)
                        p.teleport(tpLocation)
                        SkillAnimations.FLASH_FIRE.start(p.location)
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
                    val wrappedAmount = it.increase(it.max.div(100L).times(SkillParameters.HEAL_PERCENT))
                    SkillAnimations.HEAL.start(p.location.clone().add(0.0, 1.7, 0.0))
                    SkillPops.HEAL(wrappedAmount).pop(block.centralLocation.add(0.0, 0.3, 0.0))
                    PlayerMessages.REGAIN_HEALTH_DISPLAY(it, wrappedAmount).sendTo(p)
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

    // TODO 再帰的処理をEvent依存に変更
    val TERRA_DRAIN = object : BreakSkill {

        val faceList = listOf(
                BlockFace.UP,
                BlockFace.DOWN,
                BlockFace.NORTH,
                BlockFace.WEST,
                BlockFace.SOUTH,
                BlockFace.EAST
        )


        override fun findInvokable(player: Player, block: Block): Consumer<Player>? {
            if (player.gameMode != GameMode.SURVIVAL) return null
            if (!block.isTree) return null
            return Consumer { p ->
                breakTree(p, block, block)
                SkillAnimations.TERRA_DRAIN_HEAL.start(p.location.clone().add(0.0, 1.7, 0.0))
            }
        }

        private fun breakTree(player: Player, target: Block, base: Block) {
            if (!target.isTree) return
            if (Math.abs(target.location.x - base.location.x) >= 5
                    || Math.abs(target.location.z - base.location.z) >= 5) return
            if (target != base) {
                SkillAnimations.TERRA_DRAIN_TREE.start(target.location)
                SkillSounds.TERRA_DRAIN.play(target.location)
                player.manipulate(CatalogPlayerCache.HEALTH) {
                    if (it.isMaxHealth()) return@manipulate
                    val percent = when {
                        target.isLog -> SkillParameters.TERRA_DRAIN_LOG_HEAL_PERCENT
                        target.isLeaves -> SkillParameters.TERRA_DRAIN_LEAVES_HEAL_PERCENT
                        else -> 0.0
                    }
                    val wrappedAmount = it.increase(it.max.div(100.0).times(percent).toLong())
                    if (wrappedAmount > 0) {
                        SkillPops.HEAL(wrappedAmount).pop(target.centralLocation)
                        PlayerMessages.REGAIN_HEALTH_DISPLAY(it, wrappedAmount).sendTo(player)
                    }
                }
                target.type = Material.AIR
            }
            faceList.forEach { face ->
                val delay = when (face) {
                    BlockFace.UP -> 0L
                    BlockFace.DOWN -> 1L
                    BlockFace.NORTH -> 2L
                    BlockFace.SOUTH -> 3L
                    BlockFace.EAST -> 4L
                    BlockFace.WEST -> 5L
                    else -> 3L
                }
                Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                    breakTree(player, target.getRelative(face), base)
                }, delay)
            }
        }
    }


    val EXPLOSION = object : BreakSkill {

        val faceList = listOf(
                BlockFace.UP,
                BlockFace.DOWN,
                BlockFace.NORTH,
                BlockFace.WEST,
                BlockFace.SOUTH,
                BlockFace.EAST
        )

        override fun findInvokable(player: Player, block: Block): Consumer<Player>? {
            if (player.gameMode != GameMode.SURVIVAL) return null
            if (!block.isCrust) return null
            return Consumer { p ->
                breakBlock(p, p.location.blockY, p.isSneaking, block, block)
            }
        }

        private fun breakBlock(player: Player, minY: Int, isSneaking: Boolean, target: Block, base: Block) {
            if (!target.isCrust) return
            if (Math.abs(target.location.x - base.location.x) >= 7
                    || Math.abs(target.location.z - base.location.z) >= 7
                    || Math.abs(target.location.y - base.location.y) >= 1
                    || (target.y < minY && !isSneaking)
            ) return
            if (target != base) {
//                SkillAnimations.TERRA_DRAIN_TREE.start(target.location)
//                SkillSounds.TERRA_DRAIN.play(target.location)
                target.type = Material.AIR
                // Gravity process
                target.fallUpper()
                // carry player cache
                player.manipulate(CatalogPlayerCache.MINE_BLOCK) {
                    it.add(1L)
                }
                player.manipulate(CatalogPlayerCache.MINE_COMBO) {
                    it.combo(1L)
                    SkillPops.MINE_COMBO(it).pop(target.centralLocation)
                }
                // raid battle process
                RaidManager.playBattle(player)

                player.manipulate(CatalogPlayerCache.LEVEL) {
                    it.calculate(ExpProducer.calcExp(player)) { current ->
                        Bukkit.getPluginManager().callEvent(LevelUpEvent(current, player))
                    }
                    PlayerMessages.EXP_BAR_DISPLAY(it).sendTo(player)
                }

                val currentCombo = player.find(CatalogPlayerCache.MINE_COMBO)?.currentCombo ?: 0

                PlayerSounds.OBTAIN_EXP(currentCombo).playOnly(player)
            }
            faceList.forEach { face ->
                val delay = when (face) {
                    BlockFace.UP -> 0L
                    BlockFace.DOWN -> 1L
                    BlockFace.NORTH -> 2L
                    BlockFace.SOUTH -> 3L
                    BlockFace.EAST -> 4L
                    BlockFace.WEST -> 5L
                    else -> 3L
                }
//                Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                breakBlock(player, minY, isSneaking, target.getRelative(face), base)
//                }, delay)
            }
        }

    }

}