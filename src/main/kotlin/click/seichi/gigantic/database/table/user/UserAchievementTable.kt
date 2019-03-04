package click.seichi.gigantic.database.table.user

import org.jetbrains.exposed.dao.IntIdTable

/**
 * @author tar0ss
 */
object UserAchievementTable : IntIdTable("users_achievements") {

    val userId = reference("unique_id", UserTable).primaryKey()

    val achievementId = integer("achievement_id").primaryKey()

    val hasUnlocked = bool("has_unlocked").default(false)

}