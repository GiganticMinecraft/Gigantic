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
        DatabaseMessages.PLAYER_LOADING.sendTo(player)
        // craftPlayerを非同期取得
        val craftPlayer = RemotePlayer(player)
                .loadOrCreateAsync()
                .await()
        craftPlayerMap[uniqueId] = craftPlayer
        craftPlayer.init()
        DatabaseMessages.PLAYER_LOAD_COMPLETED.sendTo(player)
    }

    fun remove(player: Player) = launch {
        val uniqueId = player.uniqueId ?: return@launch
        val craftPlayer = craftPlayerMap[uniqueId] ?: return@launch
        craftPlayer.finish()
        // TODO remove comment out
//        RemotePlayer(player)
//                .saveAsync(craftPlayer)
//                .await()
    }

    fun find(uniqueId: UUID) = craftPlayerMap[uniqueId]

}