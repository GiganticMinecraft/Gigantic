package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.dao.user.User
import click.seichi.gigantic.database.table.DonateHistoryTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class DonateHistory(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, DonateHistory>(DonateHistoryTable)

    var user by User referencedOn DonateHistoryTable.userId

    var amount by DonateHistoryTable.amount

    var createdAt by DonateHistoryTable.createdAt

}