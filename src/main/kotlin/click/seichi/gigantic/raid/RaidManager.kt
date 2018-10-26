package click.seichi.gigantic.raid

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.cache.manipulator.MineBlockReason
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.message.messages.BattleMessages
import click.seichi.gigantic.popup.PopUpParameters
import click.seichi.gigantic.popup.RaidBattlePops
import click.seichi.gigantic.sound.sounds.BattleSounds
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object RaidManager {

    init {
        Bukkit.getScheduler()
    }

    const val maxBattle = 9

    private val battleList = mutableListOf<RaidBattle>()

    fun newBattle(): RaidBattle? {
        if (battleList.size >= maxBattle) return null

        val selectedBossSet = battleList.map { it.boss }.toSet()
        val newBoss = Boss.values()
                .filterNot { selectedBossSet.contains(it) }
                .shuffled()
                .firstOrNull() ?: return null
        val newBattle = RaidBattle(newBoss)
        battleList.add(newBattle)
        return newBattle
    }

    fun getBattleList() = battleList.toList()

    private fun endBattle(raidBattle: RaidBattle) {
        raidBattle.getJoinedPlayerSet().forEach { uuid ->
            val player = Bukkit.getPlayer(uuid) ?: return@forEach
            BattleSounds.WIN.playOnly(player)
            val raidBoss = raidBattle.raidBoss
            val boss = raidBattle.boss
            player.manipulate(CatalogPlayerCache.RAID_DATA) {
                if (raidBoss.isDefeated(player)) {
                    it.defeat(boss)
                    BattleMessages.DEFEAT_BOSS(boss).sendTo(player)
                }
                raidBoss.getDrop(player)?.run {
                    it.addRelic(this)
                    BattleMessages.GET_RELIC(this).sendTo(player)
                }
            }
            player.manipulate(CatalogPlayerCache.MINE_BLOCK) {
                // 20 percent
                val bonusMineBlock = (if (raidBoss.isDefeated(player))
                    raidBoss.getTotalDamage(player).div(10.0).times(2.0)
                else
                    0.0).toLong()
                if (bonusMineBlock > 0) {
                    it.add(bonusMineBlock, MineBlockReason.RAID_BOSS)
                    BattleMessages.BONUS_EXP(bonusMineBlock).sendTo(player)
                }
            }
            raidBattle.left(player)
        }
        battleList.remove(raidBattle)
    }

    fun playBattle(player: Player, location: Location, baseDamage: Long = 1) {
        getBattleList()
                .firstOrNull { it.isJoined(player) }
                ?.run {
                    if (raidBoss.isDead()) return@run
                    val attackDamage = baseDamage.times(
                            when (player.find(CatalogPlayerCache.MINE_COMBO)?.currentCombo ?: 0L) {
                                0L -> 0L
                                in 1..9 -> 1L
                                in 10..29 -> 2L
                                in 30..69 -> 3L
                                in 70..149 -> 4L
                                in 150..349 -> 5L
                                in 350..799 -> 6L
                                in 800..1199 -> 7L
                                else -> 8L
                            }
                    )
                    raidBoss.damage(player, attackDamage)
                    RaidBattlePops.BATTLE_DAMAGE(attackDamage).pop(
                            location.clone().add(0.0, PopUpParameters.RAID_BATTLE_DAMAGE_DIFF, 0.0)
                    )
                    if (raidBoss.isDead()) {
                        RaidManager.endBattle(this)
                        RaidManager.newBattle()
                    }
                    display()
                }
    }

}