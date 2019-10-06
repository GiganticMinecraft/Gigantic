package click.seichi.gigantic.database.table.user

import click.seichi.gigantic.GiganticServer
import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserHomeTable : IntIdTable("users_homes") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val homeId = integer("home_id").primaryKey()

    val serverId = integer("server_id").default(GiganticServer.SPRING_ONE.id)

    val worldId = uuid("world_id")

    val x = double("x")

    val y = double("y")

    val z = double("z")

    val name = varchar("name", 20)

    val teleportOnSwitch = bool("teleport_on_switch").default(false)

}