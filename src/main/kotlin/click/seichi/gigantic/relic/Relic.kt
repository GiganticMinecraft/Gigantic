package click.seichi.gigantic.relic

import click.seichi.gigantic.message.LocalizedText

/**
 * @author tar0ss
 */
class Relic(
        val id: Int,
        val name: LocalizedText,
        vararg description: LocalizedText
) {
    val descriptionList = description.toList()
}