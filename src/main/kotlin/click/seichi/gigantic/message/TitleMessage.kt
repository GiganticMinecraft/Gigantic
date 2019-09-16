package click.seichi.gigantic.message

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.runTaskLater
import click.seichi.gigantic.extension.wrappedLocale
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
open class TitleMessage(
        private val title: LocalizedText?,
        private val subTitle: LocalizedText?,
        private val fadeIn: Int = 10,
        private val stay: Int = 70,
        private val fadeOut: Int = 20
) : Message {

    override fun sendTo(player: Player) {
        val prevTitle = player.getOrPut(Keys.TITLE)
        val nextTitle = title?.asSafety(player.wrappedLocale) ?: prevTitle ?: ""
        val prevSubTitle = player.getOrPut(Keys.SUBTITLE)
        val nextSubTitle = subTitle?.asSafety(player.wrappedLocale) ?: prevSubTitle ?: ""

        if (title != null) {
            player.offer(Keys.TITLE, nextTitle)
            runTaskLater(fadeIn.plus(stay).plus(fadeOut).toLong()) {
                if (!player.isValid) return@runTaskLater
                player.offer(Keys.TITLE, null)
            }
        }

        if (subTitle != null) {
            player.offer(Keys.SUBTITLE, nextSubTitle)
            runTaskLater(fadeIn.plus(stay).plus(fadeOut).toLong()) {
                if (!player.isValid) return@runTaskLater
                player.offer(Keys.SUBTITLE, null)
            }
        }
        player.sendTitle(
                nextTitle,
                nextSubTitle,
                fadeIn,
                stay,
                fadeOut
        )
    }
}