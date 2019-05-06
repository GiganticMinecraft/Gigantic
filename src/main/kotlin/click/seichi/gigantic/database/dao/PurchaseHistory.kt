package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.dao.user.User
import click.seichi.gigantic.database.table.PurchaseHistoryTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class PurchaseHistory(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, PurchaseHistory>(PurchaseHistoryTable)

    var user by User referencedOn PurchaseHistoryTable.userId

    var productId by PurchaseHistoryTable.productId

    var amount by PurchaseHistoryTable.amount

    var createdAt by PurchaseHistoryTable.createdAt

    var isCancelled by PurchaseHistoryTable.isCancelled

    var cancelledAt by PurchaseHistoryTable.cancelledAt

}