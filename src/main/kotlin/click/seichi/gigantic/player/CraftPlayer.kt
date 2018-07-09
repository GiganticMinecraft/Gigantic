package click.seichi.gigantic.player

import click.seichi.gigantic.database.PlayerDao
import click.seichi.gigantic.player.components.*
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.WillGrade
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
class CraftPlayer(val isFirstJoin: Boolean = false) : GiganticPlayer, Remotable {
    override val player: Player
        get() = Bukkit.getServer().getPlayer(uniqueId)

    override lateinit var uniqueId: UUID
        private set

    override lateinit var locale: Locale
        private set

    override lateinit var mana: Mana

    override lateinit var mineBlock: MineBlock

    override lateinit var memory: Memory

    override lateinit var aptitude: WillAptitude

    override val level = PlayerLevel()

    override val abilityPoint = AbilityPoint()
    // TODO implements
    override val explosionLevel: Int = 3


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
        if (isFirstJoin) {
            aptitude.add(Random.nextWill(WillGrade.BASIC))
        }
        level.update(this)
        mana.update(this)
    }

    override fun finish() {
    }

    override fun save(playerDao: PlayerDao) {
        playerDao.user.run {
            localeString = locale.toString()
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