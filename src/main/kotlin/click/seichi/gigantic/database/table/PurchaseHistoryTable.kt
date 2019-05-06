package click.seichi.gigantic.database.table

import click.seichi.gigantic.database.table.user.UserTable
import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object PurchaseHistoryTable : IntIdTable("purchase_history") {

    val userId = reference("unique_id", UserTable).index()

    val productId = integer("product_id")

    val amount = integer("amount")

    val createdAt = datetime("created_at")

    val isCancelled = bool("is_cancelled").default(false)

    val cancelledAt = datetime("cancelled_at").nullable()

}