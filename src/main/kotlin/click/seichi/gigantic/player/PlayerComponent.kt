package click.seichi.gigantic.player

import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.components.PlayerContainer

/**
 * @author tar0ss
 */
interface PlayerComponent {

    fun getComponents(): List<PlayerComponent> = listOf()

    fun load(userContainer: UserContainer) {
        onLoad(userContainer)
        getComponents().forEach { it.load(userContainer) }
    }

    fun init(playerContainer: PlayerContainer) {
        onInit(playerContainer)
        getComponents().forEach { it.init(playerContainer) }
    }

    fun finish(playerContainer: PlayerContainer) {
        onFinish(playerContainer)
        getComponents().forEach { it.finish(playerContainer) }
    }

    fun save(userContainer: UserContainer) {
        onSave(userContainer)
        getComponents().forEach { it.save(userContainer) }
    }

    fun onLoad(userContainer: UserContainer) {}

    fun onInit(playerContainer: PlayerContainer) {}

    fun onFinish(playerContainer: PlayerContainer) {}

    fun onSave(userContainer: UserContainer) {}
}