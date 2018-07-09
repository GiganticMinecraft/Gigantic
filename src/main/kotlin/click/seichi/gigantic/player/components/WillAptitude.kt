package click.seichi.gigantic.player.components

import click.seichi.gigantic.will.Will

class WillAptitude(
        private val willAptitude: MutableSet<Will> = mutableSetOf()
) {

    fun has(will: Will) = willAptitude.contains(will)

    fun copySet() = willAptitude.toSet()

    fun add(will: Will) = willAptitude.add(will)

}
