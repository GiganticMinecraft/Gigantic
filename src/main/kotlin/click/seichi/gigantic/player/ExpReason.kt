package click.seichi.gigantic.player

/**
 * @author tar0ss
 */
enum class ExpReason(val id: Int) {
    // 通常破壊のみ
    MINE_BLOCK(0),
    // TODO 使用用途なし
    COMBO_BONUS(1),
    DEATH_PENALTY(2),
    DEBUG(3),
    SPELL_MULTI_BREAK(4),
    RELIC_BONUS(5),
    STRIP_MINE_BONUS(6),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()

        fun findById(id: Int) = idMap[id]
    }
}