package click.seichi.gigantic.raid

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.message.messages.BattleMessages
import click.seichi.gigantic.message.messages.DeathMessages
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.schedule.Scheduler
import click.seichi.gigantic.sound.sounds.BattleSounds
import click.seichi.gigantic.topbar.bars.BossBars
import io.reactivex.Observable
import org.bukkit.Bukkit
import org.bukkit.EntityEffect
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
class RaidBattle(val boss: Boss) {

    val uniqueId = UUID.randomUUID()

    val raidBoss = RaidBoss(boss)

    private val bossBar = Gigantic.createInvisibleBossBar()

    private val joinedPlayerSet: MutableSet<UUID> = mutableSetOf()

    private val droppedPlayerSet: MutableSet<UUID> = mutableSetOf()

    fun join(player: Player) {
        joinedPlayerSet.add(player.uniqueId)
        bossBar.addPlayer(player)
        display()
        BattleSounds.START.playOnly(player)
        BattleMessages.BATTLE_INFO(this, player.find(CatalogPlayerCache.HEALTH)
                ?: return, boss.attackInterval, true).sendTo(player)
        val uniqueId = player.uniqueId
        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .takeWhile {
                    val p = Bukkit.getPlayer(uniqueId) ?: return@takeWhile false
                    isJoined(p)
                }.subscribe { elapsedSeconds ->
                    val p = Bukkit.getPlayer(uniqueId) ?: return@subscribe
                    val remainTimeToAttack = boss.attackInterval - ((elapsedSeconds + 1) % boss.attackInterval)
                    BattleMessages.BATTLE_INFO(this, p.find(CatalogPlayerCache.HEALTH)
                            ?: return@subscribe, remainTimeToAttack, false).sendTo(p)
                    if ((elapsedSeconds + 1) % boss.attackInterval != 0L) return@subscribe
                    // attack
                    p.manipulate(CatalogPlayerCache.HEALTH) {
                        it.decrease(boss.attackDamage)
                        if (it.current == 0L) {
                            drop(p)
                        }
                    }
                    p.offer(Keys.DEATH_MESSAGE, DeathMessages.PLAYER_DEATH_RAID_BATTLE(p, boss))
                    PlayerMessages.HEALTH_DISPLAY(p.find(CatalogPlayerCache.HEALTH) ?: return@subscribe
                    ).sendTo(p)
                    p.playEffect(EntityEffect.HURT)
                }
    }

    fun left(player: Player) {
        raidBoss.resetDamage(player)
        joinedPlayerSet.remove(player.uniqueId)
        bossBar.removePlayer(player)
        if (joinedPlayerSet.isEmpty()) {
            raidBoss.fullHealth()
        }
        display()

        player.offer(Keys.DEATH_MESSAGE, null)

        PlayerMessages.MEMORY_SIDEBAR(
                player.find(CatalogPlayerCache.MEMORY) ?: return,
                player.find(CatalogPlayerCache.APTITUDE) ?: return,
                true
        ).sendTo(player)
    }

    fun drop(player: Player) {
        droppedPlayerSet.add(player.uniqueId)
        left(player)
    }

    fun getJoinedPlayerSet() = joinedPlayerSet.toSet()
    fun getDroppedPlayerSet() = droppedPlayerSet.toSet()
    fun isJoined(player: Player) = joinedPlayerSet.contains(player.uniqueId)
    fun isDropped(player: Player) = droppedPlayerSet.contains(player.uniqueId)

    fun display() {
        BossBars.RAID_BOSS(this, boss.localizedName.asSafety(Gigantic.DEFAULT_LOCALE)).show(bossBar)
    }

    override fun equals(other: Any?): Boolean {
        val battle = other as? RaidBattle ?: return false
        return battle.uniqueId == uniqueId
    }

    override fun hashCode(): Int {
        return uniqueId.hashCode()
    }

}