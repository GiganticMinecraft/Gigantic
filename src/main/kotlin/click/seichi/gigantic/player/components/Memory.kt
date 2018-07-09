package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.PlayerComponent
import click.seichi.gigantic.will.Will

class Memory : PlayerComponent {

    private var currentMap: MutableMap<Will, Long> = mutableMapOf()

    fun add(will: Will, n: Long): Long {
        val next = currentMap[will] ?: 0L+n
        currentMap[will] = next
        return next
    }

    fun get(will: Will) = currentMap[will] ?: 0L

    override fun onLoad(userContainer: UserContainer) {
        currentMap = userContainer.userWillMap
                .map { it.key to it.value.memory }
                .toMap().toMutableMap()
    }

    override fun onSave(userContainer: UserContainer) {
        currentMap.forEach { will, current ->
            userContainer.userWillMap[will]?.memory = current
        }
    }
}
