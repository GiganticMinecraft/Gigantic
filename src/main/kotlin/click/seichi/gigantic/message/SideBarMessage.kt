package click.seichi.gigantic.message

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.util.SideBarRow
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.ScoreboardManager

/**
 * @author unicroak
 * @author tar0ss
 */
class SideBarMessage(
        private val objectiveName: String,
        private val title: LocalizedText,
        private val messageMap: Map<SideBarRow, LocalizedText>,
        private val isForced: Boolean
) : Message {

    companion object {
        private val scoreboardManager: ScoreboardManager
            get() = Bukkit.getScoreboardManager()
    }

    override fun sendTo(player: Player) {
        player.scoreboard?.let {
            if (!isForced && it.getObjective(DisplaySlot.SIDEBAR).name != objectiveName) return
            else it.objectives.forEach { it.unregister() }
        }

        val scoreboard = scoreboardManager.newScoreboard
        val objective = scoreboard.registerNewObjective(objectiveName, "dummy", title.asSafety(player.wrappedLocale))
        player.scoreboard = scoreboard

        objective.displaySlot = DisplaySlot.SIDEBAR
        messageMap.forEach { row, text -> objective.getScore(text.asSafety(player.wrappedLocale)).score = row.toInt }
    }

}