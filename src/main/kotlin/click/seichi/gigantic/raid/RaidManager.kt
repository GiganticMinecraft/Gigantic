package click.seichi.gigantic.raid

import click.seichi.gigantic.boss.Boss
import org.bukkit.Bukkit

/**
 * @author tar0ss
 */
object RaidManager {

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

    fun endBattle(raidBattle: RaidBattle) {
        raidBattle.joinedPlayerSet.forEach {
            val player = Bukkit.getPlayer(it) ?: return@forEach
            raidBattle.left(player)
        }
        battleList.remove(raidBattle)
    }

}