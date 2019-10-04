package click.seichi.gigantic.config

/**
 * @author tar0ss
 */
object DebugConfig : SimpleConfiguration("debug") {

    val DEBUG_MODE by lazy { Config.getBoolean("debug_mode") }

    val LEVEL by lazy { getInt("level") }

    val ACHIEVEMENT_UNLOCK by lazy { getBoolean("achievement_unlock") }

    val ACHIEVEMENT_ANNOUNCE by lazy { getBoolean("achievement_announce") }

    val EFFECT_UNLOCK by lazy { getBoolean("effect_unlock") }

    val WILL_SPIRIT by lazy { getBoolean("will_spirit") }

    val SPELL_INFINITY by lazy { getBoolean("spell_infinity") }

    val TIPS_SPEED_UP by lazy { getBoolean("tips_speed_up") }
}