package click.seichi.gigantic.message

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.wrappedLocale
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

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
            object : BukkitRunnable() {
                override fun run() {
                    if (!player.isValid) return
                    player.offer(Keys.TITLE, null)
                }
            }.runTaskLater(Gigantic.PLUGIN, fadeIn.plus(stay).plus(fadeOut).toLong())
        }

        if (subTitle != null) {
            player.offer(Keys.SUBTITLE, nextSubTitle)
            object : BukkitRunnable() {
                override fun run() {
                    if (!player.isValid) return
                    player.offer(Keys.SUBTITLE, null)
                }
            }.runTaskLater(Gigantic.PLUGIN, fadeIn.plus(stay).plus(fadeOut).toLong())
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