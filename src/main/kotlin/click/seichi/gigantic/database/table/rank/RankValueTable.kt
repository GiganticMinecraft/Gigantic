package click.seichi.gigantic.database.table.rank

import org.jetbrains.exposed.dao.IdTable
import java.util.*

/**
 * ランキングで使用されるカラム列挙
 *
 * @author tar0ss
 */
object RankValueTable : IdTable<UUID>("ranks") {

    override val id = uuid("unique_id").primaryKey().entityId()

    val exp = long("exp").default(0L).index()

}