package click.seichi.gigantic.database.remote

import click.seichi.gigantic.database.dao.User
import click.seichi.gigantic.database.dao.UserMineBlock
import click.seichi.gigantic.database.dao.UserWill
import click.seichi.gigantic.database.table.UserMineBlockTable
import click.seichi.gigantic.database.table.UserWillTable
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
class RemotePlayer(player: Player) : Remotable {

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
            if (isExist()) load() else create()
        }
    }


    /**
     * player がデータベースに存在するか調べます
     *
     * @return 存在すればTRUE,しなければFALSE
     */
    private fun isExist(): Boolean {
        return User.findById(uniqueId) != null
    }

    /**
     * playerをデータベースから読み込みます。
     *
     * @return GiganticPlayer
     */
    private fun load() = GiganticPlayer(
            User[uniqueId].apply {
                name = playerName
            },
            UserWill.find { UserWillTable.userId eq uniqueId }
                    .map { Will.findWillById(it.willId)!! to it }
                    .toMap(),
            UserMineBlock.find{ UserMineBlockTable.userId eq uniqueId}
                    .map{ MineBlockReason.findById(it.reasonId)!! to it}
                    .toMap()
    )

    /**
     * playerを作成し、データベースに追加します。
     *
     * @return GiganticPlayer
     */
    private fun create(): GiganticPlayer {
        // TODO
        val yourWill = Random.nextWill()
        val newUser = User.new(uniqueId) {
            name = playerName
        }
        return GiganticPlayer(
                newUser,
                Will.values().map {
                            it to
                                    UserWill.new {
                                        user = newUser
                                        willId = it.id
                                        if (yourWill == it) {
                                            hasAptitude = true
                                        }
                                    }
                        }.toMap(),
                MineBlockReason.values().map{
                    it to UserMineBlock.new{
                        user = newUser
                        reasonId = it.id
                    }
                }.toMap(),
                isFirstJoin = true
        )
    }


    /**
     * player をデータベースに書き込みます
     *
     * @param gPlayer
     */
    fun saveAsync(gPlayer: GiganticPlayer) = async(DB) {
        transaction {
            gPlayer.save(
                    User[uniqueId].apply {
                        updatedDate = DateTime.now()
                    },
                    UserWill.find { UserWillTable.userId eq uniqueId }
                            .map { Will.findWillById(it.willId)!! to it }
                            .toMap(),
                    UserMineBlock.find{ UserMineBlockTable.userId eq uniqueId}
                            .map{ MineBlockReason.findById(it.reasonId)!! to it}
                            .toMap()
            )
        }
    }
}