package click.seichi.gigantic.database.dao.user

import click.seichi.gigantic.database.table.user.UserHomeTable
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

    var serverId by UserHomeTable.serverId

    var worldId by UserHomeTable.worldId

    var x by UserHomeTable.x

    var y by UserHomeTable.y

    var z by UserHomeTable.z

    var name by UserHomeTable.name

    var teleportOnSwitch by UserHomeTable.teleportOnSwitch
}