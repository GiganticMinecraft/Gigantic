package click.seichi.gigantic.database.remote

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.database.dao.UserDao
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.player.SkillPreferences
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
class RemotePlayer(val player: Player) : Remotable {

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
        return UserDao.findById(player.uniqueId) != null
    }

    /**
     * playerをデータベースから読み込みます。
     *
     * @return GiganticPlayer
     */
    private fun load() = UserDao[player.uniqueId].apply {
        name = player.name
    }.newPlayer()

    /**
     * playerを作成し、データベースに追加します。
     *
     * @return GiganticPlayer
     */
    private fun create() = UserDao.new(player.uniqueId) {
        name = player.name
    }.newPlayer()


    /**
     * player をデータベースに書き込みます
     *
     * @param gPlayer
     */
    fun saveAsync(gPlayer: GiganticPlayer) = async {
        transaction {
            UserDao[gPlayer.uniqueId].savePlayer(gPlayer)
        }
    }

    /**
     * daoからplayerを生成します
     *
     * @return GiganticPlayer
     */
    private fun UserDao.newPlayer() = GiganticPlayer(
            uniqueId = player.uniqueId,
            playerName = name,
            locale = Locale(locale),
            lastSaveDate = updatedDate,
            skillPreferences = SkillPreferences(1),
            mineBlock = mineBlock,
            mana = mana,
            seichiLevel = Config.seichiLevel.calcLevel(player)
    )

    /**
     * playerをセーブします
     *
     * @param gPlayer
     */
    private fun UserDao.savePlayer(gPlayer: GiganticPlayer) {
        // localeを更新
        locale = gPlayer.locale.toString()
        // 更新日時を記録
        updatedDate = DateTime.now()
        // MineBlockを更新
        mineBlock = gPlayer.mineBlock
        // 現在のマナ値を更新
        mana = gPlayer.mana
        // TODO skillpreference
    }
}