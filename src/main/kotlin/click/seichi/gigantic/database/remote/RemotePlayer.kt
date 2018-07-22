package click.seichi.gigantic.database.remote

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.database.PlayerDao
import click.seichi.gigantic.database.dao.*
import click.seichi.gigantic.database.table.UserBossTable
import click.seichi.gigantic.database.table.UserMineBlockTable
import click.seichi.gigantic.database.table.UserRelicTable
import click.seichi.gigantic.database.table.UserWillTable
import click.seichi.gigantic.player.CraftPlayer
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
class RemotePlayer(player: Player) {

    companion object {
        private val DB
            get() = Gigantic.DB
    }

    private val uniqueId = player.uniqueId

    private val playerName = player.name

    /**
     * 非同期でplayerをロード、もしくは無ければ作成します
     *
     * @return CraftPlayer
     */
    fun loadOrCreateAsync() = async(DB) {
        delay(3, TimeUnit.SECONDS)
        transaction {
            val isFirstJoin = createIfNotExists()
            load(isFirstJoin)
        }
    }

    /**
     * playerをデータベースから読み込みます。
     *
     * @return CraftPlayer
     */
    private fun load(isFirstJoin: Boolean): CraftPlayer {
        User[uniqueId].apply {
            name = playerName
        }
        val craftPlayer = CraftPlayer(isFirstJoin)
        craftPlayer.load(PlayerDao(uniqueId))
        return craftPlayer
    }


    /**
     * playerを作成し、データベースに追加します。
     */
    private fun createIfNotExists(): Boolean {
        val isExist = User.findById(uniqueId) != null
        if (!isExist) {
            User.new(uniqueId) {
                name = playerName
            }
        }

        val newUser = User[uniqueId]

        Will.values().forEach { will ->
            will to UserWill
                    .find { (UserWillTable.userId eq uniqueId) and (UserWillTable.willId eq will.id) }
                    .firstOrNull().let {
                        it ?: UserWill.new {
                            user = newUser
                            willId = will.id
                        }
                    }
        }

        MineBlockReason.values().forEach { reason ->
            reason to UserMineBlock
                    .find { (UserMineBlockTable.userId eq uniqueId) and (UserMineBlockTable.reasonId eq reason.id) }
                    .firstOrNull().let {
                        it ?: UserMineBlock.new {
                            user = newUser
                            reasonId = reason.id
                        }
                    }
        }

        Boss.values().forEach { boss ->
            boss to UserBoss
                    .find { (UserBossTable.userId eq uniqueId) and (UserBossTable.bossId eq boss.id) }
                    .firstOrNull().let {
                        it ?: UserBoss.new {
                            user = newUser
                            bossId = boss.id
                        }
                    }
        }

        Relic.values().forEach { relic ->
            relic to UserRelic
                    .find { (UserRelicTable.userId eq uniqueId) and (UserRelicTable.relicId eq relic.id) }
                    .firstOrNull().let {
                        it ?: UserRelic.new {
                            user = newUser
                            relicId = relic.id
                        }
                    }
        }

        return !isExist
    }


    /**
     * gPlayer をデータベースに書き込みます
     *
     * @param craftPlayer
     */
    fun saveAsync(craftPlayer: CraftPlayer) = async(DB) {
        transaction {
            User[uniqueId].apply {
                updatedDate = DateTime.now()
            }
            craftPlayer.save(PlayerDao(uniqueId))
        }
    }
}