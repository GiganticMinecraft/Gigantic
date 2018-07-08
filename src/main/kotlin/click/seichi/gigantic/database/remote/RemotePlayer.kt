package click.seichi.gigantic.database.remote

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.database.dao.User
import click.seichi.gigantic.database.dao.UserMineBlock
import click.seichi.gigantic.database.dao.UserWill
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.player.MineBlockReason
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
            val isFirstJoin = !isExist()
            if (isFirstJoin) {
                create()
            }
            load(isFirstJoin)
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
    private fun create() {
        val newUser = User.new(uniqueId) {
            name = playerName
        }
        Will.values().map {
            it to UserWill.new {
                user = newUser
                willId = it.id
            }
        }.toMap()
        MineBlockReason.values().map {
            it to UserMineBlock.new {
                user = newUser
                reasonId = it.id
            }
        }.toMap()
    }


    /**
     * player をデータベースに書き込みます
     *
     * @param gPlayer
     */
    fun saveAsync(gPlayer: GiganticPlayer) = async(DB) {
        transaction {
            gPlayer.onFinish(gPlayer)
            User[uniqueId].apply {
                updatedDate = DateTime.now()
            }
            gPlayer.save(UserContainer(uniqueId))
        }
    }
}