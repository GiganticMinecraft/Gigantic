package click.seichi.gigantic.player

import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.components.PlayerContainer

/**
 * @author tar0ss
 */
interface Remotable {

    fun getRemotableComponents(): List<Remotable> = listOf()

    fun load(userContainer: UserContainer) {
        onLoad(userContainer)
        getRemotableComponents().forEach { it.load(userContainer) }
    }

    fun init(playerContainer: PlayerContainer) {
        onInit(playerContainer)
        getRemotableComponents().forEach { it.init(playerContainer) }
    }

    fun finish(playerContainer: PlayerContainer) {
        onFinish(playerContainer)
        getRemotableComponents().forEach { it.finish(playerContainer) }
    }

    fun save(userContainer: UserContainer) {
        onSave(userContainer)
        getRemotableComponents().forEach { it.save(userContainer) }
    }

    fun onLoad(userContainer: UserContainer) {}

    fun onInit(playerContainer: PlayerContainer) {}

    fun onFinish(playerContainer: PlayerContainer) {}

    fun onSave(userContainer: UserContainer) {}
}