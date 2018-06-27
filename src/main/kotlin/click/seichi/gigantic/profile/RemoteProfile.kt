package click.seichi.gigantic.profile

import click.seichi.gigantic.database.dao.UserDao
import kotlinx.coroutines.experimental.async
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
object RemoteProfile {

    /**
     * player profileをロード、もしくは無ければ作成します
     *
     * @param player 対象プレイヤー
     * @return プロフィール
     */
    suspend fun loadOrCreate(player: Player): Profile {

        var profile: Profile? = null

        async {
            transaction {
                val isFirstJoin = UserDao.findById(player.uniqueId) == null
                profile = if (isFirstJoin) create(player) else load(player)
            }
        }.await()

        return profile!!
    }

    /**
     * player profile をデータベースに書き込みます
     *
     * @param profile プロフィール
     */
    suspend fun save(profile: Profile) {
        async {
            transaction {
                UserDao[profile.uniqueId].apply {
                    // localeを更新
                    locale = profile.locale.toString()
                    // 更新日時を記録
                    updatedDate = DateTime.now()
                }
            }
        }.await()
    }

    /**
     * player profileをデータベースから読み込みます。
     *
     * @param player 対象プレイヤー
     * @return プロフィール
     */
    private fun load(player: Player): Profile {
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
     * @param player 対象プレイヤー
     * @return プロフィール
     */
    private fun create(player: Player): Profile {
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