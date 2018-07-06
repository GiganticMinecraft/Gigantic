package click.seichi.gigantic.skill

import click.seichi.gigantic.message.LocalizedString
import click.seichi.gigantic.player.GiganticPlayer

/**
 * @author tar0ss
 */
abstract class Skill {
    // プレイヤーに表示される名前
    abstract val displayName: LocalizedString

    abstract fun isUnlocked(gPlayer: GiganticPlayer): Boolean

}