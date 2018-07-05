package click.seichi.gigantic.player

import click.seichi.gigantic.database.remote.RemotePlayer
import click.seichi.gigantic.message.lang.DatabaseLang
import kotlinx.coroutines.experimental.launch
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object GiganticPlayerRepository {

    private val gPlayerMap = mutableMapOf<UUID, GiganticPlayer>()


    fun add(player: Player) = launch {
        val uniqueId = player.uniqueId ?: return@launch
        DatabaseLang.PLAYER_LOADING_MESSAGE.sendTo(player)
        // gPlayerを非同期取得
        gPlayerMap[uniqueId] = RemotePlayer(player).loadOrCreateAsync().await()
        DatabaseLang.PLAYER_LOAD_COMPLETED_MESSAGE.sendTo(player)
    }

    fun remove(player: Player) = launch {
        val uniqueId = player.uniqueId ?: return@launch
        val gPlayer = gPlayerMap[uniqueId] ?: return@launch
        RemotePlayer(player).saveAsync(gPlayer).await()
    }

    fun find(uniqueId: UUID) = gPlayerMap[uniqueId]

}