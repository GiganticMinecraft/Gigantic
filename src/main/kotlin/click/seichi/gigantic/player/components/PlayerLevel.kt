package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.PlayerLevelConfig.LEVEL_MAP
import click.seichi.gigantic.config.PlayerLevelConfig.MAX
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.player.PlayerComponent

class PlayerLevel : PlayerComponent {

    enum class ExpProducer(private val producing: (PlayerContainer) -> Long) {
        MINE_BLOCK(
                { container ->
                    container.status.mineBlock.get(MineBlockReason.GENERAL)
                }
        )
        ;

        fun produce(playerContainer: PlayerContainer) = producing(playerContainer)
    }

    private val expProduceCalculating: (PlayerContainer) -> Long = { container ->
        ExpProducer.values().map { it.produce(container) }.sum()
    }


    var current: Int = 0
        private set

    private fun calcLevel(exp: Long) =
            (1..MAX).firstOrNull {
                !canLevelUp(it + 1, exp)
            } ?: MAX

    private fun canLevelUp(nextLevel: Int, exp: Long) =
            exp > LEVEL_MAP[nextLevel] ?: Long.MAX_VALUE

    fun update(playerContainer: PlayerContainer) {
        val exp = expProduceCalculating(playerContainer)
        while (canLevelUp(current + 1, exp)) {
            current++
            if (current >= MAX) {
                current = MAX
                break
            }
        }
    }

    override fun onInit(playerContainer: PlayerContainer) {
        update(playerContainer)
    }


}
