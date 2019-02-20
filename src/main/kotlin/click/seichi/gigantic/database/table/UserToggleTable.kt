package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserToggleTable : IntIdTable("users_toggles") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val toggleId = integer("toggle_id").primaryKey()

    val toggle = bool("toggle").default(true)

}