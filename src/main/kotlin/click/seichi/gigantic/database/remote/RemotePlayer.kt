package click.seichi.gigantic.database.remote

import click.seichi.gigantic.database.dao.UserDao
import click.seichi.gigantic.player.GiganticPlayer
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
        return UserDao.findById(uniqueId) != null
    }

    /**
     * playerをデータベースから読み込みます。
     *
     * @return GiganticPlayer
     */
    private fun load() = GiganticPlayer(
            UserDao[uniqueId].apply {
                name = playerName
            }
    )

    /**
     * playerを作成し、データベースに追加します。
     *
     * @return GiganticPlayer
     */
    private fun create() = GiganticPlayer(
            UserDao.new(uniqueId) {
                name = playerName
            }
    )


    /**
     * player をデータベースに書き込みます
     *
     * @param gPlayer
     */
    fun saveAsync(gPlayer: GiganticPlayer) = async {
        transaction {
            gPlayer.save(
                    UserDao[uniqueId].apply {
                        updatedDate = DateTime.now()
                    }
            )
        }
    }
}