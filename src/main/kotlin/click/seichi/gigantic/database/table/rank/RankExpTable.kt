package click.seichi.gigantic.database.table.rank

import org.jetbrains.exposed.dao.IdTable
import java.util.*

/**
 * 累計経験値ランキング(レベル)
 *
 * @author tar0ss
 */
object RankExpTable : IdTable<UUID>("ranks_exps") {

    override val id = uuid("unique_id").primaryKey().entityId()

    val amount = long("amount").default(0L).index()

}