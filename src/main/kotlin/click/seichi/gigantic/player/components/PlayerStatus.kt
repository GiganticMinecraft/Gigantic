package click.seichi.gigantic.player.components

import click.seichi.gigantic.database.dao.User
import click.seichi.gigantic.database.dao.UserMineBlock
import click.seichi.gigantic.database.dao.UserWill
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.will.Will

class PlayerStatus(user: User, willMap: Map<Will, UserWill>, mineBlockMap: Map<MineBlockReason, UserMineBlock>) {

    val mana = Mana(user)

    val mineBlock = MineBlock(mineBlockMap)

    val seichiLevel = SeichiLevel(mineBlockMap)
    // TODO implements
    val explosionLevel = 3

    val memory = Memory(willMap)

    val aptitude = Aptitude(willMap)
}
