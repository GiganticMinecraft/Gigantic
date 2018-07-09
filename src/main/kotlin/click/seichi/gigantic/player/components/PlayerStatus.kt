package click.seichi.gigantic.player.components

import click.seichi.gigantic.player.PlayerComponent

class PlayerStatus : PlayerComponent {

    val mana = Mana()

    val abilityPoint = AbilityPoint()

    val mineBlock = MineBlock()

    val level = PlayerLevel()
    // TODO implements
    val explosionLevel = 3

    val memory = Memory()

    val aptitude = WillAptitude()

    override fun getComponents() = listOf(
            mana,
            mineBlock,
            level,
            memory,
            aptitude
    )
}
