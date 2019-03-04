package click.seichi.gigantic.database.dao.rank

import click.seichi.gigantic.database.table.rank.RankExpTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import java.util.*

/**
 * @author tar0ss
 */
class RankExp(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, RankExp>(RankExpTable)

    var amount by RankExpTable.amount

}