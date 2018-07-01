package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic

/**
 * @author tar0ss
 */
object DatabaseSetting : SimpleConfiguration("DATABASE", Gigantic.PLUGIN) {

    val HOST: String = getString("HOST")

    val DATABASE: String = getString("DATABASE")

    val USER: String = getString("USER")

    val PASSWORD: String = getString("PASSWORD")
}