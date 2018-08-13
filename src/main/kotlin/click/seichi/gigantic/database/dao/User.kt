package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import java.util.*

/**
 * @author tar0ss
 */
class User(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, User>(UserTable)

    var name by UserTable.name

    var localeString by UserTable.localeString

    var mana by UserTable.mana

    var health by UserTable.health

    var isFirstJoin by UserTable.isFirstJoin

    var createdDate by UserTable.createdAt

    var updatedDate by UserTable.updatedAt

}