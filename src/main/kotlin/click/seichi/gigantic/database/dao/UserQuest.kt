package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserQuestTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity

/**
 * @author tar0ss
 */
class UserQuest(id: EntityID<Int>) : IntEntity(id) {
    companion object : EntityClass<Int, UserQuest>(UserQuestTable)

    var user by User referencedOn UserQuestTable.userId

    var questId by UserQuestTable.questId

    var isOrdered by UserQuestTable.isOrdered

    var orderedAt by UserQuestTable.orderedAt

    var isProcessed by UserQuestTable.isProcessed

    var processedDegree by UserQuestTable.processedDegree

}