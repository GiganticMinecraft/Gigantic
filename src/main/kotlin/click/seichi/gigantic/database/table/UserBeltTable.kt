package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserBeltTable : IntIdTable("users_belts") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val beltId = integer("belt_id").primaryKey()

    val canSwitch = bool("can_switch").default(false)

}