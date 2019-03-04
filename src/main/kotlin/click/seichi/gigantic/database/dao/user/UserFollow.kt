package click.seichi.gigantic.database.dao.user

import click.seichi.gigantic.database.table.user.UserFollowTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserFollow(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserFollow>(UserFollowTable)

    var user by User referencedOn UserFollowTable.userId

    var follow by User referencedOn UserFollowTable.followId

}