package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.UserContainer
import click.seichi.gigantic.player.PlayerComponent
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade

class WillAptitude : PlayerComponent {

    var willAptitude: MutableSet<Will> = mutableSetOf()
        private set

    fun hasAptitude(will: Will) = willAptitude.contains(will)

    override fun onLoad(userContainer: UserContainer) {
        willAptitude = userContainer.userWillMap
                .filter { it.value.hasAptitude }
                .map { it.key }
                .toSet().toMutableSet()
    }

    override fun onInit(playerContainer: PlayerContainer) {
        if (playerContainer.isFirstJoin) {
            val yourWill = Random.nextWill(WillGrade.BASIC)
            willAptitude.add(yourWill)
        }
    }

    override fun onSave(userContainer: UserContainer) {
        willAptitude.forEach { will ->
            userContainer.userWillMap[will]?.hasAptitude = true
        }
    }


}
