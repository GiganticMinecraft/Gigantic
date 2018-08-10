package click.seichi.gigantic.player

import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.messages.UnlockMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
enum class LockedFunction(
        val id: Int,
        private val isUnlocking: (Player) -> Boolean,
        val unlockMessage: ChatMessage? = null
) {
    MINE_BURST(0, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 5
    }, UnlockMessages.UNLOCK_MINE_BURST),

    RAID_BATTLE(1, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 6
    }, UnlockMessages.UNLOCK_RAID_BATTLE),

    MANA(2, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 10
    }),
    ;

    fun isUnlocked(player: Player) = isUnlocking(player)
}