package click.seichi.gigantic.player.components

import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade

class WillAptitude(
        private val willAptitude: MutableSet<Will> = mutableSetOf()
) {

    fun has(will: Will) = willAptitude.contains(will)

    fun copySet() = willAptitude.toSet()

    private fun add(will: Will) = willAptitude.add(will)


    fun addIfNeeded(level: Level): Will? {
        val will = when (level.current) {
            1, 21, 41, 61, 81 -> {
                Will.values().toList().filter {
                    it.grade == WillGrade.BASIC
                }.filterNot {
                    has(it)
                }.shuffled().first()
            }
            101, 121, 141, 161, 181 -> {
                Will.values().toList().filter {
                    it.grade == WillGrade.ADVANCED
                }.filterNot {
                    has(it)
                }.shuffled().first()

            }
            else -> null
        } ?: return null
        add(will)
        return will
    }
}
