package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.Remotable
import java.util.*

class PlayerSetting : Remotable {
    lateinit var locale: Locale

    override fun onLoad(userContainer: UserContainer) {
        locale = Locale(userContainer.user.locale)
    }

    override fun onSave(userContainer: UserContainer) {
        userContainer.user.locale = locale.toString()
    }
}
