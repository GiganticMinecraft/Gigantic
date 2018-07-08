package click.seichi.gigantic.config

/**
 * @author tar0ss
 */
sealed class BreakSkillLevelConfig {
    object explosionLevelConfig : MineBlockBasedLevelConfig("explosion_level")
}