package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.config.Config
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

    private fun canSwitch(tool: Tool) = Config.DEBUG_MODE || ((map[tool] ?: false) && (unlockMap[tool] ?: false))

    fun switch(): Tool {
        current = nextTool()
        return current
    }

    private fun nextTool(): Tool {
        val currentIndex = toolList.indexOf(current)
        return toolList.asSequence().filterIndexed { index, tool ->
            index > currentIndex && canSwitch(tool)
        }.firstOrNull() ?: toolList.firstOrNull { tool ->
            canSwitch(tool)
        } ?: Tool.findById(Defaults.TOOL_ID)!!
    }

}