package click.seichi.gigantic.cache.cache

import click.seichi.gigantic.cache.key.Keys
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
            val rankingEntity = RankingEntity(uniqueId)

            Keys.RANK_PLAYER_NAME.let {
                force(it, it.read(rankingEntity))
            }
            Keys.RANK_LEVEL.let {
                force(it, it.read(rankingEntity))
            }

            Score.values().forEach {
                it.read(rankingEntity, this@RankingPlayerCache)
            }
        }
    }

    override fun write() {
        // Do nothing
    }
}