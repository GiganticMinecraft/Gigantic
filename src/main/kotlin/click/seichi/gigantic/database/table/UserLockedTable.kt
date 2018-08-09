package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserLockedTable : IntIdTable("users_locked") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val lockedId = integer("locked_id").primaryKey()

    val hasUnlocked = bool("has_unlocked").default(false)

}