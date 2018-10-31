package click.seichi.gigantic.raid

import click.seichi.gigantic.bag.bags.MainBag
import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.cache.manipulator.MineBlockReason
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.menu.menus.RaidBattleMenu
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

    private val battleMap: MutableMap<Int, RaidBattle> = mutableMapOf()


    // run only onEnabled this plugin
    fun newBattles() {
        (1..Boss.MAX_RANK).forEach { rank ->
            newBattle(rank)?.let { battleMap[rank] = it }
        }
    }

    private fun newBattle(rank: Int): RaidBattle? {
        val bossSet = Boss.values().filter { it.rank == rank }.toSet()
        val newBoss = bossSet.shuffled().firstOrNull() ?: return null
        val raidBattle = RaidBattle(newBoss)
        battleMap[rank] = raidBattle
        return raidBattle
    }

    fun getBattleList() = battleMap.values.toList()

    fun getBattle(rank: Int) = battleMap[rank]

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
        battleMap.remove(raidBattle.boss.rank)
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
                        RaidManager.newBattle(boss.rank)
                    }
                    display()
                    updateAllPlayersMenu()
                }
    }

    private fun updateAllPlayersMenu() {
        Bukkit.getOnlinePlayers().filter {
            it.openInventory.topInventory.holder is RaidBattleMenu
        }.forEach {
            RaidBattleMenu.reopen(it)
            MainBag.carry(it)
        }
    }

}