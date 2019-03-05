package click.seichi.gigantic.database.table.ranking

import org.jetbrains.exposed.dao.IdTable
import java.util.*

/**
 * @author tar0ss
 */
object RankingUserTable : IdTable<UUID>("rankings_users") {

    override val id = uuid("unique_id").primaryKey().entityId()

    val name = varchar("name", 50).default("No Name")
}