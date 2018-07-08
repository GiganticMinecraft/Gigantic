package click.seichi.gigantic.database

import click.seichi.gigantic.database.dao.User
import click.seichi.gigantic.database.dao.UserMineBlock
import click.seichi.gigantic.database.dao.UserWill
import click.seichi.gigantic.database.table.UserMineBlockTable
import click.seichi.gigantic.database.table.UserWillTable
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.will.Will
import java.util.*

/**
 * @author tar0ss
 */
class UserContainer(uniqueId: UUID) {
    val user = User[uniqueId]
    val userWillMap = UserWill
            .find { UserWillTable.userId eq uniqueId }
            .map { Will.findWillById(it.willId)!! to it }
            .toMap()
    val userMineBlockMap = UserMineBlock
            .find { UserMineBlockTable.userId eq uniqueId }
            .map { MineBlockReason.findById(it.reasonId)!! to it }
            .toMap()
}