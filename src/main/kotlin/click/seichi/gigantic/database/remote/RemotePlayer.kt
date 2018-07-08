package click.seichi.gigantic.database.remote

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.database.dao.User
import click.seichi.gigantic.database.dao.UserMineBlock
import click.seichi.gigantic.database.dao.UserWill
import click.seichi.gigantic.database.table.UserMineBlockTable
import click.seichi.gigantic.database.table.UserWillTable
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.player.MineBlockReason
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
     * @return GiganticPlayer
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
     * @return GiganticPlayer
     */
    private fun load(isFirstJoin: Boolean): GiganticPlayer {
        User[uniqueId].apply {
            name = playerName
        }
        val gPlayer = GiganticPlayer(isFirstJoin)
        gPlayer.load(UserContainer(uniqueId))
        gPlayer.init(gPlayer)
        return gPlayer
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
        return !isExist
    }


    /**
     * player をデータベースに書き込みます
     *
     * @param gPlayer
     */
    fun saveAsync(gPlayer: GiganticPlayer) = async(DB) {
        transaction {
            gPlayer.finish(gPlayer)
            User[uniqueId].apply {
                updatedDate = DateTime.now()
            }
            gPlayer.save(UserContainer(uniqueId))
        }
    }
}