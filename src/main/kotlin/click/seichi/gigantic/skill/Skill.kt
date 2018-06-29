package click.seichi.gigantic.skill

import click.seichi.gigantic.message.LocalizedString
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
abstract class Skill(protected val player: Player) {
    // プレイヤーに表示される名前
    abstract val displayName: LocalizedString

    abstract val isUnlocked: Boolean

    abstract val isCooldown: Boolean

    abstract fun fire(): SkillState

    abstract fun load(): SkillState

}