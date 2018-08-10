package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.Manipulator
import org.bukkit.Location

/**
 * @author tar0ss
 */
class AfkLocation : Manipulator<AfkLocation, PlayerCache> {

    lateinit var lastLocation: Location

    override fun from(cache: Cache<PlayerCache>): AfkLocation? {
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return true
    }

    fun saveLocation(location: Location) {
        lastLocation = location
    }

    fun getLocation() = lastLocation

}