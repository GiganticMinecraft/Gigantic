package click.seichi.gigantic.message

import click.seichi.gigantic.extension.wrappedLocale
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class TitleMessage(
        private val title: LocalizedText?,
        private val subTitle: LocalizedText?,
        private val fadeIn: Int,
        private val stay: Int,
        private val fadeOut: Int
) : Message {

    override fun sendTo(player: Player) {
        player.sendTitle(
                title?.asSafety(player.wrappedLocale) ?: "",
                subTitle?.asSafety(player.wrappedLocale),
                fadeIn,
                stay,
                fadeOut
        )
    }
}