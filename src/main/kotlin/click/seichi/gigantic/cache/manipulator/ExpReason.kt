package click.seichi.gigantic.cache.manipulator

/**
 * @author tar0ss
 */
enum class ExpReason(val id: Int) {
    MINE_BLOCK(0),
    COMBO_BONUS(1),
    DEATH_PENALTY(2),
    DEBUG(3),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()

        fun findById(id: Int) = idMap[id]
    }
}