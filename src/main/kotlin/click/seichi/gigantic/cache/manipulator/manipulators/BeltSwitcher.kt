package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.player.Defaults

/**
 * @author tar0ss
 */
class BeltSwitcher : Manipulator<BeltSwitcher, PlayerCache> {

    private val map = mutableMapOf<Belt, Boolean>()

    private val unlockMap = mutableMapOf<Belt, Boolean>()

    lateinit var current: Belt

    companion object {
        val beltList = Belt.values().toList()
    }

    override fun from(cache: Cache<PlayerCache>): BeltSwitcher? {
        current = cache.getOrPut(Keys.BELT)
        map.clear()
        Belt.values().forEach {
            map[it] = cache.getOrPut(Keys.BELT_TOGGLE_MAP[it] ?: return null)
            unlockMap[it] = cache.getOrPut(Keys.BELT_UNLOCK_MAP[it] ?: return null)
        }
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        cache.offer(Keys.BELT, current)
        Belt.values().forEach {
            cache.offer(Keys.BELT_TOGGLE_MAP[it] ?: return false, map[it] ?: return false)
            cache.offer(Keys.BELT_UNLOCK_MAP[it] ?: return false, unlockMap[it] ?: return false)
        }
        return true
    }

    private fun canSwitch(belt: Belt) = (map[belt] ?: false) && (unlockMap[belt] ?: false)

    fun switch(): Belt {
        current = nextBelt()
        return current
    }

    private fun nextBelt(): Belt {
        val currentIndex = beltList.indexOf(current)
        return beltList.asSequence().filterIndexed { index, belt ->
            index > currentIndex && canSwitch(belt)
        }.firstOrNull() ?: beltList.firstOrNull { belt ->
            canSwitch(belt)
        } ?: Belt.findById(Defaults.BELT_ID)!!
    }

}