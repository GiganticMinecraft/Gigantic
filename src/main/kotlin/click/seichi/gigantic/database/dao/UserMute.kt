package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserMuteTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserMute(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserMute>(UserMuteTable)

    var user by User referencedOn UserMuteTable.userId

    var muteId by User referencedOn UserMuteTable.muteId

}