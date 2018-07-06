package click.seichi.gigantic.skill.dispather

import click.seichi.gigantic.player.GiganticPlayer
import click.seichi.gigantic.skill.Skill

/**
 * @author tar0ss
 */
interface SkillDispatcher {

    val skill: Skill

    val gPlayer: GiganticPlayer

    fun dispatch(): Boolean
}