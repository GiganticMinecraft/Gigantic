package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.PlayerComponent
import java.util.*

class PlayerSetting : PlayerComponent {
    lateinit var locale: Locale

    override fun onLoad(userContainer: UserContainer) {
        locale = Locale(userContainer.user.locale)
    }

    override fun onSave(userContainer: UserContainer) {
        userContainer.user.locale = locale.toString()
    }
}
