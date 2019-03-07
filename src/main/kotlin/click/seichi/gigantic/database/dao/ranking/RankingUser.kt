package click.seichi.gigantic.database.dao.ranking

import click.seichi.gigantic.database.table.ranking.RankingUserTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import java.util.*

/**
 * @author tar0ss
 */
class RankingUser(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, RankingUser>(RankingUserTable)

    var name by RankingUserTable.name

    var level by RankingUserTable.level
}