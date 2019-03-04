package click.seichi.gigantic.database.dao.rank

import click.seichi.gigantic.database.table.rank.RankValueTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import java.util.*

/**
 * @author tar0ss
 */
class RankValue(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, RankValue>(RankValueTable)

    var exp by RankValueTable.exp

}