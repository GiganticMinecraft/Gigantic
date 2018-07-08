package click.seichi.gigantic.skill

import click.seichi.gigantic.language.LocalizedText
import click.seichi.gigantic.language.messages.SkillMessages

/**
 * @author tar0ss
 */
enum class SkillState(
        val canFire: Boolean,
        val LocalizedText: LocalizedText = if (canFire) SkillMessages.ACTIVATE else SkillMessages.NOT_ACTIVATE
) {
    ACTIVATE(true),
    FIRE_COMPLETED(true),
    NOT_ACTIVATE(false, SkillMessages.NOT_ACTIVATE),
    LOCKED(false, SkillMessages.LOCKED),
    COOLDOWN(false, SkillMessages.COOLDOWN),
    NOT_SURVIVAL(false, SkillMessages.NOT_SURVIVAL),
    NOT_SEICHI_WORLD(false, SkillMessages.NOT_SEICHI_WORLD),
    FLYING(false, SkillMessages.FLYING),
    NOT_SEICHI_TOOL(false, SkillMessages.NOT_SEICHI_TOOL),
    UPPER_BLOCK(false, SkillMessages.UPPER_BLOCK),
    FOOTHOLD_BLOCK(false, SkillMessages.FOOTHOLD_BLOCK),
    NO_BLOCK(false, SkillMessages.NO_BLOCK),
    NO_DURABILITY(false, SkillMessages.NO_DURABILITY),
    NO_MANA(false, SkillMessages.NO_MANA),
    ;
}