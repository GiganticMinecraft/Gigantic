package click.seichi.gigantic.database.table.ranking

import org.jetbrains.exposed.dao.IdTable
import java.util.*

/**
 * ランキングで使用されるscore
 * ※必ずlong型にすること
 *
 * @author tar0ss
 */
object RankingScoreTable : IdTable<UUID>("rankings_scores") {

    override val id = uuid("unique_id").primaryKey().entityId()

    val exp = long("exp").default(0L).index()

}