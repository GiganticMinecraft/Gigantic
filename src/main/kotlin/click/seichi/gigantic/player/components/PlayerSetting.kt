package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.UserDao
import java.util.*

class PlayerSetting(userDao: UserDao) {

    val locale = Locale(userDao.locale)
}
