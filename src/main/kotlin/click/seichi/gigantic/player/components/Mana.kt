package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.SeichiLevelConfig
import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.PlayerContainer
import click.seichi.gigantic.player.Remotable

class Mana : Remotable {

    var current: Long = 0L
        private set

    var max: Long = 0L
        private set

    fun increase(n: Long): Long {
        val next = current + n
        current = if (next > max) max else next
        return current
    }

    fun decrease(n: Long): Long {
        val next = current - n
        current = if (next < 0) 0L else next
        return current
    }

    override fun onLoad(userContainer: UserContainer) {
        current = userContainer.user.mana
    }

    override fun onInit(playerContainer: PlayerContainer) {
        max = SeichiLevelConfig.MANA_MAP[playerContainer.status.seichiLevel.current] ?: 0L
    }

    override fun onSave(userContainer: UserContainer) {
        userContainer.user.mana = current
    }

}
