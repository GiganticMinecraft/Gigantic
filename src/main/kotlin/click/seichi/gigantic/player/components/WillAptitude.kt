package click.seichi.gigantic.player.components

import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade

class WillAptitude(
        private val willAptitude: MutableSet<Will> = mutableSetOf()
) {

    fun has(will: Will) = willAptitude.contains(will)

    fun copySet() = willAptitude.toSet()

    private fun add(will: Will) = willAptitude.add(will)

    fun addIfNeeded(level: Level): Set<Will> {
        val newWillSet = mutableSetOf<Will>()
        WillGrade.values().forEach { grade ->
            (0..calcMissingAptitude(level, grade)).forEach {
                Will.values().toList().filter {
                    it.grade == WillGrade.BASIC
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

    private fun needAptitude(level: Level, grade: WillGrade): Int {
        return when (grade) {
            WillGrade.BASIC -> level.current.div(20).plus(1).coerceIn(0, 5)
            WillGrade.ADVANCED -> level.current.div(20).minus(4).coerceIn(0, 5)
        }
    }

    private fun calcMissingAptitude(level: Level, grade: WillGrade): Int {
        return needAptitude(level, grade).minus(count(grade)).coerceAtLeast(0)
    }
}
