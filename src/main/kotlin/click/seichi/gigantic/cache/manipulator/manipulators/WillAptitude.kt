package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade

/**
 * @author tar0ss
 */
class WillAptitude : Manipulator<WillAptitude, PlayerCache> {

    private val set = mutableSetOf<Will>()

    override fun from(cache: Cache<PlayerCache>): WillAptitude? {
        set.clear()
        Will.values().map { it to cache.getOrPut(Keys.APTITUDE_MAP[it] ?: return null) }
                .filter { it.second }
                .forEach { add(it.first) }
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        Will.values().forEach { cache.offer(Keys.APTITUDE_MAP[it] ?: return false, has(it)) }
        return true
    }

    private fun has(will: Will) = set.contains(will)

    fun copySet() = set.toSet()

    private fun add(will: Will) = set.add(will)

    fun addRandomIfNeeded(willGrade: WillGrade, maxNum: Int): Will? {
        if (count(willGrade) >= maxNum) return null
        return Will.values()
                .filter { !has(it) && it.grade == willGrade }
                .shuffled()
                .firstOrNull()?.also {
                    add(it)
                }
    }

    private fun count(grade: WillGrade): Int {
        return Will.values()
                .filter { it.grade == grade }
                .count { has(it) }
    }
}