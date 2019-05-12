package click.seichi.gigantic.sidebar

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.runTaskLater
import click.seichi.gigantic.util.SideBarRow
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.ScoreboardManager
import java.util.*

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

    protected abstract fun canShow(player: Player): Boolean

    protected abstract fun getTitle(player: Player): String

    protected abstract fun getMessageMap(player: Player): Map<SideBarRow, String>

    /**
     * @param player プレイヤー
     * @param ticks 指定すると[ticks]tick経過後に消える
     * @param isForced FALSEにするとサイドバーが切り替わらない
     */
    fun tryShow(player: Player, ticks: Long = -1L, isForced: Boolean = true) {
        if (!isForced && this != player.getOrPut(Keys.CURRENT_SIDEBAR)) return

        if (!canShow(player)) return

        player.offer(Keys.CURRENT_SIDEBAR, this)

        // 遅延処理がユニークに実行されるよう保証する
        val uniqueId = UUID.randomUUID()
        player.offer(Keys.SIDEBAR_SHOW_UID, uniqueId)

        if (ticks > 0) {
            // 遅延
            runTaskLater(ticks) {
                if (!player.isValid) return@runTaskLater
                // サイドバーが別の場合は終了
                if (player.getOrPut(Keys.CURRENT_SIDEBAR) != this) return@runTaskLater
                // SHOW_UIDが違う場合は終了
                if (player.getOrPut(Keys.SIDEBAR_SHOW_UID) != uniqueId) return@runTaskLater

                hide(player)
            }
        } else {
            // 継続
            player.offer(Keys.SUSTAINED_SIDEBAR, this)
        }

        show(player)

    }

    private fun show(player: Player) {

        val messageMap = getMessageMap(player)
        var needUpdate = false
        val current = player.scoreboard.getObjective(DisplaySlot.SIDEBAR)

        if (current == null || current.name != objectiveName) {
            needUpdate = true
        } else {
            messageMap.values.forEach { score ->
                if (current.getScore(score).isScoreSet) return@forEach
                needUpdate = true
            }
        }

        if (!needUpdate) return

        val new = scoreboardManager.newScoreboard
        val objective = new.registerNewObjective(objectiveName, "dummy", getTitle(player))

        objective.displaySlot = DisplaySlot.SIDEBAR

        getMessageMap(player).forEach { (row, text) ->
            objective.getScore(text).score = row.toInt
        }

        player.scoreboard = new
    }

    private fun hide(player: Player) {
        val current = player.getOrPut(Keys.CURRENT_SIDEBAR)

        // サイドバーが別の場合は終了
        if (current != this) return

        // 固定バー
        val sustained = player.getOrPut(Keys.SUSTAINED_SIDEBAR)

        if (sustained != this) {
            // 固定バーではないときは固定バーで上書き
            sustained.tryShow(player)
        } else {
            // 固定バーの時は閉じるだけ
            player.scoreboard.getObjective(DisplaySlot.SIDEBAR)?.unregister()
        }

    }

}