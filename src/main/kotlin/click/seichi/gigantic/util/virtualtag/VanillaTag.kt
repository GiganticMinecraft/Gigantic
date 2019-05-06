package click.seichi.gigantic.util.virtualtag

import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.util.Vector

/**
 * @author unicroak
 */
class VanillaTag(var location: Location,
                 private val text: String) : VirtualTag {

    private var armorStand: ArmorStand? = null

    override fun show() {
        val world = location.world ?: return

        armorStand = world.spawnEntity(location, EntityType.ARMOR_STAND) as ArmorStand

        armorStand?.apply {
            customName = text
            isCustomNameVisible = true
            isSilent = true
            isVisible = true
            isSmall = true
            setBasePlate(false)
            isMarker = true
        }
    }

    override fun push(delta: Vector) {
        armorStand?.velocity = delta
    }

    override fun destroy() {
        armorStand?.remove()
        armorStand = null
    }

}