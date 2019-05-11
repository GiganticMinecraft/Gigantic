package click.seichi.gigantic.sidebar.bars

import click.seichi.gigantic.extension.combo
import click.seichi.gigantic.extension.getWrappedExp
import click.seichi.gigantic.extension.mana
import click.seichi.gigantic.extension.wrappedLevel
import click.seichi.gigantic.player.ExpReason
import click.seichi.gigantic.sidebar.SideBar
import click.seichi.gigantic.util.SideBarRow
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.joda.time.DateTime
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * @author tar0ss
 */
object MainBar : SideBar("info") {

    private val infoMap = mutableMapOf<UUID, PlayerInformation>()

    /**
     * [click.seichi.gigantic.player.Defaults.SIDEBAR_RECORD_INTERVAL]秒ごとに呼ばれる
     * @param player 記録するプレイヤー
     */
    fun update(player: Player) {
        infoMap.getOrPut(player.uniqueId) {
            PlayerInformation(player)
        }.run {
            refresh()
            record(player)
        }

        // 表示
        tryShow(player, isForced = false)
    }

    override fun canShow(player: Player): Boolean {
        return true
    }

    override fun getTitle(player: Player): String {
        return "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                "整地鯖(春)"
    }

    override fun getMessageMap(player: Player): Map<SideBarRow, String> {
        val info = infoMap[player.uniqueId] ?: return mapOf()
        return mapOf(
                // 整地レベル
                SideBarRow.ONE to "${ChatColor.GREEN}${ChatColor.BOLD} レベル : " +
                        "${ChatColor.WHITE}${info.level}",
                SideBarRow.TWO to "${ChatColor.DARK_GRAY}_",
                // 分間通常破壊量
                SideBarRow.THREE to "${ChatColor.GREEN}${ChatColor.BOLD}通常破壊: " +
                        "${ChatColor.WHITE}${info.mineBlockPerMinute.setScale(0, RoundingMode.HALF_UP)}/分",
                // 分間魔法破壊量
                SideBarRow.FOUR to "${ChatColor.DARK_AQUA}${ChatColor.BOLD}魔法破壊: " +
                        "${ChatColor.WHITE}${info.multiBlockPerMinute.setScale(0, RoundingMode.HALF_UP)}/分",
                // 分間レリックボーナス量
                SideBarRow.FIVE to "${ChatColor.YELLOW}${ChatColor.BOLD}レリック: " +
                        "${ChatColor.WHITE}${info.relicBonusPerMinute.setScale(2, RoundingMode.HALF_UP)}/分",
                // 次のレベルまでの到達予想時間
                SideBarRow.SIX to "${ChatColor.DARK_GRAY}__",
                // 分間マナ増減
                SideBarRow.SEVEN to "${ChatColor.AQUA}${ChatColor.BOLD}  マナ  : " +
                        "${ChatColor.WHITE}${info.manaPerMinute.setScale(2, RoundingMode.HALF_UP)}/分",
                // 分間コンボ数
                SideBarRow.EIGHT to "${ChatColor.GOLD}${ChatColor.BOLD} コンボ : " +
                        "${ChatColor.WHITE}${info.comboPerMinute}/分",
                // 次の採掘速度アップまでの到達予想時間
                SideBarRow.NINE to "${ChatColor.DARK_GRAY}___",
                SideBarRow.FOURTEEN to "${ChatColor.YELLOW}" +
                        "     seichi.click"
        )
    }

    private class PlayerInformation(player: Player) {

        var level = player.wrappedLevel
            private set

        private val recordQueue: Deque<Record> = LinkedList<Record>()

        val mineBlockPerMinute
            get() = when (recordQueue.size) {
                0 -> BigDecimal.ZERO
                1 -> recordQueue.peekFirst().mineBlock
                else -> recordQueue.peekFirst().mineBlock - recordQueue.peekLast().mineBlock
            }
        val multiBlockPerMinute
            get() = when (recordQueue.size) {
                0 -> BigDecimal.ZERO
                1 -> recordQueue.peekFirst().multiBlock
                else -> recordQueue.peekFirst().multiBlock - recordQueue.peekLast().multiBlock
            }
        val relicBonusPerMinute
            get() = when (recordQueue.size) {
                0 -> BigDecimal.ZERO
                1 -> recordQueue.peekFirst().relicBonus
                else -> recordQueue.peekFirst().relicBonus - recordQueue.peekLast().relicBonus
            }
        val manaPerMinute
            get() = when (recordQueue.size) {
                0 -> BigDecimal.ZERO
                1 -> recordQueue.peekFirst().mana
                else -> recordQueue.peekFirst().mana - recordQueue.peekLast().mana
            }
        val comboPerMinute
            get() = when (recordQueue.size) {
                0 -> 0L
                1 -> recordQueue.peekFirst().combo
                else -> recordQueue.peekFirst().combo - recordQueue.peekLast().combo
            }

        // 必要のないデータを削除
        fun refresh() {
            // 1分前よりも前のデータを全て削除
            recordQueue.removeIf {
                it.createdAt.isBefore(DateTime.now().minusMinutes(1).minusSeconds(1))
            }
        }

        // 個人データを収集し保存
        fun record(player: Player) {
            level = player.wrappedLevel

            recordQueue.addFirst(
                    Record(
                            player.getWrappedExp(ExpReason.MINE_BLOCK),
                            player.getWrappedExp(ExpReason.SPELL_MULTI_BREAK),
                            player.getWrappedExp(ExpReason.RELIC_BONUS),
                            player.mana,
                            player.combo
                    )
            )
        }

        private class Record(
                val mineBlock: BigDecimal,
                val multiBlock: BigDecimal,
                val relicBonus: BigDecimal,
                val mana: BigDecimal,
                val combo: Long
        ) {
            val createdAt: DateTime = DateTime.now()
        }
    }
}