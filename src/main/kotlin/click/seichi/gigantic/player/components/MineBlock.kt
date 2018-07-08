package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.player.Remotable

class MineBlock : Remotable {

    private var currentMap: MutableMap<MineBlockReason, Long> = mutableMapOf()

    fun add(num: Long, reason: MineBlockReason = MineBlockReason.GENERAL): Long {
        val next = currentMap[reason] ?: 0L+num
        currentMap[reason] = next
        return next
    }

    fun get(reason: MineBlockReason = MineBlockReason.GENERAL) = currentMap[reason] ?: 0L

    override fun onLoad(userContainer: UserContainer) {
        currentMap = userContainer.userMineBlockMap.map { it.key to it.value.mineBlock }.toMap().toMutableMap()
    }

    override fun onSave(userContainer: UserContainer) {
        currentMap.forEach { reason, current ->
            userContainer.userMineBlockMap[reason]?.mineBlock = current
        }
    }
}
