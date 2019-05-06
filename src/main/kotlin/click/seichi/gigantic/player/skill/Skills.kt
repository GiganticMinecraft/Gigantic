package click.seichi.gigantic.player.skill

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.SkillAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.event.events.ComboEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.message.messages.PopUpMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.player.Invokable
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.popup.PopUp
import click.seichi.gigantic.popup.SimpleAnimation
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SkillSounds
import click.seichi.gigantic.util.Random
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.function.Consumer

/**
 * @author tar0ss
 */
object Skills {

    val MINE_BURST = object : Invokable {

        val duration = Config.SKILL_MINE_BURST_DURATION
        val coolTime = Config.SKILL_MINE_BURST_COOLTIME

        override fun findInvokable(player: Player): Consumer<Player>? {
            val mineBurst = player.getOrPut(Keys.SKILL_MINE_BURST)
            if (!mineBurst.canStart()) return null
            return Consumer { p ->
                mineBurst.coolTime = coolTime
                mineBurst.duration = duration
                mineBurst.onStart {
                    if (!p.isValid) return@onStart
                    p.removePotionEffect(PotionEffectType.FAST_DIGGING)
                    p.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 100, 2, true, false))
                    SkillSounds.MINE_BURST_ON_FIRE.play(player.eyeLocation)
                    p.updateBelt(true, false)
                    p.fixHandToTool()
                }.onFire {
                    if (!p.isValid) return@onFire
                    p.updateBelt(false, false)
                }.onCompleteFire {
                    if (!p.isValid) return@onCompleteFire
                    p.updateBelt(true, false)
                }.onCooldown {
                    if (!p.isValid) return@onCooldown
                    p.updateBelt(false, false)
                }.onCompleteCooldown {
                    if (!p.isValid) return@onCompleteCooldown
                    p.updateBelt(true, false)
                }.start()
            }
        }
    }

    val FLASH = object : Invokable {

        val transparentMaterialSet = setOf(
                Material.AIR,
                Material.CAVE_AIR,
                Material.VOID_AIR,
                Material.WATER,
                Material.LAVA,
                Defaults.SKY_WALK_AIR_MATERIAL,
                Defaults.SKY_WALK_WATER_MATERIAL,
                Defaults.SKY_WALK_LAVA_MATERIAL,
                Defaults.SKY_WALK_TORCH_MATERIAL
        )

        val maxDistance = 50

        val coolTime = Config.SKILL_FLASH_COOLTIME

        override fun findInvokable(player: Player): Consumer<Player>? {
            val flash = player.getOrPut(Keys.SKILL_FLASH)
            if (!flash.canStart()) return null
            return Consumer { p ->
                flash.coolTime = coolTime
                flash.onStart {
                    if (!p.isValid) return@onStart
                    val tpLocation = p.getTargetBlock(transparentMaterialSet, maxDistance).let { block ->
                        if (transparentMaterialSet.contains(block.type)) return@let null
                        var nextBlock = block
                        while (!nextBlock.isSurface) {
                            nextBlock = nextBlock.getRelative(BlockFace.UP)
                        }
                        nextBlock.centralLocation.add(0.0, 0.75, 0.0).apply {
                            direction = p.location.direction
                        }
                    }
                    if (tpLocation != null) {
                        SkillAnimations.FLASH_FIRE.start(p.location)
                        p.teleportSafely(tpLocation)
                        SkillAnimations.FLASH_FIRE.start(p.location)
                        SkillSounds.FLASH_FIRE.play(p.location)
                    } else {
                        SkillSounds.FLASH_MISS.play(p.location)
                        flash.isCancelled = true
                    }
                    p.updateBelt(true, false)
                }.onCooldown {
                    if (!p.isValid) return@onCooldown
                    p.updateBelt(false, false)
                }.onCompleteCooldown {
                    if (!p.isValid) return@onCompleteCooldown
                    p.updateBelt(true, false)
                }.start()
            }
        }

    }

    val HEAL = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            player.getOrPut(Keys.BREAK_BLOCK) ?: return null
            if (Config.SKILL_HEAL_PROBABILITY < Random.nextInt(100)) return null
            if (player.health >= player.healthScale) return null

            return Consumer { p ->
                val block = p.getOrPut(Keys.BREAK_BLOCK) ?: return@Consumer
                val inc = p.healthScale.div(100.0).times(Config.SKILL_HEAL_RATIO)
                val nextHealth = inc.plus(p.health).coerceAtMost(p.healthScale)
                val diff = nextHealth - p.health
                p.health = nextHealth

                SkillAnimations.HEAL.absorb(p, block.centralLocation)
                PopUp(SimpleAnimation, block.centralLocation.add(0.0, 0.2, 0.0), PopUpMessages.HEAL(diff))
                        .pop()
                SkillSounds.HEAL.play(block.centralLocation)
            }
        }

    }

    // sound 処理は[Miner]で纏めてある
    val MINE_COMBO = object : Invokable {
        override fun findInvokable(p: Player): Consumer<Player>? {
            p.getOrPut(Keys.BREAK_BLOCK) ?: return null
            return Consumer { player ->
                val block = player.getOrPut(Keys.BREAK_BLOCK) ?: return@Consumer
                val currentCombo = player.combo
                val increaseCombo = 1L
                val currentMaxCombo = player.maxCombo

                player.manipulate(CatalogPlayerCache.MINE_COMBO) {
                    it.combo(increaseCombo)
                }

                // コンボ数が減少した場合警告
                if (player.combo <= currentCombo) {
                    // 長時間放置時は除外
                    if (player.combo != increaseCombo) {
                        PlayerMessages.DECREASE_COMBO(currentCombo - player.combo + increaseCombo).sendTo(player)
                    }
                    // 最大コンボ数が更新されていれば報告
                    if (player.maxCombo > currentMaxCombo) {
                        PlayerMessages.UPDATE_MAX_COMBO(currentCombo, player.maxCombo).sendTo(player)
                    }
                }
                // コンボイベント発生
                Bukkit.getPluginManager().callEvent(ComboEvent(player.combo, player))

                if (ToggleSetting.COMBO.getToggle(player)) {
                    // 現在のコンボ数をプレイヤーに告知
                    PopUp(SimpleAnimation, block.centralLocation, PopUpMessages.MINE_COMBO(player.combo, player.comboRank))
                            .pop()
                }
            }
        }

    }

    /**
     * 参照:[click.seichi.gigantic.listenerElytraListener]
     */
    val JUMP = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            return null
        }
    }

    val FOCUS_TOTEM = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            if (Random.nextDouble() > Defaults.PIECE_PROBABILITY) return null

            val block = player.getOrPut(Keys.BREAK_BLOCK) ?: return null

            val totem = player.getOrPut(Keys.TOTEM)

            if (totem > 0) return null

            return Consumer { p ->
                val piece = p.getOrPut(Keys.TOTEM_PIECE)

                SkillAnimations.TOTEM_PIECE.start(block.centralLocation)

                if (piece + 1 >= Defaults.MAX_TOTEM_PIECE) {
                    // トーテム完成
                    p.transform(Keys.TOTEM) { it.plus(1) }
                    p.offer(Keys.TOTEM_PIECE, 0)
                    SkillSounds.TOTEM_COMPLETE.playOnly(p)
                    PlayerMessages.COMPLETE_TOTEM.sendTo(p)
                } else {
                    // 欠片を獲得
                    p.transform(Keys.TOTEM_PIECE) { it.plus(1) }
                    PlayerMessages.GET_TOTEM_PIECE(p.getOrPut(Keys.TOTEM_PIECE)).sendTo(p)
                    object : BukkitRunnable() {
                        override fun run() {
                            if (!p.isValid) return
                            PlayerSounds.PICK_UP.playOnly(p)
                        }
                    }.runTaskLater(Gigantic.PLUGIN, SkillAnimations.TOTEM_PIECE.ticks)
                }
                p.updateBelt(applyMainHand = false, applyOffHand = false)
            }
        }
    }

}