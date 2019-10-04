package click.seichi.gigantic.nexus

import org.bukkit.Chunk
import java.util.*

/**
 * 保護
 * @author tar0ss
 */
abstract class Nexus(val owner: UUID?) {
    var chunkSet: Set<Chunk> = setOf()
        private set

    var memberSet: Set<UUID> =
            if (owner == null)
                setOf()
            else
                setOf(owner)
        private set

    val chunkSize: Int
        get() = chunkSet.size

    val memberSize: Int
        get() = memberSet.size

    fun hasOwner() = owner != null

    fun isOwner(uniqueId: UUID) = uniqueId == owner

    fun isMember(uniqueId: UUID) = memberSet.contains(uniqueId)

    fun addMember(uniqueId: UUID) {
        memberSet = memberSet.toMutableSet().apply { add(uniqueId) }
    }

    fun removeMember(uniqueId: UUID) {
        memberSet = memberSet.toMutableSet().apply { remove(uniqueId) }
    }

    fun isProtect(chunk: Chunk) = chunkSet.contains(chunk)

    fun addChunk(chunk: Chunk) {
        chunkSet = chunkSet.toMutableSet().apply { add(chunk) }
    }

    fun removeChunk(chunk: Chunk) {
        chunkSet = chunkSet.toMutableSet().apply { remove(chunk) }
    }
}