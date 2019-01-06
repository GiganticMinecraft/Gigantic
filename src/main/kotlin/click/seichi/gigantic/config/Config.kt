package click.seichi.gigantic.config

/**
 * @author tar0ss
 */
object Config : SimpleConfiguration("config") {

    val WORLD_SIDE_LENGTH by lazy { getDouble("world.side_length") }

    val PLAYER_DEATH_PENALTY by lazy { getDouble("player.death_penalty") }

    val PLAYER_MAX_FOLLOW by lazy { getInt("player.max_follow") }

    val PROTECT_RADIUS by lazy { getDouble("player.protect_radius") }

    val LOAD_TIME by lazy { getInt("player.load_time") }

    val ELYTRA_CHARGE_UP_TIME by lazy { getLong("elytra.charge_up_time") }

    val ELYTRA_SPEED_MULTIPLIER by lazy { getDouble("elytra.speed_multiplier") }

    val ELYTRA_LAUNCH_MULTIPLIER by lazy { getDouble("elytra.launch_multiplier") }

    val MAX_BREAKABLE_GRAVITY by lazy { getInt("player.max_breakable_gravity") }

    val DEBUG_MODE by lazy { getBoolean("debug_mode") }

    val SKILL_HEAL_RATIO by lazy { getDouble("skill.heal.ratio") }

    val SKILL_HEAL_PROBABILITY by lazy { getDouble("skill.heal.probability") }

    val SKILL_MINE_COMBO_CONTINUATION_SECONDS by lazy { getDouble("skill.mine_combo.continuation_seconds") }

    val SKILL_MINE_COMBO_DECREASE_INTERVAL by lazy { getDouble("skill.mine_combo.decrease_interval") }

    val SKILL_FLASH_COOLTIME by lazy { getLong("skill.flash.cooltime") }

    val SKILL_MINE_BURST_DURATION by lazy { getLong("skill.mine_burst.duration") }

    val SKILL_MINE_BURST_COOLTIME by lazy { getLong("skill.mine_burst.cooltime") }

    val SPELL_STELLA_CLAIR_RATIO by lazy { getDouble("spell.stella_clair.ratio") }

    val SPELL_STELLA_CLAIR_PROBABILITY by lazy { getDouble("spell.stella_clair.probability") }

    val SPELL_APOSTOL_MANA_PER_BLOCK by lazy { getDouble("spell.apostol.mana_per_block") }

    val SPELL_APOSTOL_LIMIT_SIZE by lazy { getInt("spell.apostol.limit_size") }

    val SPELL_APOSTOL_DELAY by lazy { getDouble("spell.apostol.delay") }

}
