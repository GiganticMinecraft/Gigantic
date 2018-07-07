package click.seichi.gigantic.player

/**
 * @author tar0ss
 */
enum class MineBlockReason(val id:Int) {
        GENERAL(0),
        EXPLOSION(1)
        ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()

        fun findById(id:Int) = idMap[id]
    }
}