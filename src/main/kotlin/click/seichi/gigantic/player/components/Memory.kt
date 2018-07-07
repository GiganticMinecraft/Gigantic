package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.UserWill
import click.seichi.gigantic.will.Will

class Memory(willMap: Map<Will, UserWill>) {

    val memoryMap: Map<Will, Long> = willMap
            .map { it.key to it.value.memory }
            .toMap()
}
