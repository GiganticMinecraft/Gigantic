package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserDisplayTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserDisplay(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserDisplay>(UserDisplayTable)

    var user by User referencedOn UserDisplayTable.userId

    var displayId by UserDisplayTable.displayId

    var isDisplay by UserDisplayTable.isDisplay

}