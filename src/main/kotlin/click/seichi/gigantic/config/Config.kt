package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic

/**
 * @author tar0ss
 */
object Config : SimpleConfiguration("config", Gigantic.PLUGIN) {

    val PLAYER_DEATH_PENALTY = getDouble("player.death_penalty")

    val WORLD_SIDE_LENGTH = getDouble("world.side_length")
}