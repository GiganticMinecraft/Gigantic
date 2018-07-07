package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.UserWill
import click.seichi.gigantic.will.Will

class Aptitude(willMap: Map<Will, UserWill>) {

    var willAptitude: Set<Will> = willMap
            .filter { it.value.hasAptitude }
            .map { it.key }
            .toSet()
}
