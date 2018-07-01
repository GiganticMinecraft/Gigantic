package click.seichi.gigantic.skill

import click.seichi.gigantic.message.LocalizedString
import click.seichi.gigantic.message.lang.skill.SkillLang

/**
 * @author tar0ss
 */
enum class SkillState(
        val canFire: Boolean,
        val localizedName: LocalizedString = if (canFire) SkillLang.ACTIVATE else SkillLang.NOT_ACTIVATE
) {
    FAILED_TO_LOAD(false, SkillLang.FAILED_TO_LOAD),
    ACTIVATE(true),
    FIRE_COMPLETED(true),
    NOT_ACTIVATE(false, SkillLang.NOT_ACTIVATE),
    LOCKED(false, SkillLang.LOCKED),
    COOLDOWN(false, SkillLang.COOLDOWN),
    NOT_SURVIVAL(false, SkillLang.NOT_SURVIVAL),
    NOT_SEICHI_WORLD(false, SkillLang.NOT_SEICHI_WORLD),
    FLYING(false, SkillLang.FLYING),
    NOT_SEICHI_TOOL(false, SkillLang.NOT_SEICHI_TOOL),
    UPPER_BLOCK(false, SkillLang.UPPER_BLOCK),
    FOOTHOLD_BLOCK(false, SkillLang.FOOTHOLD_BLOCK),
    NO_BLOCK(false, SkillLang.NO_BLOCK),
    NO_DURABILITY(false, SkillLang.NO_DURABILITY),
    NO_MANA(false, SkillLang.NO_MANA),
    ;
}