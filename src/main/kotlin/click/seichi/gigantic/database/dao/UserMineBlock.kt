package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserMineBlockTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserMineBlock(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserMineBlock>(UserMineBlockTable)

    var user by User referencedOn UserMineBlockTable.userId

    var reasonId by UserMineBlockTable.reasonId

    var mineBlock by UserMineBlockTable.mineBlock

}