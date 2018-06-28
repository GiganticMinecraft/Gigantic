package click.seichi.gigantic.database.remote

import click.seichi.gigantic.database.dao.UserDao
import click.seichi.gigantic.profile.Profile
import kotlinx.coroutines.experimental.async
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
class RemoteProfile(val player: Player) : Remotable {

    /**
     * 非同期でplayer profileをロード、もしくは無ければ作成します
     *
     * @return プロフィール
     */
    fun loadOrCreateAsync() = async(DB) {
        transaction {
            if (isExist()) load() else create()
        }
    }

    /**
     * player profile をデータベースに書き込みます
     *
     * @param profile プロフィール
     */
    fun saveAsync(profile: Profile) = async {
        transaction {
            UserDao[profile.uniqueId].apply {
                // localeを更新
                locale = profile.locale.toString()
                // 更新日時を記録
                updatedDate = DateTime.now()
            }
        }
    }


    /**
     * player profile がデータベースに存在するか調べます
     *
     * @return 存在すればTRUE,しなければFALSE
     */
    private fun isExist(): Boolean {
        return UserDao.findById(player.uniqueId) != null
    }

    /**
     * player profileをデータベースから読み込みます。
     *
     * @return プロフィール
     */
    private fun load(): Profile {
        UserDao[player.uniqueId].run {
            // playerNameを更新(更新日時は記録しない)
            name = player.name
            return Profile(
                    player.uniqueId,
                    name,
                    Locale(locale),
                    updatedDate
            )
        }
    }

    /**
     * player profileを作成し、データベースに追加します。
     *
     * @return プロフィール
     */
    private fun create(): Profile {
        UserDao.new(player.uniqueId) {
            name = player.name
        }.run {
            return Profile(
                    player.uniqueId,
                    name,
                    Locale(locale),
                    updatedDate
            )
        }
    }
}