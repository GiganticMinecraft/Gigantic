package click.seichi.gigantic.sidebar.bars

import click.seichi.gigantic.extension.wrappedLevel
import click.seichi.gigantic.sidebar.SideBar
import click.seichi.gigantic.sidebar.SideBarType
import click.seichi.gigantic.util.SideBarRow
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class InfoBar : SideBar("info") {
    override val type = SideBarType.INFO

    override fun getTitle(player: Player): String {
        return "Information"
    }

    override fun getMessageMap(player: Player): Map<SideBarRow, String> {
        val level = player.wrappedLevel
        // TODO implements
        return mapOf(
                // 整地レベル
                SideBarRow.ONE to "",
                // 分間通常破壊量
                SideBarRow.TWO to "",
                // 分間範囲破壊量
                SideBarRow.THREE to "",
                // 分間レリックボーナス量
                SideBarRow.FOUR to "",
                // 次のレベルまでの到達予想時間
                SideBarRow.FIVE to "",
                // 分間マナ回復量
                SideBarRow.SIX to "",
                // 分間マナ減少量
                SideBarRow.SEVEN to "",
                // 分間コンボ数
                SideBarRow.EIGHT to "",
                // 次の採掘速度アップまでの到達予想時間
                SideBarRow.NINE to ""
        )
    }
}