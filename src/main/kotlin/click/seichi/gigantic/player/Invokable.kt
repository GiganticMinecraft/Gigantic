package click.seichi.gigantic.player

import org.bukkit.entity.Player
import java.util.function.Consumer

/**
 * マナを必要としないスキル（能力）
 *
 * @author tar0ss
 */
interface Invokable {

    fun findInvokable(player: Player): Consumer<Player>?

    fun tryInvoke(player: Player): Boolean {
        val invokable = findInvokable(player)
        invokable?.accept(player)
        return invokable != null
    }
}