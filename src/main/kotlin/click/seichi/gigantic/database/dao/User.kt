package click.seichi.gigantic.database.dao

import click.seichi.gigantic.database.table.UserTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import java.util.*

/**
 * @author tar0ss
 */
class User(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, User>(UserTable)

    var name by UserTable.name

    var localeString by UserTable.localeString

    var mana by UserTable.mana

    var toolId by UserTable.toolId

    var beltId by UserTable.beltId

    var maxCombo by UserTable.maxCombo

    var combo by UserTable.combo

    var lastComboTime by UserTable.lastComboTime

    var spellToggle by UserTable.spellToggle

    var multiBreakWidth by UserTable.multiBreakWidth

    var multiBreakHeight by UserTable.multiBreakHeight

    var multiBreakDepth by UserTable.multiBreakDepth

    var teleportToggle by UserTable.teleportToggle

    var effectId by UserTable.effectId

    var vote by UserTable.vote

    var pomme by UserTable.pomme

    var donation by UserTable.donation

    var givenVoteBonus by UserTable.givenVoteBonus

    var isOnline by UserTable.isOnline

    var autoSwitch by UserTable.autoSwitch

    var skyWalkToggle by UserTable.skyWalkToggle

    var walkSpeed by UserTable.walkSpeed

    var totem by UserTable.totem

    var totemPiece by UserTable.totemPiece

    var eventJmsKingGivenAt by UserTable.eventJmsKingGivenAt

    var createdDate by UserTable.createdAt

    var updatedDate by UserTable.updatedAt
}