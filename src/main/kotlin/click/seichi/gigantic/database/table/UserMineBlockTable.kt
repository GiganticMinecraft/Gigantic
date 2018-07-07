package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserMineBlockTable : IntIdTable("users_mineblocks") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val reasonId = integer("reasonId").primaryKey()

    val mineBlock = long("mine_block").default(0L)

}