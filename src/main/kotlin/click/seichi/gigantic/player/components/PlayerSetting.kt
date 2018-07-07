package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.User
import java.util.*

class PlayerSetting(user: User) {

    val locale = Locale(user.locale)
}
