package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserHomeTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserHome(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserHome>(UserHomeTable)

    var user by User referencedOn UserHomeTable.userId

    var homeId by UserHomeTable.homeId

    var serverName by UserHomeTable.serverName

    var worldId by UserHomeTable.worldId

    var x by UserHomeTable.x

    var y by UserHomeTable.y

    var z by UserHomeTable.z

}