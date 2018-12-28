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

    val health = long("health").default(Defaults.HEALTH)

    val toolId = integer("tool_id").default(Defaults.TOOL_ID)

    val beltId = integer("belt_id").default(Defaults.BELT_ID)

    val maxCombo = long("max_combo").default(0L)

    val spellToggle = bool("spell_toggle").default(false)

    val apostolusWidth = integer("apostolus_width").default(1)

    val apostolusHeight = integer("apostolus_height").default(1)

    val apostolusDepth = integer("apostolus_depth").default(1)

    val teleportToggle = bool("teleport_toggle").default(true)

    val effectId = integer("effect_id").default(Defaults.EFFECT_ID)

    val vote = integer("vote").default(0)

    val pomme = integer("pomme").default(0)

    val donation = integer("donation").default(0)

    val createdAt = datetime("created_at").default(DateTime.now())

    val updatedAt = datetime("updated_at").default(DateTime.now())

}