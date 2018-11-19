package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserExpTable : IntIdTable("users_exps") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val reasonId = integer("reasonId").primaryKey()

    val exp = long("exp").default(0L)

}