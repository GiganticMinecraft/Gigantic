package click.seichi.gigantic.player

import click.seichi.gigantic.database.dao.UserDao
import click.seichi.gigantic.player.components.PlayerSetting
import click.seichi.gigantic.player.components.PlayerStatus
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class GiganticPlayer(userDao: UserDao) {

    val player: Player
        get() = Bukkit.getServer().getPlayer(uniqueId)

    val uniqueId = userDao.id.value

    val setting = PlayerSetting(userDao)

    val status = PlayerStatus(userDao)

    fun save(userDao: UserDao) {
        userDao.locale = setting.locale.toString()
        userDao.mana = status.mana.current
        userDao.mineBlock = status.mineBlock.current
    }

}