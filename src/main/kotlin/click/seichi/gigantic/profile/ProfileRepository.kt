package click.seichi.gigantic.profile

import click.seichi.gigantic.message.lang.DatabaseLang
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object ProfileRepository {

    private val profileMap = mutableMapOf<UUID, Profile>()

    fun addProfile(player: Player, profile: Profile) {
        val uniqueId = player.uniqueId ?: return
        DatabaseLang.PROFILE_LOADING_MESSAGE.sendTo(player)
        profileMap[uniqueId] = profile
        DatabaseLang.PROFILE_LOAD_COMPLETED_MESSAGE.sendTo(player)
    }

    fun removeProfile(uniqueId: UUID) {
        profileMap.remove(uniqueId)
    }

    fun getProfile(uniqueId: UUID) = profileMap[uniqueId]

}