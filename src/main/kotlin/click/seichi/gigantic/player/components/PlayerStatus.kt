package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.User
import click.seichi.gigantic.database.dao.UserWill
import click.seichi.gigantic.will.Will

class PlayerStatus(user: User, willMap: Map<Will, UserWill>) {

    val mana = Mana(user)

    val mineBlock = MineBlock(user)

    val seichiLevel = SeichiLevel(user)
    // TODO implements
    val explosionLevel = 3

    val memory = Memory(willMap)

    val aptitude = Aptitude(willMap)
}
