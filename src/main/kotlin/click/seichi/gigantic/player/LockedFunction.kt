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

    FLASH(3, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 4
    }, UnlockMessages.UNLOCK_FLASH),

    HEAL(4, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 3
    }, UnlockMessages.UNLOCK_HEAL),

    SWITCH(5, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 2
    }, UnlockMessages.UNLOCK_SWITCH),

    EXPLOSION(6, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 10
    }, UnlockMessages.UNLOCK_EXPLOSION),

    TERRA_DRAIN(6, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 7
    }, UnlockMessages.UNLOCK_TERRA_DRAIN),

    ;

    fun isUnlocked(player: Player) = isUnlocking(player)
}