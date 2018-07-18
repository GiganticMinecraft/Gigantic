package click.seichi.gigantic.player.components

/**
 * @author tar0ss
 */
class MineCombo {

    var currentCombo: Long = 0L
        private set

    private var lastComboTime = System.currentTimeMillis()

    companion object {
        const val COMBO_CONTINUATION_SECONDS = 3L
    }

    fun combo(count: Long): Long {
        if (canContinue()) {
            currentCombo += count
        } else {
            currentCombo = count
        }
        updateComboTime()
        return currentCombo
    }

    private fun updateComboTime() {
        lastComboTime = System.currentTimeMillis()
    }

    private fun canContinue(): Boolean {
        val now = System.currentTimeMillis()
        val diff = now - lastComboTime
        return COMBO_CONTINUATION_SECONDS > diff.div(1000)
    }
}