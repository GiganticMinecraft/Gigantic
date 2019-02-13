package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserMuteTable : IntIdTable("users_mutes") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val muteId = reference("mute_id", UserTable).primaryKey()

}