package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.SeichiLevelConfig
import click.seichi.gigantic.database.dao.UserDao

class SeichiLevel(userDao: UserDao) {
    companion object {
        private val max = SeichiLevelConfig.MAX
    }

    var current = calcLevel(userDao.mineBlock)

    fun calcLevel(mineBlock: Long) =
            (1..max).firstOrNull {
                !canLevelUp(it + 1, mineBlock)
            } ?: max

    fun canLevelUp(nextLevel: Int, mineBlock: Long) =
            mineBlock > SeichiLevelConfig.MINEBLOCK_MAP[nextLevel] ?: Long.MAX_VALUE

}
