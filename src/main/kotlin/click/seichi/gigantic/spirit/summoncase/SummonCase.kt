package click.seichi.gigantic.spirit.summoncase

import org.bukkit.event.Event

/**
 * @author unicroak
 */
abstract class SummonCase<T : Event>(val clazz: Class<T>) {

    protected abstract val summoning: (T) -> Unit

    @Suppress("UNCHECKED_CAST")
    fun summon(event: Event) = summoning(event as T)

}