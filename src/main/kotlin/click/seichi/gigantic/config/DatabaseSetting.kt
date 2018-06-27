package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic

/**
 * @author tar0ss
 */
object DatabaseSetting : SimpleConfiguration("database", Gigantic.PLUGIN) {

    val host = getString("host")

    val database = getString("database")

    val user = getString("user")

    val password = getString("password")
}