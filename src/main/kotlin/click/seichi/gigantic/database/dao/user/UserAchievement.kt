package click.seichi.gigantic.database.dao.user

import click.seichi.gigantic.database.table.user.UserAchievementTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserAchievement(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserAchievement>(UserAchievementTable)

    var user by User referencedOn UserAchievementTable.userId

    var achievementId by UserAchievementTable.achievementId

    var hasUnlocked by UserAchievementTable.hasUnlocked

}