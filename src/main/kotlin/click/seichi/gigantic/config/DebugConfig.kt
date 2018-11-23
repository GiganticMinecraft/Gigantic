package click.seichi.gigantic.config

/**
 * @author tar0ss
 */
object DebugConfig : SimpleConfiguration("debug") {

    val LEVEL by lazy { getInt("level") }

    val IS_SAVE_DATABASE by lazy { getBoolean("database.save") }

}