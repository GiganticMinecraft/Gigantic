package click.seichi.gigantic.skill

import click.seichi.gigantic.language.LocalizedText
import click.seichi.gigantic.player.GiganticPlayer

/**
 * @author tar0ss
 */
abstract class Skill {
    // プレイヤーに表示される名前
    abstract val displayName: LocalizedText

    abstract fun isUnlocked(gPlayer: GiganticPlayer): Boolean

}