package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object DonateHistoryTable : IntIdTable("donate_history") {

    val userId = reference("unique_id", UserTable).index()

    val amount = integer("requireAmount")

    val createdAt = datetime("created_at")

}