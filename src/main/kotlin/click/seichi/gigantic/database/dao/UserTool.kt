package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserToolTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserTool(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserTool>(UserToolTable)

    var user by User referencedOn UserToolTable.userId

    var toolId by UserToolTable.toolId

    var canSwitch by UserToolTable.canSwitch

    var isUnlocked by UserToolTable.isUnlocked

}