package click.seichi.gigantic.listener

import click.seichi.gigantic.profile.ProfileRepository
import click.seichi.spade.database.RemoteProfile
import kotlinx.coroutines.experimental.launch
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author tar0ss
 */
class PlayerListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player ?: return

        launch {
            ProfileRepository.addProfile(
                    player,
                    RemoteProfile.loadOrCreate(player)
            )
        }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player ?: return
        val uniqueId = player.uniqueId ?: return
        val profile = ProfileRepository.getProfile(uniqueId) ?: return

        // プロフィールを保存
        launch {
            RemoteProfile.save(profile)
        }
        ProfileRepository.removeProfile(uniqueId)
    }
}