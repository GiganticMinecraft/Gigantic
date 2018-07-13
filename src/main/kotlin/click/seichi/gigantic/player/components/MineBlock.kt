package click.seichi.gigantic.player.components

import click.seichi.gigantic.player.MineBlockReason

class MineBlock(private val currentMap: MutableMap<MineBlockReason, Long>) {

    fun add(num: Long, reason: MineBlockReason = MineBlockReason.GENERAL): Long {
        val next = currentMap[reason] ?: num
        currentMap[reason] = next
        return next
    }

    fun get(reason: MineBlockReason = MineBlockReason.GENERAL) = currentMap[reason] ?: 0L

    fun copyMap() = currentMap.toMap()

}
