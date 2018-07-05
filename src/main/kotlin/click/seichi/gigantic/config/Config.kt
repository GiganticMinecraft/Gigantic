package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.player.level.SeichiLevel
import click.seichi.gigantic.util.Polynomial

/**
 * @author tar0ss
 */
object Config : SimpleConfiguration("config", Gigantic.PLUGIN) {

    private const val SEICHI_LEVEL_ID = "seichi_level"

    val seichiLevel: SeichiLevel =
            SeichiLevel(
                    getInt("$SEICHI_LEVEL_ID.max"),
                    Polynomial(
                            *getDoubleList("$SEICHI_LEVEL_ID.def.mineBlock").toDoubleArray()
                    ),
                    Polynomial(
                            *getDoubleList("$SEICHI_LEVEL_ID.def.mana").toDoubleArray()
                    )
            )
}