package click.seichi.gigantic.menu

import click.seichi.gigantic.menu.menus.MainMenu
import click.seichi.gigantic.menu.triggercase.TriggerCase
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent

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
    MAIN_MENU(
            TriggerCase(PlayerInteractEvent::class.java) { event ->
                val player = event.player ?: return@TriggerCase
                val tool = player.inventory.itemInMainHand ?: return@TriggerCase
                if (tool.type != Material.STICK) return@TriggerCase
                MainMenu.open(event.player)
            }
    )
    ;

    private val triggerCaseSet = setOf(*triggerCases)

    // TODO Bukkitを亡き者にし, ジェネリクスを蘇らせる
    fun <T : Event> openIfNeeded(event: T) {
        triggerCaseSet
                .firstOrNull { it.clazz == event::class.java }
                ?.open(event)
    }
}