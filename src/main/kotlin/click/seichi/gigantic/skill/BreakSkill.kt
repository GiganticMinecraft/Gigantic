package click.seichi.gigantic.skill

import click.seichi.gigantic.message.LocalizedString
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
abstract class BreakSkill : Skill() {
    // プレイヤーに表示される簡略名（1文字）
    abstract val shortName: LocalizedString

    /**
     * スキル発火
     *
     * @param player 発火するプレイヤー
     * @param baseBlock 発火するブロック
     * @return 成功->TRUE,失敗->FALSE
     */
    abstract fun fire(player: Player, baseBlock: Block): Boolean
}