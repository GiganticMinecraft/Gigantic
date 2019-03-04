package click.seichi.gigantic.database.dao.user

import click.seichi.gigantic.database.table.user.UserExpTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserExp(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserExp>(UserExpTable)

    var user by User referencedOn UserExpTable.userId

    var reasonId by UserExpTable.reasonId

    var exp by UserExpTable.exp

}