package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.Manipulator

/**
 * @author tar0ss
 */
class Teleporter : Manipulator<Teleporter, PlayerCache> {

    override fun from(cache: Cache<PlayerCache>): Teleporter? {
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return true
    }

}