package click.seichi.gigantic.sidebar

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.sidebar.bars.EthelLogger
import click.seichi.gigantic.sidebar.bars.InfoBar
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
enum class SideBarType(
        val id: Int,
        val sideBar: SideBar
) {
    Ethel(0, EthelLogger()),
    INFO(1, InfoBar())
    ;

    companion object {
        // 重複確認
        val hasDuplicateId = values().size != values().map { it.id }.toSet().size
    }

    fun change(player: Player) {
        val current = player.getOrPut(Keys.SIDEBAR_TYPE)
        if (current == this) return
        player.offer(Keys.SIDEBAR_TYPE, this)
        sideBar.show(player)
    }
}