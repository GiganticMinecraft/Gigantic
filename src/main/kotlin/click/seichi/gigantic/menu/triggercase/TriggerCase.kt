package click.seichi.gigantic.menu.triggercase

import org.bukkit.event.Event

/**
 * @author tar0ss
 * powered by unicroak
 */
class TriggerCase<T : Event>(
        val clazz: Class<T>,
        private val opening: (T) -> Unit
) {

    fun open(event: Event) = opening(event as T)

}