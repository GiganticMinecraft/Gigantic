package click.seichi.gigantic.player.profile

import click.seichi.gigantic.database.remote.RemoteProfile
import click.seichi.gigantic.message.lang.DatabaseLang
import kotlinx.coroutines.experimental.launch
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object ProfileRepository {

    private val profileMap = mutableMapOf<UUID, Profile>()


    fun addProfile(player: Player) = launch {
        val uniqueId = player.uniqueId ?: return@launch
        DatabaseLang.PROFILE_LOADING_MESSAGE.sendTo(player)
        // profileを非同期取得
        profileMap[uniqueId] = RemoteProfile(player).loadOrCreateAsync().await()
        DatabaseLang.PROFILE_LOAD_COMPLETED_MESSAGE.sendTo(player)
    }

    fun removeProfile(player: Player) = launch {
        val uniqueId = player.uniqueId ?: return@launch
        val profile = profileMap[uniqueId] ?: return@launch
        RemoteProfile(player).saveAsync(profile).await()
    }

    fun getProfile(uniqueId: UUID) = profileMap[uniqueId]

}