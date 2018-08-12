package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.Manipulator

/**
 * @author tar0ss
 */
class MenuData : Manipulator<MenuData, PlayerCache> {

    var page: Int = 1

    override fun from(cache: Cache<PlayerCache>): MenuData? {
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return true
    }
}