package click.seichi.gigantic.skill

import click.seichi.gigantic.message.LocalizedString
import click.seichi.gigantic.util.Box
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
abstract class BreakSkill : Skill() {
    // プレイヤーに表示される簡略名（1文字）
    abstract val shortName: LocalizedString

    protected abstract fun getBreakBox(player: Player): Box
}