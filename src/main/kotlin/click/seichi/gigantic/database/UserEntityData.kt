package click.seichi.gigantic.database

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.database.dao.*
import click.seichi.gigantic.database.table.UserBossTable
import click.seichi.gigantic.database.table.UserMineBlockTable
import click.seichi.gigantic.database.table.UserRelicTable
import click.seichi.gigantic.database.table.UserWillTable
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import java.util.*

/**
 * @author tar0ss
 */
data class UserEntityData(val uniqueId: UUID) {

    val user = User[uniqueId]

    val userWillMap = UserWill
            .find { UserWillTable.userId eq uniqueId }
            .map { Will.findById(it.willId)!! to it }
            .toMap()

    val userMineBlockMap = UserMineBlock
            .find { UserMineBlockTable.userId eq uniqueId }
            .map { MineBlockReason.findById(it.reasonId)!! to it }
            .toMap()

    val userBossMap = UserBoss
            .find { UserBossTable.userId eq uniqueId }
            .map { Boss.findById(it.bossId)!! to it }
            .toMap()

    val userRelicMap = UserRelic
            .find { UserRelicTable.userId eq uniqueId }
            .map { Relic.findById(it.relicId)!! to it }
            .toMap()

}