package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.skill.Skills
import click.seichi.gigantic.skill.timer.SimpleSkillTimer

/**
 * @author tar0ss
 */
class Flash : SimpleSkillTimer(Skills.FLASH), Manipulator<Flash, PlayerCache> {

    override fun from(cache: Cache<PlayerCache>): Flash? {
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return true
    }

}