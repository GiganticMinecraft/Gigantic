package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserLockedTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserLocked(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserLocked>(UserLockedTable)

    var user by User referencedOn UserLockedTable.userId

    var lockedId by UserLockedTable.lockedId

    var hasUnlocked by UserLockedTable.hasUnlocked

}