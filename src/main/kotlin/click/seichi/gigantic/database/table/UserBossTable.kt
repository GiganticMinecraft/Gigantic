package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserBossTable : IntIdTable("users_bosses") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val bossId = integer("boss_id").primaryKey()

    val defeat = long("defeat").default(0L)

}