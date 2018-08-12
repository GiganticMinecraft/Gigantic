package click.seichi.gigantic.raid

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.message.messages.BattleMessages
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.sound.sounds.BattleSounds
import org.bukkit.Bukkit
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
        raidBattle.getJoinedPlayerSet().forEach {
            val player = Bukkit.getPlayer(it) ?: return@forEach
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
                it.add(bonusMineBlock, MineBlockReason.RAID_BOSS)
                BattleMessages.BONUS_EXP(bonusMineBlock).sendTo(player)
            }
            raidBattle.left(player)
        }
        battleList.remove(raidBattle)
    }

    fun playBattle(player: Player, baseDamage: Double = 1.0) {
        getBattleList()
                .firstOrNull { it.isJoined(player) }
                ?.run {
                    if (raidBoss.isDead()) return@run
                    val attackDamage = baseDamage.times(
                            when (player.find(CatalogPlayerCache.MINE_COMBO)?.currentCombo ?: 0L) {
                                0L -> 0.0
                                in 1..9 -> 1.0
                                in 10..29 -> 1.1
                                in 30..69 -> 1.2
                                in 70..149 -> 1.3
                                in 150..349 -> 1.4
                                in 350..799 -> 1.5
                                in 800..1199 -> 1.6
                                else -> 1.7
                            }
                    )
                    raidBoss.damage(player, attackDamage)
                    if (raidBoss.isDead()) {
                        RaidManager.endBattle(this)
                        RaidManager.newBattle()
                    }
                    display()
                }
    }

}