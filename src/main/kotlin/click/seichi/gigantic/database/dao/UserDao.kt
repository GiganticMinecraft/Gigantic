package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import java.util.*

/**
 * @author tar0ss
 */
class UserDao(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, UserDao>(UserTable)

    var name by UserTable.name

    var locale by UserTable.locale

    var mineBlock by UserTable.mineBlock

    var mana by UserTable.mana

    var createdDate by UserTable.createdAt

    var updatedDate by UserTable.updatedAt

}