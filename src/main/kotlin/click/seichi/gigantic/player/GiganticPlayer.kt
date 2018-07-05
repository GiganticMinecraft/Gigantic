package click.seichi.gigantic.player

import click.seichi.gigantic.database.dao.UserDao
import click.seichi.gigantic.player.components.PlayerSetting
import click.seichi.gigantic.player.components.PlayerStatus

/**
 * @author tar0ss
 */
class GiganticPlayer(userDao: UserDao) {

    val setting = PlayerSetting(userDao)

    val status = PlayerStatus(userDao)

    fun save(userDao: UserDao) {
        userDao.locale = setting.locale.toString()
        userDao.mana = status.mana.current
        userDao.mineBlock = status.mineBlock.current
    }

}