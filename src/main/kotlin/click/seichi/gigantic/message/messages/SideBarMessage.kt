package click.seichi.gigantic.message.messages

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.LocalizedString
import click.seichi.gigantic.message.MessageArgumentList
import click.seichi.gigantic.message.Transmittable
import click.seichi.gigantic.util.SideBarRow
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.ScoreboardManager

/**
 * @author unicroak
 * @author tar0ss
 */
class SideBarMessage(
        private val objectiveName: String,
        private val title: LocalizedString,
        private val messageMap: Map<SideBarRow, (MessageArgumentList) -> LocalizedString>,
        private val isForced: Boolean
) : Transmittable {

    companion object {
        protected val scoreboardManager: ScoreboardManager = Bukkit.getScoreboardManager()
    }

    private lateinit var scoreboard: Scoreboard

    private lateinit var objective: Objective

    override fun sendTo(receiver: Player, vararg arguments: Any) {
        receiver.scoreboard?.let {
            if (!isForced && it.getObjective(DisplaySlot.SIDEBAR).name != objectiveName) return else {
                it.objectives.forEach { it.unregister() }
            }
        }
        create()
        receiver.scoreboard = scoreboard
        objective.displayName = title.asSafety(receiver.wrappedLocale)
        objective.displaySlot = DisplaySlot.SIDEBAR
        messageMap.forEach { row, message ->
            objective.getScore(message(MessageArgumentList(*arguments)).asSafety(receiver.wrappedLocale)).score = row.toInt
        }
    }

    fun create() {
        scoreboard = scoreboardManager.newScoreboard
        objective = scoreboard.registerNewObjective(objectiveName, "dummy")
    }

}