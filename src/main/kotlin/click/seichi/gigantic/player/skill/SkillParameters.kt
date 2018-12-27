package click.seichi.gigantic.player.skill

/**
 * @author tar0ss
 */
object SkillParameters {

    const val MINE_BURST_DURATION = 5L

    const val MINE_BURST_COOLTIME = 30L

    const val FLASH_COOLTIME = 3L

    const val HEAL_AMOUNT_PERCENT = 5

    const val HEAL_PROBABILITY_PERCENT = 20

    const val COMBO_CONTINUATION_SECONDS = 3L

    const val COMBO_DECREASE_INTERVAL = 3L

    fun calcComboRank(combo: Long) = when (combo) {
        in 0..9 -> 1
        in 10..29 -> 2
        in 30..69 -> 3
        in 70..149 -> 4
        in 150..309 -> 5
        in 310..629 -> 6
        in 630..1269 -> 7
        in 1270..2549 -> 8
        in 2550..5109 -> 9
        in 5110..10229 -> 10
        else -> 11
    }

}