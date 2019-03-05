package click.seichi.gigantic.database

import click.seichi.gigantic.database.dao.ranking.RankingScore
import java.util.*

/**
 * @author tar0ss
 */
class RankingEntity(uniqueId: UUID) {

    val rankingScore = RankingScore.findById(uniqueId) ?: RankingScore.new(uniqueId) {}

}