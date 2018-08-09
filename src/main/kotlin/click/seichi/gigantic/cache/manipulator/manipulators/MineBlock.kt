package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.player.MineBlockReason

class MineBlock : Manipulator<MineBlock, PlayerCache> {

    private val map: MutableMap<MineBlockReason, Long> = mutableMapOf()

    override fun from(cache: Cache<PlayerCache>): MineBlock? {
        MineBlockReason.values()
                .map { add(cache.find(Keys.MINEBLOCK_MAP[it] ?: return null) ?: return null, it) }
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        MineBlockReason.values()
                .forEach { cache.offer(Keys.MINEBLOCK_MAP[it] ?: return false, map[it] ?: return false) }
        return true
    }

    fun add(num: Long, reason: MineBlockReason = MineBlockReason.GENERAL): Long {
        val next = (map[reason] ?: 0L) + num
        map[reason] = next
        return next
    }

    fun get(reason: MineBlockReason = MineBlockReason.GENERAL) = map[reason] ?: 0L

    fun copyMap() = map.toMap()

}
