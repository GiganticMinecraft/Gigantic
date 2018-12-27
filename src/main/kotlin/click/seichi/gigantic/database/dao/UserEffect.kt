package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserEffectTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserEffect(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserEffect>(UserEffectTable)

    var user by User referencedOn UserEffectTable.userId

    var effectId by UserEffectTable.effectId

    var isBought by UserEffectTable.isBought

    var boughtAt by UserEffectTable.boughtAt

}