package click.seichi.gigantic.player

import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.components.PlayerSetting
import click.seichi.gigantic.player.components.PlayerStatus
import java.util.*

/**
 * @author tar0ss
 */
abstract class PlayerContainer : Remotable {

    lateinit var uniqueId: UUID
        private set

    abstract val isFirstJoin: Boolean

    val setting = PlayerSetting()

    val status = PlayerStatus()

    override fun getRemotableComponents() = listOf(setting, status)

    override fun onLoad(userContainer: UserContainer) {
        userContainer.run {
            uniqueId = user.id.value
        }
    }
}