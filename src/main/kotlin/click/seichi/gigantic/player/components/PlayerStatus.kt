package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.UserDao

class PlayerStatus(userDao: UserDao) {

    val mana = Mana(userDao)

    val mineBlock = MineBlock(userDao)
}
