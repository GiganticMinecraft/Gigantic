package click.seichi.gigantic.database.table.user

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserEffectTable : IntIdTable("users_effects") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val effectId = integer("effect_id").primaryKey()

    val isBought = bool("is_bought").default(false)

    val boughtAt = datetime("bought_at")

}