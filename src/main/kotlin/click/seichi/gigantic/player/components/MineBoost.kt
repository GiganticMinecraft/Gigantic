package click.seichi.gigantic.player.components

/**
 * @author tar0ss
 */
class MineBoost {
    companion object {
        const val MAX_BOOST = 122
    }

    var current: Int = 0
        private set

    fun update(nextAmplifier: Int) {
        current = nextAmplifier.coerceAtMost(MAX_BOOST)
    }
}
