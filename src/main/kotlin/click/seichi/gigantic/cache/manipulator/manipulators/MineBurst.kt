package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.skill.LingeringSkillTimer
import click.seichi.gigantic.skill.SkillParameters

/**
 * @author tar0ss
 */
class MineBurst : LingeringSkillTimer(), Manipulator<MineBurst, PlayerCache> {
    override val duration: Long
        get() = SkillParameters.MINE_BURST_DURATION

    override val coolTime: Long
        get() = SkillParameters.MINE_BURST_COOLTIME

    override fun from(cache: Cache<PlayerCache>): MineBurst? {
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return true
    }
}