package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserWillTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserWill(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserWill>(UserWillTable)

    var user by User referencedOn UserWillTable.userId

    var willId by UserWillTable.willId

    var memory by UserWillTable.memory

    var hasAptitude by UserWillTable.hasAptitude

    var secretAmount by UserWillTable.secretAmount

}