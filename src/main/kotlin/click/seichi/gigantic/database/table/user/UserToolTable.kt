package click.seichi.gigantic.database.table.user

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserToolTable : IntIdTable("users_tools") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val toolId = integer("tool_id").primaryKey()

    val canSwitch = bool("can_switch").default(true)

    val isUnlocked = bool("is_unlocked").default(false)

}