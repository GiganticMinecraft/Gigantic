package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.timer.LingeringTimer

/**
 * @author tar0ss
 */
class MineBurst : LingeringTimer(), Manipulator<MineBurst, PlayerCache> {

    override fun from(cache: Cache<PlayerCache>): MineBurst? {
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return true
    }
}