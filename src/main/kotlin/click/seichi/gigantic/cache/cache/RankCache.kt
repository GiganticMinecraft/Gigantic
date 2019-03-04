package click.seichi.gigantic.cache.cache

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.database.RankEntity
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * @author tar0ss
 */
class RankCache(private val uniqueId: UUID) : Cache<RankCache>() {
    override fun read() {
        transaction {
            val entity = RankEntity(uniqueId)

            Keys.RANK_EXP.let {
                offer(it, it.read(entity))
            }
        }
    }

    override fun write() {
        // Do nothing
    }
}