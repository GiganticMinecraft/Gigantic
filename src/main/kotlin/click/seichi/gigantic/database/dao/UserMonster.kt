package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserMonsterTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserMonster(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserMonster>(UserMonsterTable)

    var user by User referencedOn UserMonsterTable.userId

    var monsterId by UserMonsterTable.monsterId

    var defeat by UserMonsterTable.defeat

}