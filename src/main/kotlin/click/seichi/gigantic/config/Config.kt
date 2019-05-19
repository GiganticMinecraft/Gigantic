package click.seichi.gigantic.config

/**
 * @author tar0ss
 */
object Config : SimpleConfiguration("config") {

    val RESOURCE_FOLDER by lazy { getString("resource.folder")!! }

    val RESOURCE_DEFAULT by lazy { RESOURCE_FOLDER + getString("resource.default")!! }

    val RESOURCE_NO_PARTICLE by lazy { RESOURCE_FOLDER + getString("resource.no_particle")!! }

    val TIPS_INTERVAL by lazy { getLong("tips.interval") }

    val WORLD_SIDE_LENGTH by lazy { getDouble("world.side_length") }

    val PLAYER_DEATH_PENALTY by lazy { getDouble("player.death_penalty") }

    val PLAYER_MAX_FOLLOW by lazy { getInt("player.max_follow") }

    val PLAYER_MAX_MUTE by lazy { getInt("player.max_mute") }

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

    val SPELL_MULTI_BREAK_MANA_PER_BLOCK by lazy { getDouble("spell.multi_break.mana_per_block") }

    val SPELL_MULTI_BREAK_LIMIT_SIZE by lazy { getInt("spell.multi_break.limit_size") }

    val SPELL_MULTI_BREAK_DELAY by lazy { getDouble("spell.multi_break.delay") }

    val SPELL_SKY_WALK_MANA_PER_BLOCK by lazy { getDouble("spell.sky_walk.mana_per_block") }

    val SPELL_SKY_WALK_RADIUS by lazy { getInt("spell.sky_walk.radius") }

    val SPELL_LUNA_FLEX_MANA_PER_DEGREE by lazy { getDouble("spell.luna_flex.mana_per_degree") }

}
