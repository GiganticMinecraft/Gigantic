package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.UserWill
import click.seichi.gigantic.will.Will

class Memory(willMap: Map<Will, UserWill>) {

    private val memoryMap: MutableMap<Will, Long> = willMap
            .map { it.key to it.value.memory }
            .toMap().toMutableMap()

    fun add(will: Will, n: Long): Long {
        val next = memoryMap[will] ?: 0L+n
        memoryMap[will] = next
        return next
    }

    fun get(will: Will) = memoryMap[will] ?: 0L
}
