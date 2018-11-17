package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.monster.SoulMonster
import java.util.*

/**
 * @author tar0ss
 */
object DeathMessages {

    val BY_MONSTER = { name: String, monster: SoulMonster ->
        LocalizedText(
                Locale.JAPANESE.let { it to "$name は ${monster.getName(it)} に倒された..." }
        )
    }

}