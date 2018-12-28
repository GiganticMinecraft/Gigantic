package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable
import org.joda.time.DateTime

/**
 * @author tar0ss
 */
object DonateHistoryTable : IntIdTable("donate_history") {

    val userId = reference("unique_id", UserTable).index()

    val amount = integer("amount")

    val createdAt = datetime("created_at").default(DateTime.now())

}