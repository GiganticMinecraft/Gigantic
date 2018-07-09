package click.seichi.gigantic.player.components

import click.seichi.gigantic.will.Will

class Memory(private val currentMap: MutableMap<Will, Long>) {

    fun add(will: Will, n: Long): Long {
        val next = currentMap[will] ?: 0L+n
        currentMap[will] = next
        return next
    }

    fun get(will: Will) = currentMap[will] ?: 0L

    fun copyMap() = currentMap.toMap()

}
