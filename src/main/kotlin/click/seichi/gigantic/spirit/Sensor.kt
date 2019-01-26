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
        private val inProgress: (Player?, Long) -> Unit,
        private val sense: (Player?) -> Unit,
        private val inSenseCancelling: (Player?) -> Unit,
        private val duration: Long
) {

    private val senseProgressMap = mutableMapOf<UUID, Long>()

    private val alreadySensedSet = mutableSetOf<UUID>()

    fun update() {

        // 条件を満たしているプレイヤーを探索
        val rangedPlayerSet = location.world.players
                .filter { meetingConditions(it) }
                .filterNot { alreadySensedSet.contains(it.uniqueId) }

        // 交感中のプレイヤーではないプレイヤーのカウントをリセット
        senseProgressMap
                .toMap()
                .filterNot { rangedPlayerSet.contains(Bukkit.getPlayer(it.key)) }
                .forEach {
                    inSenseCancelling(Bukkit.getPlayer(it.key))
                    senseProgressMap.remove(it.key)
                }

        // 交感継続処理
        rangedPlayerSet.forEach { senseProgressMap.compute(it.uniqueId) { uuid, count -> count?.plus(1) ?: 0 } }

        //　交感処理
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