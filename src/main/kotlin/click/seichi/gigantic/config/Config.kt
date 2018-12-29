package click.seichi.gigantic.config

/**
 * @author tar0ss
 */
object Config : SimpleConfiguration("config") {

    val PLAYER_DEATH_PENALTY by lazy { getDouble("player.death_penalty") }

    val WORLD_SIDE_LENGTH by lazy { getDouble("world.side_length") }

    val PROTECT_RADIUS by lazy { getDouble("player.protect_radius") }

    val MAX_BREAKABLE_GRAVITY by lazy { getInt("player.max_breakable_gravity") }

    val DEBUG_MODE by lazy { getBoolean("debug_mode") }

}