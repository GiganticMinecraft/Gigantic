package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.PlayerComponent
import java.util.*

/**
 * @author tar0ss
 */
abstract class PlayerContainer : PlayerComponent {

    lateinit var uniqueId: UUID
        private set

    abstract val isFirstJoin: Boolean

    val setting = PlayerSetting()

    val status = PlayerStatus()

    override fun getComponents() = listOf(setting, status)

    override fun onLoad(userContainer: UserContainer) {
        userContainer.run {
            uniqueId = user.id.value
        }
    }
}