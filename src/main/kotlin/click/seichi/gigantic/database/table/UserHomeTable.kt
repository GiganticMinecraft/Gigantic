package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserHomeTable : IntIdTable("users_homes") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val homeId = integer("home_id").primaryKey()

    val serverName = varchar("server_name", 10)

    val worldId = uuid("world_id")

    val x = double("x")

    val y = double("y")

    val z = double("z")
}