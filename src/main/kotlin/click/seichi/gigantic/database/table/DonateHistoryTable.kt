package click.seichi.gigantic.database.table

import click.seichi.gigantic.database.table.user.UserTable
import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object DonateHistoryTable : IntIdTable("donate_history") {

    val userId = reference("unique_id", UserTable).index()

    val amount = integer("amount")

    val createdAt = datetime("created_at")

}