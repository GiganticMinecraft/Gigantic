package click.seichi.gigantic.skill

import org.bukkit.entity.Player
import java.util.function.Consumer

/**
 * @author tar0ss
 */
interface Skill {

    fun findInvokable(player: Player): Consumer<Player>?

    fun tryInvoke(player: Player): Boolean {
        val invokable = findInvokable(player)
        invokable?.accept(player)
        return invokable != null
    }
}