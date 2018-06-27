package click.seichi.gigantic.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * @author tar0ss
 * @author unicroak
 */
abstract class CustomEvent : Event() {

    companion object {
        @JvmStatic
        private val handler = HandlerList()

        @JvmStatic
        fun getHandlerList() = handler
    }

    override fun getHandlers() = handler

}