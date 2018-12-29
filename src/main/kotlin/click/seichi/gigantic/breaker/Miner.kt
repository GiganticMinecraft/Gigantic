package click.seichi.gigantic.breaker

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.animation.animations.SkillAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.player.skill.Skill
import click.seichi.gigantic.player.spell.Spell
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * 通常破壊時の処理
 *
 * @author tar0ss
 */
open class Miner : Breaker {

    override fun breakBlock(block: Block) {
        block.type = Material.AIR
    }


    open fun onBreakBlock(player: Player?, block: Block) {

        if (!block.isCrust && !block.isTree) {
            block.update()
            return
        }

        if (player == null) {
            block.update()
            return
        }

        player.manipulate(CatalogPlayerCache.EXP) {
            it.inc()
        }

        // 破壊対象ブロックをInvoker用に保存
        player.offer(Keys.BREAK_BLOCK, block)

        // ヒール系
        when {
            Skill.HEAL.tryCast(player) -> {
            }
            Spell.STELLA_CLAIR.tryCast(player) -> {
            }
        }
        // 破壊系
        var isCastApostol: Boolean = false
        when {
            !player.getOrPut(Keys.SPELL_TOGGLE) -> {
            }
            Spell.APOSTOL.tryCast(player) -> {
                isCastApostol = true
            }
        }

        // 最終結果コンボ数
        Skill.MINE_COMBO.tryCast(player)

        // play sounds
        val mineBurst = player.getOrPut(Keys.SKILL_MINE_BURST)
        if (Achievement.SKILL_MINE_COMBO.isGranted(player)) {
            // Sounds
            when {
                mineBurst.duringFire() -> {
                    SkillSounds.MINE_BURST_ON_BREAK(player.combo).playOnly(player)
                    SkillAnimations.MINE_BURST_ON_BREAK.start(block.centralLocation)
                }
                !isCastApostol -> PlayerSounds.OBTAIN_EXP(player.combo).playOnly(player)
                else -> {
                }
            }
        }

        player.updateLevel()

        block.update()
    }

}