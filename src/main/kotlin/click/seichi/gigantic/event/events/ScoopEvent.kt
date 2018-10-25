package click.seichi.gigantic.event.events

import click.seichi.gigantic.event.CustomEvent
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable

/**
 * @author tar0ss
 */
class ScoopEvent(val player: Player, val block: Block) : CustomEvent(), Cancellable {

    private var cancel: Boolean = false

    override fun setCancelled(cancel: Boolean) {
        this.cancel = cancel
    }

    override fun isCancelled(): Boolean {
        return cancel
    }

}