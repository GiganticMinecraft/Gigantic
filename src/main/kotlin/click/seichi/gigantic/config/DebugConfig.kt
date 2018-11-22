package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic

/**
 * @author tar0ss
 */
object DebugConfig : SimpleConfiguration("debug", Gigantic.PLUGIN) {

    val START_LEVEL = getInt("start_level")

    val IS_SAVE_DATA = getBoolean("is_save_data")

}