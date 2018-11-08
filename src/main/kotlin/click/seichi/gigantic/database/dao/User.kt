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

    var beltId by UserTable.beltId

    var maxCombo by UserTable.maxCombo

    var spellToggle by UserTable.spellToggle

    var teleportToggle by UserTable.teleportToggle

    var terraDrainToggle by UserTable.terraDrainToggle

    var grandNaturaToggle by UserTable.grandNaturaToggle

    var aquaLineaToggle by UserTable.aquaLineaToggle

    var createdDate by UserTable.createdAt

    var updatedDate by UserTable.updatedAt

}