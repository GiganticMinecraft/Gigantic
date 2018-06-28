package click.seichi.gigantic.skill

import click.seichi.gigantic.message.LocalizedString
import org.bukkit.entity.Player
import org.bukkit.event.Event

/**
 * @author tar0ss
 */
abstract class Skill {
    // プレイヤーに表示される名前
    abstract val displayName: LocalizedString
    // プレイヤーに表示される簡略名（1文字）
    abstract val shortName: LocalizedString

    /**
     * スキル発火
     *
     * @param player 発火するプレイヤー
     * @return 成功->TRUE,失敗->FALSE
     */
    abstract fun fire(player: Player, event: Event): Boolean
}