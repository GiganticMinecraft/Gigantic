package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic

/**
 * @author tar0ss
 */
object Config : SimpleConfiguration("config", Gigantic.PLUGIN) {

    val DEATH_PENALTY = getDouble("death_penalty")
}