package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import kotlin.properties.Delegates

/**
 * @author tar0ss
 */
class VotePoint : Manipulator<VotePoint, PlayerCache> {

    var current: Int by Delegates.notNull()
        private set

    override fun from(cache: Cache<PlayerCache>): VotePoint? {
        current = cache.getOrPut(Keys.VOTE_POINT)
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return cache.offer(Keys.VOTE_POINT, current)
    }


}