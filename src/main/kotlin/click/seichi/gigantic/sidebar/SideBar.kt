package click.seichi.gigantic.sidebar

import click.seichi.gigantic.util.SideBarRow
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.ScoreboardManager

/**
 * @author tar0ss
 */
abstract class SideBar(
        private val objectiveName: String
) {
    companion object {
        private val scoreboardManager: ScoreboardManager
            get() = Bukkit.getScoreboardManager()!!
    }

    abstract val type: SideBarType

    protected abstract fun getTitle(player: Player): String

    protected abstract fun getMessageMap(player: Player): Map<SideBarRow, String>

    fun show(player: Player) {

        val new = scoreboardManager.newScoreboard
        val objective = new.registerNewObjective(objectiveName, "dummy", getTitle(player))

        objective.displaySlot = DisplaySlot.SIDEBAR

        getMessageMap(player).forEach { (row, text) ->
            objective.getScore(text).score = row.toInt
        }

        player.scoreboard = new
    }

}