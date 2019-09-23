package click.seichi.gigantic.player

import click.seichi.gigantic.database.dao.PurchaseHistory
import click.seichi.gigantic.product.Product
import org.joda.time.DateTime

/**
 * @author tar0ss
 */
class PurchaseTicket(
        // こちら側で新規発行する場合はnullにする
        _purchaseId: Int?,
        val product: Product,
        val amount: Int,
        val date: DateTime = DateTime.now(),
        var isCancelled: Boolean = false,
        var cancelledAt: DateTime? = null
) {
    var purchaseId: Int? = _purchaseId
        private set

    constructor(purchaseHistory: PurchaseHistory) : this(
            purchaseHistory.id.value,
            Product.findById(purchaseHistory.productId)!!,
            purchaseHistory.amount,
            purchaseHistory.createdAt,
            purchaseHistory.isCancelled,
            purchaseHistory.cancelledAt
    )

    fun cancel(ticket: PurchaseTicket) {
        ticket.isCancelled = true
        ticket.cancelledAt = DateTime.now()
    }

    fun setIdIfAbsent(purchaseId: Int) {
        if (this.purchaseId != null) return
        this.purchaseId = purchaseId
    }

}