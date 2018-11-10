package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import java.util.*

/**
 * @author tar0ss
 */
object MonsterSpiritMessages {

    val SPIRIT_SEALED = { monsterName: String ->
        LocalizedText(
                Locale.JAPANESE to "封印されし$monsterName"
        )
    }

}