package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic

/**
 * @author tar0ss
 */
object DatabaseSetting : SimpleConfiguration("database", Gigantic.PLUGIN) {

    val HOST: String = getString("host")

    val DATABASE: String = getString("database")

    val USER: String = getString("user")

    val PASSWORD: String = getString("password")
}