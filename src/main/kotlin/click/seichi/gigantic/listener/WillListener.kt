package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.animation.animations.WillSpiritAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import kotlin.random.asKotlinRandom

/**
 * @author tar0ss
 */
class WillListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        if (player.gameMode != GameMode.SURVIVAL) return
        val block = event.block
        if (!block.isCrust && !block.isTree) return

        val preSenseWillSet = Will.values().filter {
            // カウント条件を満たしている
            when (it.grade) {
                WillGrade.BASIC -> Achievement.WILL_BASIC.isGranted(player)
                WillGrade.ADVANCED -> Achievement.WILL_ADVANCED.isGranted(player)
                WillGrade.SPECIAL -> Achievement.WILL_SPECIAL.isGranted(player)
            }
        }.filter {
            // 適性を持たない
            !player.hasAptitude(it)
        }.filter {
            // スポーン条件を満たしている
            it.canSpawn(player, block)
        }.toSet()

        if (preSenseWillSet.isEmpty()) return

        val preSenseWill = preSenseWillSet.random(Random.generator.asKotlinRandom())

        player.transform(Keys.WILL_SECRET_MAP.getValue(preSenseWill)) {
            if (Gigantic.IS_DEBUG)
                it.plus(10)
            else
                it.inc()
        }

        val secretAmount = player.getOrPut(Keys.WILL_SECRET_MAP.getValue(preSenseWill))

        val interval = preSenseWill.grade.unlockAmount.div(100)

        if (secretAmount == 0L || secretAmount % interval != 0L) return

        if (secretAmount < preSenseWill.grade.unlockAmount)
            WillSpiritAnimations.PRE_SENSE(preSenseWill.color).rise(block.centralLocation, 4.5)
        else
            WillSpiritAnimations.PRE_SENSE_COMPLETE(preSenseWill.color).rise(block.centralLocation, 4.5)

        val ratio = secretAmount.div(interval)
        PlayerMessages.FRIEND_RATIO(preSenseWill, ratio.toInt()).sendTo(player)
        // プレイヤーの達成度を変更
        player.updateBag()
        // 実績解除
        Achievement.update(player)

    }

}