package click.seichi.gigantic.player

import click.seichi.gigantic.database.remote.RemotePlayer
import click.seichi.gigantic.language.messages.DatabaseMessages
import kotlinx.coroutines.experimental.launch
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object PlayerRepository {

    private val craftPlayerMap = mutableMapOf<UUID, CraftPlayer>()


    fun add(player: Player) = launch {
        val uniqueId = player.uniqueId ?: return@launch
        DatabaseMessages.PLAYER_LOADING_MESSAGE.sendTo(player)
        // craftPlayerを非同期取得
        craftPlayerMap[uniqueId] = RemotePlayer(player)
                .loadOrCreateAsync()
                .await()
                .apply {
                    init()
                }
        DatabaseMessages.PLAYER_LOAD_COMPLETED_MESSAGE.sendTo(player)
    }

    fun remove(player: Player) = launch {
        val uniqueId = player.uniqueId ?: return@launch
        val craftPlayer = craftPlayerMap[uniqueId] ?: return@launch
        craftPlayer.finish()
        RemotePlayer(player)
                .saveAsync(craftPlayer)
                .await()
    }

    fun find(uniqueId: UUID) = craftPlayerMap[uniqueId]

}