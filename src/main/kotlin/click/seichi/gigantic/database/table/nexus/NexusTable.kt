package click.seichi.gigantic.database.table.nexus

import click.seichi.gigantic.database.table.user.UserTable
import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object NexusTable : IntIdTable("nexuses") {

    // TODO add server
    val ownerUniqueId = uuid("owner_unique_id").index(isUnique = false)

    val name = varchar("name", 50)

    val createdAt = UserTable.datetime("created_at")

    val updatedAt = UserTable.datetime("updated_at")
}