package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import java.util.*

/**
 * @author tar0ss
 */
object MonsterSpiritMessages {

    val SEAL = { monsterName: String ->
        LocalizedText(
                Locale.JAPANESE to "封印されし$monsterName"
        )
    }

    val START = { monsterName: String ->
        LocalizedText(
                Locale.JAPANESE to "$monsterName"
        )
    }

}