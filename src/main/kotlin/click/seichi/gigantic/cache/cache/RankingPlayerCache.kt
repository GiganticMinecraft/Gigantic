package click.seichi.gigantic.cache.cache

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.database.RankingEntity
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * @author tar0ss
 */
class RankingPlayerCache(private val uniqueId: UUID) : Cache<RankingPlayerCache>() {
    override fun read() {
        transaction {
            val entity = RankingEntity(uniqueId)

            Keys.RANK_EXP.let {
                force(it, it.read(entity))
            }
        }
    }

    override fun write() {
        // Do nothing
    }
}