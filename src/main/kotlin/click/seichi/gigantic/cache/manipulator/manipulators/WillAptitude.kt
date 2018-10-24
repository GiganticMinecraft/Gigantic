package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade

/**
 * @author tar0ss
 */
class WillAptitude : Manipulator<WillAptitude, PlayerCache> {

    private lateinit var level: Level

    private val set = mutableSetOf<Will>()

    override fun from(cache: Cache<PlayerCache>): WillAptitude? {
        set.clear()
        Will.values().map { it to cache.getOrPut(Keys.APTITUDE_MAP[it] ?: return null) }
                .filter { it.second }
                .forEach { add(it.first) }
        level = cache.find(CatalogPlayerCache.LEVEL) ?: return null
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        Will.values().forEach { cache.offer(Keys.APTITUDE_MAP[it] ?: return false, has(it)) }
        return true
    }

    fun has(will: Will) = set.contains(will)

    fun copySet() = set.toSet()

    private fun add(will: Will) = set.add(will)

    // 前はレベルで判定していたが、エラーで適正が正しく付与されなかったときに、
    // 直す術がないので、毎レベルごとに、正しい個数の適性があるか判定する。
    fun addIfNeeded(): Set<Will> {
        val newWillSet = mutableSetOf<Will>()
        WillGrade.values().forEach { grade ->
            (0 until calcMissingAptitude(grade)).forEach { _ ->
                Will.values().toList().filter {
                    it.grade == grade
                }.filterNot {
                    has(it)
                }.shuffled().firstOrNull()?.run {
                    newWillSet.add(this)
                    add(this)
                }
            }
        }
        return newWillSet
    }

    private fun count(grade: WillGrade): Int {
        return Will.values()
                .filter { it.grade == grade }
                .count { has(it) }
    }

    private fun needAptitude(grade: WillGrade): Int {
        return when (grade) {
            WillGrade.BASIC -> when (level.current) {
                0 -> 0
                in 1..20 -> 1
                in 21..40 -> 2
                in 41..60 -> 3
                in 61..80 -> 4
                else -> 5
            }
            WillGrade.ADVANCED -> when (level.current) {
                in 0..100 -> 0
                in 101..120 -> 1
                in 121..140 -> 2
                in 141..160 -> 3
                in 161..180 -> 4
                else -> 5
            }
        }
    }

    private fun calcMissingAptitude(grade: WillGrade): Int {
        return needAptitude(grade).minus(count(grade)).coerceAtLeast(0)
    }
}