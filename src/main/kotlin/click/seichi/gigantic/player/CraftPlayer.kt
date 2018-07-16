package click.seichi.gigantic.player

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.database.PlayerDao
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.language.messages.PlayerMessages
import click.seichi.gigantic.player.belt.Belt
import click.seichi.gigantic.player.belt.belts.FightBelt
import click.seichi.gigantic.player.belt.belts.MineBelt
import click.seichi.gigantic.player.components.*
import click.seichi.gigantic.player.defalutInventory.inventories.MainInventory
import click.seichi.gigantic.schedule.Scheduler
import click.seichi.gigantic.sound.PlayerSounds
import io.reactivex.Observable
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
class CraftPlayer(val isFirstJoin: Boolean = false) : GiganticPlayer, RemotablePlayer {

    override val manaBar: BossBar by lazy {
        Bukkit.createBossBar("title", BarColor.YELLOW, BarStyle.SOLID).apply {
            isVisible = false
            addPlayer(player)
        }
    }

    override val player: Player
        get() = Bukkit.getServer().getPlayer(uniqueId)

    override lateinit var uniqueId: UUID
        private set

    override lateinit var locale: Locale

    override lateinit var mana: Mana

    override lateinit var mineBlock: MineBlock

    override lateinit var memory: Memory

    override lateinit var aptitude: WillAptitude

    override val level = Level()

    override val abilityPoint = AbilityPoint()
    // TODO implements
    override val explosionLevel: Int = 3

    override val defaultInventory = MainInventory

    override var belt: Belt = MineBelt

    override val mineCombo = MineCombo()

    override fun switchBelt() {
        when (belt) {
            is MineBelt -> belt = FightBelt
            is FightBelt -> belt = MineBelt
            else -> {
            }
        }
        belt.apply(player)
    }

    override fun updateLevel() {
        val player = player
        val level = player.gPlayer?.level ?: return
        val expBefore = level.exp
        val expAfter = ExpProducer.calcExp(player)
        val diff = expAfter - expBefore
        val isLevelUp = level.updateLevel(expAfter) {
            Bukkit.getPluginManager().callEvent(LevelUpEvent(it, player))
        }
        PlayerMessages.LEVEL_DISPLAY(level).sendTo(player)
        if (isLevelUp) {
            PlayerSounds.LEVEL_UP.play(player.location)
        } else if (diff > 0) {
            val count = if (diff > 20) 20 else diff
            Observable.interval(2, TimeUnit.MILLISECONDS)
                    .take(count)
                    .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                    .subscribe { PlayerSounds.OBTAIN_EXP.play(player) }
        }
    }


    override fun load(playerDao: PlayerDao) {
        playerDao.user.run {
            uniqueId = id.value
            locale = Locale(localeString)
            this@CraftPlayer.mana = Mana(mana)
        }
        playerDao.userMineBlockMap.run {
            mineBlock = MineBlock(
                    map { it.key to it.value.mineBlock }
                            .toMap()
                            .toMutableMap()
            )
        }
        playerDao.userWillMap.run {
            memory = Memory(
                    map { it.key to it.value.memory }
                            .toMap().toMutableMap()
            )
            aptitude = WillAptitude(
                    filter { it.value.hasAptitude }
                            .map { it.key }
                            .toSet().toMutableSet()
            )
        }
    }

    override fun init() {
        val player = player
        if (isFirstJoin) {
            PlayerMessages.FIRST_JOIN.sendTo(player)
        }
        // レベル更新
        level.updateLevel(ExpProducer.calcExp(player)) {}
        // 表示を更新
        PlayerMessages.LEVEL_DISPLAY(level).sendTo(player)
        if (LockedFunction.MANA.isUnlocked(level)) {
            mana.updateMaxMana(level)
            PlayerMessages.MANA_DISPLAY(manaBar, mana).sendTo(player)
        }
        PlayerMessages.MEMORY_SIDEBAR(memory, aptitude).sendTo(player)
        // インベントリーを設定
        defaultInventory.apply(player)
        // ベルトを設定
        belt.apply(player)
    }

    override fun finish() {
        manaBar.removeAll()
    }

    override fun save(playerDao: PlayerDao) {
        playerDao.user.run {
            // ja_jpとなったときにjaを保存する
            localeString = locale.language.substringBefore("_")
            mana = this@CraftPlayer.mana.current
        }
        mineBlock.copyMap().forEach { reason, current ->
            playerDao.userMineBlockMap[reason]?.mineBlock = current
        }

        memory.copyMap().forEach { will, current ->
            playerDao.userWillMap[will]?.memory = current
        }

        aptitude.copySet().forEach { will ->
            playerDao.userWillMap[will]?.hasAptitude = true
        }

    }


}