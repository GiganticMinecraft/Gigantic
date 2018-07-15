package click.seichi.gigantic.player

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.database.remote.RemotePlayer
import click.seichi.gigantic.language.messages.DatabaseMessages
import kotlinx.coroutines.experimental.launch
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object PlayerRepository {

    private val craftPlayerMap = mutableMapOf<UUID, CraftPlayer>()

    fun add(player: Player) {
        val uniqueId = player.uniqueId ?: return
        DatabaseMessages.PLAYER_LOADING.sendTo(player)
        launch {
            // craftPlayerを非同期取得
            craftPlayerMap[uniqueId] = RemotePlayer(player)
                    .loadOrCreateAsync()
                    .await()
            Bukkit.getScheduler().runTask(Gigantic.PLUGIN) {
                craftPlayerMap[uniqueId]!!.init()
                DatabaseMessages.PLAYER_LOAD_COMPLETED.sendTo(player)
            }
        }
    }

    fun remove(player: Player) {
        val uniqueId = player.uniqueId ?: return
        val craftPlayer = craftPlayerMap[uniqueId] ?: return
        craftPlayer.finish()
        launch {
            RemotePlayer(player)
                    .saveAsync(craftPlayer)
                    .await()
        }
    }

    fun find(uniqueId: UUID) = craftPlayerMap[uniqueId]

}