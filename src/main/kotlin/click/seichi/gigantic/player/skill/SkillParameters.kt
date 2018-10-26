package click.seichi.gigantic.player.skill

import org.bukkit.block.BlockFace

/**
 * @author tar0ss
 *
 * TODO skill.ymlに移動
 */
object SkillParameters {

    const val MINE_BURST_DURATION = 5L

    const val MINE_BURST_COOLTIME = 60L

    const val MINE_BURST_KEY = "3"

    const val FLASH_COOLTIME = 15L

    const val FLASH_KEY = "2"

    const val SWITCH_KEY = "f"

    const val SWITCH_SETTING_KEY = "9"

    const val HEAL_AMOUNT_PERCENT = 5

    const val HEAL_PROBABILITY_PERCENT = 20

    const val TERRA_DRAIN_LOG_HEAL_PERCENT = 3.0

    const val TERRA_DRAIN_LEAVES_HEAL_PERCENT = 0.3

    val TERRA_DRAIN_FACE_SET = BlockFace.values().toSet().minus(BlockFace.SELF)
}