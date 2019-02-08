package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserDisplayTable : IntIdTable("users_displays") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val displayId = integer("display_id").primaryKey()

    val isDisplay = bool("isDisplay").default(true)

}