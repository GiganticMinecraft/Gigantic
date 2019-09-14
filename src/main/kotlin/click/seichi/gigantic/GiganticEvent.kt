package click.seichi.gigantic

import click.seichi.gigantic.message.LinedChatMessage
import click.seichi.gigantic.message.messages.LoginMessages
import org.bukkit.entity.Player
import org.joda.time.DateTime

/**
 * 様々なイベントの期間をここで決める
 * @author tar0ss
 */
enum class GiganticEvent(
        val loginMessage: LinedChatMessage
) {
    // 桜の意志出現イベント
    SAKURA(LoginMessages.EVENT_SAKURA) {
        val from = DateTime("2019-03-01T00:00:00+09:00")
        val to = DateTime("2019-06-01T00:00:00+09:00")
        override fun isActive(): Boolean {
            val now = DateTime.now()
            val year = now.year
            return now in from.withYear(year)..to.withYear(year)
        }
    },
    // 澪の意志出現イベント
    MIO(LoginMessages.EVENT_MIO) {
        val from = DateTime("2019-06-01T00:00:00+09:00")
        val to = DateTime("2019-09-01T00:00:00+09:00")
        override fun isActive(): Boolean {
            val now = DateTime.now()
            val year = now.year
            return now in from.withYear(year)..to.withYear(year)
        }
    },
    // 楓の意志出現イベント
    KAEDE(LoginMessages.EVENT_KAEDE) {
        val from = DateTime("2019-09-01T00:00:00+09:00")
        val to = DateTime("2019-12-01T00:00:00+09:00")
        override fun isActive(): Boolean {
            val now = DateTime.now()
            val year = now.year
            return now in from.withYear(year)..to.withYear(year)
        }
    },
    // 玲の意志出現イベント
    REI(LoginMessages.EVENT_REI) {
        val from = DateTime("2019-12-01T00:00:00+09:00")
        val lastDayTime = DateTime("2020-01-01T00:00:00+09:00")
        val to = DateTime("2020-03-01T00:00:00+09:00")
        override fun isActive(): Boolean {
            val now = DateTime.now()
            val year = now.year
            return now in from.withYear(year)..lastDayTime.withYear(year + 1) ||
                    now in lastDayTime.withYear(year)..to.withYear(year)
        }
    },
    // JMS1位記念イベント
    JMS_KING(LoginMessages.EVENT_JMS_KING) {
        val from = DateTime("2019-03-01T18:00:00+09:00")
        val to = DateTime("2019-04-01T00:00:00+09:00")
        override fun isActive(): Boolean {
            val now = DateTime.now()
            return now in from..to
        }
    }
    ;

    abstract fun isActive(): Boolean

    companion object {
        fun trySendLoginMessageTo(player: Player) {
            values().filter { if (Gigantic.IS_DEBUG) true else it.isActive() }
                    .forEach { it.loginMessage.sendTo(player) }
        }
    }

}