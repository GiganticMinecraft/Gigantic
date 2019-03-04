package click.seichi.gigantic.database.dao.user

import click.seichi.gigantic.database.table.user.UserToggleTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserToggle(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserToggle>(UserToggleTable)

    var user by User referencedOn UserToggleTable.userId

    var toggleId by UserToggleTable.toggleId

    var toggle by UserToggleTable.toggle

}