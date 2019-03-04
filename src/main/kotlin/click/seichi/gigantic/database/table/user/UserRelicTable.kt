package click.seichi.gigantic.database.table.user

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserRelicTable : IntIdTable("users_relics") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val relicId = integer("relic_id").primaryKey()

    val amount = long("amount").default(0L)

}