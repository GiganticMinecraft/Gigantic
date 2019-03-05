package click.seichi.gigantic.ranking

import click.seichi.gigantic.database.table.ranking.RankingScoreTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * @author tar0ss
 */
class Ranking(val score: Score) {

    var rankMap = mapOf<Int, UUID>()
        private set

    private var uuidMap = mapOf<UUID, Int>()

    private var valueMap = mapOf<UUID, Long>()

    fun contains(uniqueId: UUID) = uuidMap.contains(uniqueId)

    fun getScore(uniqueId: UUID) = valueMap.getValue(uniqueId)

    fun getRank(uniqueId: UUID) = uuidMap.getValue(uniqueId)

    fun findUUID(rank: Int) = rankMap[rank]

    fun update() {
        val _rankMap = mutableMapOf<Int, UUID>()
        val _uuidMap = mutableMapOf<UUID, Int>()
        val _valueMap = mutableMapOf<UUID, Long>()
        transaction {
            // 同順位処理
            var rank = 1
            var prevValue = -1L
            RankingScoreTable.selectAll()
                    .notForUpdate()
                    .orderBy(RankingScoreTable.exp to SortOrder.DESC)
                    .limit(10000)
                    .forEachIndexed { index, row ->
                        val uniqueId = row[RankingScoreTable.id].value
                        val value = row[score.column]
                        if (prevValue != value) {
                            prevValue = value
                            rank = index + 1
                        }
                        _rankMap[rank] = uniqueId
                        _uuidMap[uniqueId] = rank
                        _valueMap[uniqueId] = value
                    }
            rankMap = _rankMap
            uuidMap = _uuidMap
            valueMap = _valueMap
        }
    }

}