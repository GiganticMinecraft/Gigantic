package click.seichi.gigantic.spell

import org.bukkit.entity.Player
import java.util.function.Consumer

/**
 * マナを必要とするスペル(呪文)
 * @author tar0ss
 */
interface Spell {

    fun findInvokable(player: Player): Consumer<Player>?

    fun tryInvoke(player: Player): Boolean {
        val invokable = findInvokable(player)
        invokable?.accept(player)
        return invokable != null
    }
}