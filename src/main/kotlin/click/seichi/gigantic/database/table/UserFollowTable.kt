package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserFollowTable : IntIdTable("users_follows") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val followId = reference("follow_id", UserTable).primaryKey()

}