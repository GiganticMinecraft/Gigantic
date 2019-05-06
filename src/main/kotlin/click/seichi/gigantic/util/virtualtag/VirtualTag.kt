package click.seichi.gigantic.util.virtualtag

import org.bukkit.util.Vector

/**
 * @author unicroak
 */
interface VirtualTag {

    fun show()

    fun push(delta: Vector)

    fun destroy()

}