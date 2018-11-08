package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserMonsterTable : IntIdTable("users_monsters") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val monsterId = integer("monster_id").primaryKey()

    val defeat = long("defeat").default(0L)

}