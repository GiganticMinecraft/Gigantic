package click.seichi.gigantic.database.dao.nexus

import click.seichi.gigantic.database.table.nexus.NexusTable
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

/**
 * @author tar0ss
 */
class NexusDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<NexusDAO>(NexusTable)

    // TODO add server
    var ownerUniqueId by NexusTable.ownerUniqueId

    var name by NexusTable.name

    var createdAt by NexusTable.createdAt

    var updatedAt by NexusTable.updatedAt
}