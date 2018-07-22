package click.seichi.gigantic.raid

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.topbar.bars.BossBars
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
class RaidBattle(val boss: Boss) {

    val uniqueId = UUID.randomUUID()

    val raidBoss = RaidBoss(boss)

    private val bossBar = Gigantic.createInvisibleBossBar()

    val joinedPlayerSet: MutableSet<UUID> = mutableSetOf()

    val droppedPlayerSet: MutableSet<UUID> = mutableSetOf()

    fun join(player: Player) {
        joinedPlayerSet.add(player.uniqueId)
        bossBar.addPlayer(player)
        BossBars.RAID_BOSS(this, boss.localizedName.asSafety(Gigantic.DEFAULT_LOCALE)).show(bossBar)
    }

    fun left(player: Player) {
        joinedPlayerSet.remove(player.uniqueId)
        bossBar.removePlayer(player)
    }

    fun update() = BossBars.RAID_BOSS(this, boss.localizedName.asSafety(Gigantic.DEFAULT_LOCALE)).show(bossBar)

    override fun equals(other: Any?): Boolean {
        val battle = other as? RaidBattle ?: return false
        return battle.uniqueId == uniqueId
    }

}