package click.seichi.gigantic.player.components

import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.language.messages.PlayerMessages
import click.seichi.gigantic.spirit.SpiritManager.spawn
import click.seichi.gigantic.spirit.spawnreason.WillSpawnReason
import click.seichi.gigantic.spirit.spirits.WillSpirit
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import click.seichi.gigantic.will.WillSize

class WillAptitude(
        private val willAptitude: MutableSet<Will> = mutableSetOf()
) {

    fun has(will: Will) = willAptitude.contains(will)

    fun copySet() = willAptitude.toSet()

    private fun add(will: Will) = willAptitude.add(will)


    fun onLevelUp(event: LevelUpEvent) {

        val will = when (event.level) {
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
        } ?: return
        add(will)

        if (event.level == 1) {
            PlayerMessages.FIRST_OBTAIN_WILL_APTITUDE(will).sendTo(event.player)
            spawn(WillSpirit(WillSpawnReason.AWAKE, event.player.eyeLocation
                    .clone()
                    .let {
                        it.add(
                                it.direction.x * 2,
                                0.0,
                                it.direction.z * 2
                        )
                    }, will, event.player, WillSize.MEDIUM))
        } else PlayerMessages.OBTAIN_WILL_APTITUDE(will).sendTo(event.player)
    }
}
