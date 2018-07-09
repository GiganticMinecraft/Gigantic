package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.PlayerLevelConfig.LEVEL_MAP
import click.seichi.gigantic.config.PlayerLevelConfig.MAX
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.player.MineBlockReason

class PlayerLevel {

    enum class ExpProducer(private val producing: (GiganticPlayer) -> Long) {
        MINE_BLOCK(
                { gPlayer ->
                    gPlayer.mineBlock.get(MineBlockReason.GENERAL)
                }
        )
        ;

        fun produce(gPlayer: GiganticPlayer) = producing(gPlayer)
    }

    private val expProduceCalculating: (GiganticPlayer) -> Long = { gPlayer ->
        ExpProducer.values().map { it.produce(gPlayer) }.sum()
    }


    var current: Int = 1
        private set

    private fun calcLevel(exp: Long) =
            (1..MAX).firstOrNull {
                !canLevelUp(it + 1, exp)
            } ?: MAX

    private fun canLevelUp(nextLevel: Int, exp: Long) =
            exp > LEVEL_MAP[nextLevel] ?: Long.MAX_VALUE

    fun update(gPlayer: GiganticPlayer) {
        val exp = expProduceCalculating(gPlayer)
        while (canLevelUp(current + 1, exp)) {
            current++
            if (current >= MAX) {
                current = MAX
                break
            }
        }
    }

}
