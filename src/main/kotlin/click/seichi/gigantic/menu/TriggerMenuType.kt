package click.seichi.gigantic.menu

import click.seichi.gigantic.menu.triggercase.TriggerCase
import org.bukkit.event.Event

/**
 * @author tar0ss
 * powered by unicroak
 *
 * if you want to open menu on a event,you need to listen that event yourself.
 * ex)
 *   @EventHandler
 *   fun onBlockBreak(event: BlockBreakEvent) {
 *      event.openMenu()
 *   }
 */
enum class TriggerMenuType(vararg triggerCases: TriggerCase<*>) {
//    ALL_MENU(
//            TriggerCase(InventoryClickEvent::class.java) { event ->
//
//
//            }
//    )
    ;

    private val triggerCaseSet = setOf(*triggerCases)

    // TODO Bukkitを亡き者にし, ジェネリクスを蘇らせる
    fun <T : Event> openIfNeeded(event: T) {
        triggerCaseSet
                .firstOrNull { it.clazz == event::class.java }
                ?.open(event)
    }
}