package click.seichi.gigantic.skill

import org.bukkit.block.BlockFace

/**
 * @author tar0ss
 *
 * TODO skill.ymlに移動
 */
object SkillParameters {

    const val TERRA_DRAIN_MAX_RADIUS = 5

    val TERRA_DRAIN_FACE_SET = setOf(
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.WEST,
            BlockFace.SOUTH,
            BlockFace.EAST
    )

    const val TERRA_DRAIN_DELAY = 3L

    const val MINE_BURST_DURATION = 5L

    const val MINE_BURST_COOLTIME = 60L

    const val MINE_BURST_KEY = "3"

    const val FLASH_COOLTIME = 15L

    const val FLASH_KEY = "2"

    const val SWITCH_KEY = "f"

    const val SWITCH_SETTING_KEY = "9"

    const val HEAL_PERCENT = 5

    const val HEAL_PROBABILITY = 0.2

    const val TERRA_DRAIN_LOG_HEAL_PERCENT = 3.0

    const val TERRA_DRAIN_LEAVES_HEAL_PERCENT = 0.3

}