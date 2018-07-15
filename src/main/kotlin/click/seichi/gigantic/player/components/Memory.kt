package click.seichi.gigantic.player.components

import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.language.messages.PlayerMessages.MEMORY_SIDEBAR
import click.seichi.gigantic.will.Will
import org.bukkit.entity.Player

class Memory(private val currentMap: MutableMap<Will, Long>) {

    fun add(will: Will, n: Long): Long {
        val next = (currentMap[will] ?: 0L) + n
        currentMap[will] = next
        return next
    }

    fun get(will: Will) = currentMap[will] ?: 0L

    fun copyMap() = currentMap.toMap()

    fun display(player: Player) {
        val gPlayer = player.gPlayer ?: return
        val aptitude = gPlayer.aptitude
        val willMap = Will.values().filter { aptitude.has(it) }.map { it to (currentMap[it] ?: 0L) }.toMap()
        MEMORY_SIDEBAR(willMap).sendTo(player)
    }
}
