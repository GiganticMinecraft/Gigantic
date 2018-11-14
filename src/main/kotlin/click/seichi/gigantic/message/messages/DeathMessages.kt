package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.monster.SoulMonster
import java.util.*

/**
 * @author tar0ss
 */
object DeathMessages {

    val BY_MONSTER = { monster: SoulMonster ->
        LocalizedText(
                Locale.JAPANESE.let { it to "${monster.getName(it)}に殺された..." }
        )
    }

}