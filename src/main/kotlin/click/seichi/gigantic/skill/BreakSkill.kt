package click.seichi.gigantic.skill

import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.function.Consumer

/**
 * @author tar0ss
 */
interface BreakSkill {

    fun findInvokable(player: Player, block: Block): Consumer<Player>?

    fun tryInvoke(player: Player, block: Block): Boolean {
        val invokable = findInvokable(player, block)
        invokable?.accept(player)
        return invokable != null
    }

}