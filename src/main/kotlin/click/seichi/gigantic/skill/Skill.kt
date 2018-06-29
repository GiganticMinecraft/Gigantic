package click.seichi.gigantic.skill

import click.seichi.gigantic.message.LocalizedString
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
abstract class Skill {
    // プレイヤーに表示される名前
    abstract val displayName: LocalizedString

    abstract fun fire(player: Player): SkillState

    abstract fun load(player: Player): SkillState

    protected abstract fun isUnlocked(player: Player): Boolean

    protected abstract fun isCooldown(player: Player): Boolean

}