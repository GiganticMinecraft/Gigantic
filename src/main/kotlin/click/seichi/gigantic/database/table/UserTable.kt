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

    val beltId = integer("belt_id").default(Defaults.BELT_ID)

    val isFirstJoin = bool("is_first_join").default(true)

    val maxCombo = long("max_combo").default(0L)

    val spellToggle = bool("spell_toggle").default(false)

    val teleportToggle = bool("teleport_toggle").default(false)

    val createdAt = datetime("created_at").default(DateTime.now())

    val updatedAt = datetime("updated_at").default(DateTime.now())

}