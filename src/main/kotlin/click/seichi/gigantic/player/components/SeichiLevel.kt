package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.SeichiLevelConfig
import click.seichi.gigantic.database.dao.UserMineBlock
import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.player.MineBlockReason

class SeichiLevel(mineBlockMap: Map<MineBlockReason, UserMineBlock>) {
    companion object {
        private val max = SeichiLevelConfig.MAX
    }

    var current = calcLevel(mineBlockMap[MineBlockReason.GENERAL]!!.mineBlock)

    private fun calcLevel(mineBlock: Long) =
            (1..max).firstOrNull {
                !canLevelUp(it + 1, mineBlock)
            } ?: max

    private fun canLevelUp(nextLevel: Int, mineBlock: Long) =
            mineBlock > SeichiLevelConfig.MINEBLOCK_MAP[nextLevel] ?: Long.MAX_VALUE

    fun update(gPlayer: GiganticPlayer) {
        val mineBlock = gPlayer.status.mineBlock.get()
        while (canLevelUp(current + 1, mineBlock)) {
            current++
            if (current >= max) {
                current = max
                break
            }
        }
    }
}
