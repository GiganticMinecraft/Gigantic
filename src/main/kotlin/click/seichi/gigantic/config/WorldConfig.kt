package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic

/**
 * @author tar0ss
 */
object WorldConfig : SimpleConfiguration("world", Gigantic.PLUGIN) {

    val SEICHI_WORLDS: List<String> = getStringList("seichi")

}