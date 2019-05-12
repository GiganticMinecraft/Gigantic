package click.seichi.gigantic.sidebar.bars

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.extension.getWrappedExp
import click.seichi.gigantic.extension.mana
import click.seichi.gigantic.extension.wrappedLevel
import click.seichi.gigantic.player.Defaults
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
        val map = mutableMapOf<SideBarRow, String>()

        map[SideBarRow.ONE] = "${ChatColor.GREEN}${ChatColor.BOLD} レベル : " +
                "${ChatColor.WHITE}${info.level}"

        map[SideBarRow.TWO] = "${Defaults.SIDEBAR_HIDE_COLOR}_"

        map[SideBarRow.THREE] = "${ChatColor.GREEN}${ChatColor.BOLD}通常破壊: " +
                if (info.mineBlockPerMinute >= 99999999.toBigDecimal()) "${ChatColor.RED}測定不能"
                else "${ChatColor.WHITE}${info.mineBlockPerMinute.setScale(0, RoundingMode.HALF_UP)}/分"

        if (Achievement.SPELL_MULTI_BREAK.isGranted(player))
            map[SideBarRow.FOUR] = "${ChatColor.DARK_AQUA}${ChatColor.BOLD}魔法破壊: " +
                    if (info.multiBlockPerMinute >= 99999999.toBigDecimal()) "${ChatColor.RED}測定不能"
                    else "${ChatColor.WHITE}${info.multiBlockPerMinute.setScale(0, RoundingMode.HALF_UP)}/分"

        if (Achievement.FIRST_RELIC.isGranted(player))
            map[SideBarRow.FIVE] = "${ChatColor.GOLD}${ChatColor.BOLD}レリック: " +
                    if (info.relicBonusPerMinute >= (99999.99).toBigDecimal()) "${ChatColor.RED}測定不能"
                    else "${ChatColor.WHITE}${info.relicBonusPerMinute.setScale(2, RoundingMode.HALF_UP)}/分"

        map[SideBarRow.SIX] = "${Defaults.SIDEBAR_HIDE_COLOR}__"

        if (Achievement.MANA_STONE.isGranted(player))
            map[SideBarRow.SEVEN] = "${ChatColor.AQUA}${ChatColor.BOLD}  マナ  : " +
                    if (info.manaPerMinute >= (99999.99).toBigDecimal()) "${ChatColor.RED}測定不能"
                    else "${ChatColor.WHITE}${info.manaPerMinute.setScale(2, RoundingMode.HALF_UP)}/分"

        if (Achievement.MANA_STONE.isGranted(player))
            map[SideBarRow.THIRTEEN] = "${Defaults.SIDEBAR_HIDE_COLOR}___"

        map[SideBarRow.FOURTEEN] = "${ChatColor.YELLOW}" +
                "       seichi.click       "

        return map
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
                            player.mana
                    )
            )
        }

        private class Record(
                val mineBlock: BigDecimal,
                val multiBlock: BigDecimal,
                val relicBonus: BigDecimal,
                val mana: BigDecimal
        ) {
            val createdAt: DateTime = DateTime.now()
        }
    }
}