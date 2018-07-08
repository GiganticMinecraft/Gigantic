package click.seichi.gigantic.player.components

import click.seichi.gigantic.player.Remotable

class PlayerStatus : Remotable {

    val mana = Mana()

    val mineBlock = MineBlock()

    val seichiLevel = SeichiLevel()
    // TODO implements
    val explosionLevel = 3

    val memory = Memory()

    val aptitude = WillAptitude()

    override fun getRemotableComponents() = listOf(
            mana,
            mineBlock,
            seichiLevel,
            memory,
            aptitude
    )
}
