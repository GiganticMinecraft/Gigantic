package click.seichi.gigantic.database.dao.user

import click.seichi.gigantic.database.table.user.UserBeltTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserBelt(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserBelt>(UserBeltTable)

    var user by User referencedOn UserBeltTable.userId

    var beltId by UserBeltTable.beltId

    var canSwitch by UserBeltTable.canSwitch

    var isUnlocked by UserBeltTable.isUnlocked

}