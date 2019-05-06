package click.seichi.gigantic.player

import click.seichi.gigantic.database.dao.PurchaseHistory
import click.seichi.gigantic.product.Product
import org.joda.time.DateTime

/**
 * @author tar0ss
 */
class PurchaseTicket(
        // こちら側で新規発行する場合はnullにする
        val purchaseId: Int?,
        val product: Product,
        val amount: Int,
        val date: DateTime = DateTime.now(),
        var isCancelled: Boolean = false,
        var cancelledAt: DateTime? = null
) {
    constructor(purchaseHistory: PurchaseHistory) : this(
            purchaseHistory.id.value,
            Product.findById(purchaseHistory.productId)!!,
            purchaseHistory.amount,
            purchaseHistory.createdAt
    )

    fun cancel(ticket: PurchaseTicket) {
        ticket.isCancelled = true
        ticket.cancelledAt = DateTime.now()
    }

}