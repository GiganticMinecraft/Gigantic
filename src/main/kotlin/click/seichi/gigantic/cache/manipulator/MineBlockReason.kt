package click.seichi.gigantic.cache.manipulator

/**
 * @author tar0ss
 */
enum class MineBlockReason(val id: Int) {
    GENERAL(0),
    DEATH_PENALTY(2),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()

        fun findById(id: Int) = idMap[id]
    }
}