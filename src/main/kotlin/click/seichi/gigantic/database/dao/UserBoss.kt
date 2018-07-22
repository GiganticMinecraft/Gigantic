package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserBossTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserBoss(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserBoss>(UserBossTable)

    var user by User referencedOn UserBossTable.userId

    var bossId by UserBossTable.bossId

    var defeat by UserBossTable.defeat

}