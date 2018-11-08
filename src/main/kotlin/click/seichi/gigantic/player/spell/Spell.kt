package click.seichi.gigantic.player.spell

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.SpellMessages

/**
 * @author tar0ss
 */
enum class Spell(
        val id: Int,
        val localizedName: LocalizedText,
        val localizedLore: List<LocalizedText>?
) {
    STELLA_CLAIR(0, SpellMessages.STELLA_CLAIR, null),
    AQUA_LINEA(100, SpellMessages.AQUA_LINEA, null),
    IGNIS_VOLCANO(200, SpellMessages.IGNIS_VOLCANO, null),
    AER_SLASH(300, SpellMessages.AER_SLASH, null),
    TERRA_DRAIN(400, SpellMessages.TERRA_DRAIN, null),
    GRAND_NATURA(500, SpellMessages.GRAND_NATURA, null),
    ;

}