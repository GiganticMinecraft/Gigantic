package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IdTable
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
object UserTable : IdTable<UUID>("users") {

    override val id = uuid("unique_id").primaryKey().entityId()

    val name = varchar("name", 50)

    val localeString = varchar("localeString", 2).default(Locale.JAPANESE.toString())

    val mana = long("mana").default(0L)

    val isFirstJoin = bool("isFirstJoin").default(true)

    val createdAt = datetime("created_at").default(DateTime.now())

    val updatedAt = datetime("updated_at").default(DateTime.now())

}