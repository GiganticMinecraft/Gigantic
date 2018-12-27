package click.seichi.gigantic.database.table

import org.jetbrains.exposed.dao.IntIdTable
import org.joda.time.DateTime

/**
 * @author tar0ss
 */
object UserEffectTable : IntIdTable("users_effects") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val effectId = integer("effect_id").primaryKey()

    val isBought = bool("is_bought").default(false)

    val boughtAt = UserTable.datetime("bought_at").default(DateTime.now())

}