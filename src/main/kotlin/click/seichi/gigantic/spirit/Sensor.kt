package click.seichi.gigantic.spirit

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
        private val sense: (Player?) -> Unit,
        private val inSenseCancelling: (Player?) -> Unit,
        private val duration: Int
) {

    private val senseProgressMap = mutableMapOf<UUID, Int>()

    private val alreadySensedSet = mutableSetOf<UUID>()

    fun update() {
        val rangedPlayerSet = location.world.players
                .filter { meetingConditions(it) }
                .filterNot { alreadySensedSet.contains(it.uniqueId) }

        senseProgressMap
                .toMap()
                .filterNot { rangedPlayerSet.contains(Bukkit.getPlayer(it.key)) }
                .forEach {
                    inSenseCancelling(Bukkit.getPlayer(it.key))
                    senseProgressMap.remove(it.key)
                }

        rangedPlayerSet.forEach { senseProgressMap.compute(it.uniqueId) { uuid, count -> count?.plus(1) ?: 0 } }

        senseProgressMap
                .toMap()
                .onEach { inProgress(Bukkit.getPlayer(it.key), it.value) }
                .filterValues { duration <= it }
                .forEach { uniqueId, _ ->
                    senseProgressMap.remove(uniqueId)
                    alreadySensedSet.add(uniqueId)
                    sense(Bukkit.getPlayer(uniqueId))
                }
    }

}