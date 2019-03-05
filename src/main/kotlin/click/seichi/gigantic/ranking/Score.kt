package click.seichi.gigantic.ranking

import click.seichi.gigantic.database.table.ranking.RankingScoreTable
import org.jetbrains.exposed.sql.Column

/**
 * @author tar0ss
 */
enum class Score(val column: Column<Long>) {
    EXP(RankingScoreTable.exp)
    ;
}