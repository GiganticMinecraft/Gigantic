package click.seichi.gigantic.config

/**
 * @author tar0ss
 */
object DebugConfig : SimpleConfiguration("debug") {

    val LEVEL by lazy { getInt("level") }

    val ACHIEVEMENT_UNLOCK by lazy { getBoolean("achievement_unlock") }

    val EFFECT_UNLOCK by lazy { getBoolean("effect_unlock") }

    val WILL_SPIRIT by lazy { getBoolean("will_spirit") }

    val SPELL_INFINITY by lazy { getBoolean("spell_infinity") }
}