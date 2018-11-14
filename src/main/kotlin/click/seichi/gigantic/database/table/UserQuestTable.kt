package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable
import org.joda.time.DateTime

/**
 * @author tar0ss
 */
object UserQuestTable : IntIdTable("users_quests") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val questId = integer("quest_id").primaryKey()
    // クエストがサーバーによって発注されたかどうか
    val isOrdered = bool("is_ordered").default(false)
    // クエストが発注された時刻
    val orderedAt = datetime("ordered_at").default(DateTime.now())
    // プレイヤーによって進行中かどうか
    val isProcessed = bool("is_processed").default(false)
    // 進んだ度合い
    val processedDegree = integer("processed_degree").default(0)

    val clearNum = integer("clear_num").default(0)

}