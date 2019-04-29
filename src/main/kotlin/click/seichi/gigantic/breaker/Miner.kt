package click.seichi.gigantic.breaker

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.animation.animations.SkillAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.player.ExpReason
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.player.skill.Skill
import click.seichi.gigantic.player.spell.Spell
import click.seichi.gigantic.relic.Relic
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
        if (player == null) {
            block.update()
            return
        }

        if (!block.isCrust && !block.isTree) {
            block.update()
            block.setTorchIfNeeded(player)
            return
        }

        // 露天掘り
        var stripBonus = 0L
        if (block.y == 1 && block.calcGravity() == 0) {
            stripBonus += Defaults.STRIP_BONUS
        }

        player.offer(Keys.STRIP_COUNT, if (stripBonus > 0) 1L else 0L)

        // レリック
        var relicBonus = Relic.calcMultiplier(player, block)
        player.offer(Keys.RELIC_BONUS, relicBonus)


        // 経験値追加
        player.manipulate(CatalogPlayerCache.EXP) {
            it.inc()
            it.add(relicBonus.toBigDecimal(), reason = ExpReason.RELIC_BONUS)
            it.add(stripBonus.toBigDecimal(), reason = ExpReason.STRIP_MINE_BONUS)
        }

        // 破壊対象ブロックをInvoker用に保存
        player.offer(Keys.BREAK_BLOCK, block)
        // 現時点での破壊数を保存
        player.offer(Keys.BREAK_COUNT, 1)

        // ヒール系
        when {
            Skill.HEAL.tryCast(player) -> {
            }
            Spell.STELLA_CLAIR.tryCast(player) -> {
            }
            Skill.FOCUS_TOTEM.tryCast(player) -> {
            }
        }
        // 破壊系
        var isCastMultiBreak = false
        when {
            !player.getOrPut(Keys.SPELL_TOGGLE) -> {
            }
            Spell.MULTI_BREAK.tryCast(player) -> {
                isCastMultiBreak = true
            }
        }

        // 最終結果コンボ数
        Skill.MINE_COMBO.tryCast(player)

        // play sounds
        val mineBurst = player.getOrPut(Keys.SKILL_MINE_BURST)
        val effect = player.getOrPut(Keys.EFFECT)
        if (Achievement.SKILL_MINE_COMBO.isGranted(player)) {
            // Sounds
            when {
                mineBurst.duringFire() -> {
                    SkillSounds.MINE_BURST_ON_BREAK(player.combo).playOnly(player)
                    SkillAnimations.MINE_BURST_ON_BREAK.start(block.centralLocation)
                }
                // TODO 音があるかないかで判断すること
                isCastMultiBreak && effect != GiganticEffect.DEFAULT -> {
                }
                else -> PlayerSounds.OBTAIN_EXP(player.combo).playOnly(player)
            }
        }


        // 全てのスキルを通して破壊したブロック数を取得
        val count = player.getOrPut(Keys.BREAK_COUNT)

        // 全てのスキルを通して得たボーナス経験値を取得
        relicBonus = player.getOrPut(Keys.RELIC_BONUS)

        // 全てのスキルを通して計算された露天掘りブロック数を取得
        val stripCount = player.getOrPut(Keys.STRIP_COUNT)

        // actionbar
        if (ToggleSetting.GAIN_EXP.getToggle(player)) {
            if (relicBonus > 0.0)
                PlayerMessages.EXP_AND_BONUS(count, relicBonus).sendTo(player)
            else if (count > 1)
                PlayerMessages.EXP(count).sendTo(player)
        }

        // message
        if (stripCount > 0) {
            PlayerMessages.STRIP_EXP(stripCount).sendTo(player)
        }

        player.transform(Keys.STRIP_MINE) { it + stripCount }

        player.updateLevel()

        block.update()
        // 松明をセット
        block.setTorchIfNeeded(player)

    }

}