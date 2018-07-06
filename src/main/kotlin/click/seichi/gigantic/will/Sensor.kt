package click.seichi.gigantic.will

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

/**
 * @author unicroak
 * @author tar0ss
 */
class Sensor(
        private val location: Location,
        private val meetingConditions: (Player?) -> Boolean,
        private val inProgress: (Player?, Int) -> Unit,
        private val sense: (Player?) -> Unit
) {

    companion object {
        private const val RANGE = 3.0
        private const val DURATION = 60
    }

    private val senseProgressMap = mutableMapOf<UUID, Int>()

    private val alreadySensedSet = mutableSetOf<UUID>()

    fun update() {
        val rangedPlayerSet = location.world
                .getNearbyEntities(location, RANGE, RANGE, RANGE)
                .mapNotNull { it as? Player }
                .filter { meetingConditions(it) }
                .filterNot { alreadySensedSet.contains(it.uniqueId) }

        senseProgressMap
                .toMap()
                .filterNot { rangedPlayerSet.contains(Bukkit.getPlayer(it.key)) }
                .forEach { senseProgressMap.remove(it.key) }

        rangedPlayerSet.forEach { senseProgressMap[it.uniqueId] = senseProgressMap.getOrDefault(it.uniqueId, 0) + 1 }

        senseProgressMap
                .toMap()
                .onEach { inProgress(Bukkit.getPlayer(it.key), it.value) }
                .filterValues { DURATION <= it }
                .forEach { uniqueId, _ ->
                    sense(Bukkit.getPlayer(uniqueId))
                    senseProgressMap.remove(uniqueId)
                    alreadySensedSet.add(uniqueId)
                }
    }

}