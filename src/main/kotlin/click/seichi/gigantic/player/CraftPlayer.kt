package click.seichi.gigantic.player

import click.seichi.gigantic.database.PlayerDao
import click.seichi.gigantic.language.messages.PlayerMessages
import click.seichi.gigantic.player.belt.Belt
import click.seichi.gigantic.player.belt.belts.FightBelt
import click.seichi.gigantic.player.belt.belts.MineBelt
import click.seichi.gigantic.player.components.*
import click.seichi.gigantic.player.defalutInventory.inventories.MainInventory
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
class CraftPlayer(val isFirstJoin: Boolean = false) : GiganticPlayer, RemotablePlayer {

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

    override fun switchBelt() {
        when (belt) {
            is MineBelt -> belt = FightBelt
            is FightBelt -> belt = MineBelt
            else -> {
            }
        }
        belt.apply(player)
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
        // TODO remove
//        if (isFirstJoin) {
        PlayerMessages.FIRST_JOIN.sendTo(player)
//        }
        level.init(player)
        mana.init(player)
        memory.display(player)
        // インベントリーを設定
        defaultInventory.apply(player)
        // ベルトを設定
        belt.apply(player)
    }

    override fun finish() {
        mana.finish(player)
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