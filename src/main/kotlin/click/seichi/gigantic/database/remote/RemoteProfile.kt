package click.seichi.gigantic.database.remote

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.database.dao.UserDao
import click.seichi.gigantic.profile.Profile
import click.seichi.gigantic.profile.SkillPreferences
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
    private fun load() = UserDao[player.uniqueId].apply {
        name = player.name
    }.newProfile()

    /**
     * player profileを作成し、データベースに追加します。
     *
     * @return プロフィール
     */
    private fun create() = UserDao.new(player.uniqueId) {
        name = player.name
    }.newProfile()


    /**
     * player profile をデータベースに書き込みます
     *
     * @param profile プロフィール
     */
    fun saveAsync(profile: Profile) = async {
        transaction {
            UserDao[profile.uniqueId].saveProfile(profile)
        }
    }

    /**
     * daoからprofileを生成します
     *
     * @return プロフィール
     */
    private fun UserDao.newProfile() = Profile(
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
     * profileをセーブします
     *
     * @param profile プロフィール
     */
    private fun UserDao.saveProfile(profile: Profile) {
        // localeを更新
        locale = profile.locale.toString()
        // 更新日時を記録
        updatedDate = DateTime.now()
        // MineBlockを更新
        mineBlock = profile.mineBlock
        // 現在のマナ値を更新
        mana = profile.mana
        // TODO skillpreference
    }
}