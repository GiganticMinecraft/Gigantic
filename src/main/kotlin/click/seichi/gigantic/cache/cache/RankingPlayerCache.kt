package click.seichi.gigantic.cache.cache

import click.seichi.gigantic.database.RankingEntity
import click.seichi.gigantic.ranking.Score
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * @author tar0ss
 */
class RankingPlayerCache(private val uniqueId: UUID) : Cache<RankingPlayerCache>() {
    override fun read() {
        transaction {
            val entity = RankingEntity(uniqueId)

            Score.values().forEach {
                it.read(entity, this@RankingPlayerCache)
            }
        }
    }

    override fun write() {
        // Do nothing
    }
}