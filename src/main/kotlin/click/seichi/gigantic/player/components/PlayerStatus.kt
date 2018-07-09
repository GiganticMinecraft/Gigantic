package click.seichi.gigantic.player.components

import click.seichi.gigantic.player.Remotable

class PlayerStatus : Remotable {

    val mana = Mana()

    val abilityPoint = AbilityPoint()

    val mineBlock = MineBlock()

    val seichiLevel = PlayerLevel()
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
