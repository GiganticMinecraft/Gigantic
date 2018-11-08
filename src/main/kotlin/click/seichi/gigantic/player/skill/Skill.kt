package click.seichi.gigantic.player.skill

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.SkillMessages

/**
 * @author tar0ss
 */
enum class Skill(
        val id: Int,
        val localizedName: LocalizedText,
        val localizedLore: List<LocalizedText>?
) {
    FLASH(1, SkillMessages.FLASH, null),
    HEAL(2, SkillMessages.HEAL, null),
    MINE_BURST(3, SkillMessages.MINE_BURST, null)
    ;

}