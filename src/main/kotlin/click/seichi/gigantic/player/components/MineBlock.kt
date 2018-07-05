package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.UserDao

class MineBlock(userDao: UserDao) {
    var current = userDao.mineBlock
}
