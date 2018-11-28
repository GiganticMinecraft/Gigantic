package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.tool.Tool

/**
 * @author tar0ss
 */
class ToolSwitcher : Manipulator<ToolSwitcher, PlayerCache> {

    private val map = mutableMapOf<Tool, Boolean>()

    private val unlockMap = mutableMapOf<Tool, Boolean>()

    lateinit var current: Tool

    companion object {
        val toolList = Tool.values().toList()
    }

    override fun from(cache: Cache<PlayerCache>): ToolSwitcher? {
        current = cache.getOrPut(Keys.TOOL)
        map.clear()
        Tool.values().forEach {
            map[it] = cache.getOrPut(Keys.TOOL_TOGGLE_MAP[it] ?: return null)
            unlockMap[it] = cache.getOrPut(Keys.TOOL_UNLOCK_MAP[it] ?: return null)
        }
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        cache.offer(Keys.TOOL, current)
        Tool.values().forEach {
            cache.offer(Keys.TOOL_TOGGLE_MAP[it] ?: return false, map[it] ?: return false)
            cache.offer(Keys.TOOL_UNLOCK_MAP[it] ?: return false, unlockMap[it] ?: return false)
        }
        return true
    }

    fun canSwitch(belt: Tool) = (map[belt] ?: false) && (unlockMap[belt] ?: false)

    fun setCanSwitch(belt: Tool, canSwitch: Boolean) {
        if (unlockMap[belt] == true)
            map[belt] = canSwitch
    }

    fun unlock(belt: Tool) {
        unlockMap[belt] = true
    }

    fun switch(): Tool {
        current = nextTool()
        return current
    }

    fun nextTool(): Tool {
        val currentIndex = toolList.indexOf(current)
        return toolList.asSequence().filterIndexed { index, belt ->
            index > currentIndex && canSwitch(belt)
        }.firstOrNull() ?: toolList.firstOrNull { belt ->
            canSwitch(belt)
        } ?: Tool.findById(Defaults.TOOL_ID)!!
    }

}