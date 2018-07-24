package click.seichi.gigantic.data

import click.seichi.gigantic.data.container.PlayerDataContainer
import java.util.*

/**
 * @author tar0ss
 */
object PlayerDataMemory {
    private val containerMap = mutableMapOf<UUID, PlayerDataContainer>()

    fun get(uniqueId: UUID) = containerMap[uniqueId]!!

    fun add(uniqueId: UUID) {
        containerMap[uniqueId] = PlayerDataContainer()
    }

    fun remove(uniqueId: UUID) {
        containerMap.remove(uniqueId)
    }
}