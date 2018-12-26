package click.seichi.gigantic.config

/**
 * @author tar0ss
 */
object DebugConfig : SimpleConfiguration("debug") {

    val LEVEL by lazy { getInt("level") }

    val ACHIEVEMENT_UNLOCK by lazy { getBoolean("achievement_unlock") }

}