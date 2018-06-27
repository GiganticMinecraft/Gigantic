package click.seichi.gigantic.database

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
object RemoteProfile {

    suspend fun loadOrCreate(player: Player): Profile {

        val profile = Profile(player.uniqueId, player.name)

        async {
            transaction {
                val isFirstJoin = UserDao.findById(player.uniqueId) == null
                if (isFirstJoin) {
                    create(player)
                } else {
                    load(player, profile)
                }
            }
        }.await()

        return profile
    }

    suspend fun save(profile: Profile) {
        async {
            transaction {

                UserDao[profile.uniqueId].apply {
                    // localeを更新
                    locale = profile.locale.toString()
                }

            }
        }.await()
    }

    private fun load(player: Player, profile: Profile): Profile {
        UserDao[profile.uniqueId].apply {
            // playerNameを更新
            name = player.name
            // 更新日時を記録
            updateDate = DateTime.now()

            // 以下profileのロード処理
            profile.locale = Locale(locale)
        }

        return profile
    }

    private fun create(player: Player) {
        UserDao.new(player.uniqueId) {
            name = player.name
        }
    }
}