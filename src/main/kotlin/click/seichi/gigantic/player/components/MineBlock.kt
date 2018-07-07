package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.UserMineBlock
import click.seichi.gigantic.player.MineBlockReason

class MineBlock(mineBlockMap: Map<MineBlockReason, UserMineBlock>) {

    private val currentMap = mineBlockMap.map { it.key to it.value.mineBlock }.toMap().toMutableMap()

    fun add(num: Long,reason: MineBlockReason = MineBlockReason.GENERAL) {
        val next = currentMap[reason] ?: 0L + num
        currentMap[reason] = next
    }

    fun get(reason: MineBlockReason = MineBlockReason.GENERAL) = currentMap[reason] ?: 0L

}
