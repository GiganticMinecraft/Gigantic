package click.seichi.gigantic.database.table

import click.seichi.gigantic.player.Defaults
import org.jetbrains.exposed.dao.IdTable
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
object UserTable : IdTable<UUID>("users") {

    override val id = uuid("unique_id").primaryKey().entityId()

    val name = varchar("name", 50)

    val localeString = varchar("locale", 2).default(Locale.JAPANESE.toString())

    val mana = decimal("mana", 30, 6).default(Defaults.MANA.toBigDecimal())

    val toolId = integer("tool_id").default(Defaults.TOOL_ID)

    val beltId = integer("belt_id").default(Defaults.BELT_ID)

    val maxCombo = long("max_combo").default(0L)

    val combo = long("combo").default(0L)

    val lastComboTime = long("last_combo_time").default(System.currentTimeMillis())

    val spellToggle = bool("spell_toggle").default(false)

    val multiBreakWidth = integer("multi_break_width").default(1)

    val multiBreakHeight = integer("multi_break_height").default(1)

    val multiBreakDepth = integer("multi_break_depth").default(1)

    val teleportToggle = bool("teleport_toggle").default(false)

    val effectId = integer("effect_id").default(Defaults.EFFECT_ID)

    val vote = integer("vote").default(0)

    val pomme = integer("pomme").default(0)

    val donation = integer("donation").default(0)

    val givenVoteBonus = integer("given_vote_bonus").default(0)

    val isOnline = bool("is_online").default(false)

    val autoSwitch = bool("auto_switch").default(true)

    val skyWalkToggle = bool("sky_walk_toggle").default(false)

    val walkSpeed = decimal("walk_speed", 5, 1).default(Defaults.WALK_SPEED)

    val totem = integer("totem").default(0)

    val totemPiece = integer("totem_piece").default(0)

    val eventJmsKingGivenAt = datetime("event_jms_king_given_at").default(DateTime("2019-02-21T00:00:00+09:00"))

    val isNormalTexture = bool("is_normal_texture").default(true)

    val createdAt = datetime("created_at")

    val updatedAt = datetime("updated_at")

}