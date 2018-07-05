package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.UserDao

class Mana(userDao: UserDao) {

    var current = userDao.mana
}
