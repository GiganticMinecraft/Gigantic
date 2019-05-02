package click.seichi.gigantic.sidebar

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.util.SideBarRow
import org.bukkit.entity.Player
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
abstract class Logger(
        objectiveName: String,
        // ログを保存する時間（分）
        private val saveMinutes: Int = 1,
        // ログの表示数(14以下で設定すること)
        private val maxLog: Int = 14
) : SideBar(objectiveName) {

    abstract fun getDeque(player: Player): Deque<Log>

    fun log(player: Player, text: String) {
        val deque = getDeque(player)
        val latestId = if (deque.isEmpty()) 0 else deque.peekFirst().id
        val newId = (latestId % maxLog) + 1
        val newLog = Log(newId, text)

        deque.addFirst(newLog)

        // 上限設定
        if (deque.size > maxLog) {
            deque.removeLast()
        }
        update(player)
    }

    private fun update(player: Player) {
        val now = DateTime.now()
        getDeque(player).removeIf { isOld(now, it) }
        if (player.getOrPut(Keys.SIDEBAR_TYPE) == type)
            show(player)
    }

    private fun isOld(now: DateTime, log: Log): Boolean {
        val diff = now.millis - log.createdAt.millis
        val diffMinutes = diff / 1000 / 60
        return diffMinutes > saveMinutes
    }

    override fun getMessageMap(player: Player): Map<SideBarRow, String> {
        return getDeque(player).mapIndexed { index, log ->
            SideBarRow.getByNumber(index + 1) to log.text
        }.toMap()
    }
}