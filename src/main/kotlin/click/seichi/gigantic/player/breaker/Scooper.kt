package click.seichi.gigantic.player.breaker

import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.popup.PopUpParameters
import click.seichi.gigantic.popup.SkillPops
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * 通常の掬う処理
 * @author tar0ss
 */
class Scooper : Breaker {

    override fun breakBlock(player: Player, block: Block, isBroken: Boolean, showBrokenEffect: Boolean) {
        onBreakBlock(player, block)
        if (showBrokenEffect) {
            when (block.type) {
                Material.WATER -> PlayerSounds.SCOOP_WATER
                Material.LAVA -> PlayerSounds.SCOOP_LAVA
                else -> error("${block.type} has to be a water or lava")
            }.playOnly(player)
        }
        if (isBroken) return
        block.type = Material.AIR
    }

    private fun onBreakBlock(player: Player, block: Block) {
        // carry player cache
        player.manipulate(CatalogPlayerCache.MINE_BLOCK) {
            it.add(1L)
        }
        player.manipulate(CatalogPlayerCache.MINE_COMBO) {
            it.combo(1L)
            SkillPops.MINE_COMBO(it).pop(block.centralLocation.add(0.0, PopUpParameters.MINE_COMBO_DIFF, 0.0))
        }

        // raid battle process
        RaidManager.playBattle(player, block.centralLocation.clone())

        player.manipulate(CatalogPlayerCache.LEVEL) {
            it.calculate(ExpProducer.calcExp(player)) { current ->
                Bukkit.getPluginManager().callEvent(LevelUpEvent(current, player))
            }
            PlayerMessages.EXP_BAR_DISPLAY(it).sendTo(player)
        }

        val currentCombo = player.find(CatalogPlayerCache.MINE_COMBO)?.currentCombo ?: 0

        // Sounds
        PlayerSounds.OBTAIN_EXP(currentCombo).playOnly(player)

    }

}