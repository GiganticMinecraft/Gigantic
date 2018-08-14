package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.timer.SimpleTimer

/**
 * @author tar0ss
 */
class Explosion : SimpleTimer(), Manipulator<Explosion, PlayerCache> {

    override fun from(cache: Cache<PlayerCache>): Explosion? {
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return true
    }

}