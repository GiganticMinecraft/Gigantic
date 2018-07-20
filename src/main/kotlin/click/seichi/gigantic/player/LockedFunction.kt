package click.seichi.gigantic.player

/**
 * @author tar0ss
 */
enum class LockedFunction(
        private val isUnlocking: (GiganticPlayer) -> Boolean
) {
    MINE_BURST(
            { it.level.current >= 5 }
    ),
    MANA(
            { it.level.current >= 10 }
    ),
    ;

    fun isUnlocked(gPlayer: GiganticPlayer) = isUnlocking(gPlayer)
}