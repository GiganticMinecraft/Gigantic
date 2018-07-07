package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.UserWill
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade

class Aptitude(willMap: Map<Will, UserWill>, isFirstJoin: Boolean) {

    init {
        if (isFirstJoin) {
            val yourWill = Random.nextWill(WillGrade.BASIC)
            willMap[yourWill]?.hasAptitude = true
        }
    }

    var willAptitude: Set<Will> = willMap
            .filter { it.value.hasAptitude }
            .map { it.key }
            .toSet()
}
