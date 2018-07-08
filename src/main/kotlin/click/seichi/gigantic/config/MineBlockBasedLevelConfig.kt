package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic

/**
 * @author tar0ss
 */
abstract class MineBlockBasedLevelConfig(fileName: String) : SimpleConfiguration(fileName, Gigantic.PLUGIN) {

    val max by lazy { getInt("max") }

    val mineblockMap by lazy {
        (1..max).map {
            it to getLong("level_map.$it.mineBlock")
        }.toMap()
    }
}