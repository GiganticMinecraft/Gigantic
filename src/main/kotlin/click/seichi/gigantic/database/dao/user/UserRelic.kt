package click.seichi.gigantic.database.dao.user

import click.seichi.gigantic.database.table.user.UserRelicTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserRelic(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserRelic>(UserRelicTable)

    var user by User referencedOn UserRelicTable.userId

    var relicId by UserRelicTable.relicId

    var amount by UserRelicTable.amount

}