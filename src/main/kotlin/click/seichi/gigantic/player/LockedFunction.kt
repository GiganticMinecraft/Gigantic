package click.seichi.gigantic.player

import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.messages.UnlockMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 *
 * TODO 解放レベルを全てfunction.ymlで調整可能にする
 */
enum class LockedFunction(
        val id: Int,
        private val canUnlocking: (Player) -> Boolean,
        val unlockAction: (Player) -> Unit = {},
        val unlockMessage: ChatMessage? = null
) {
    MINE_BURST(0, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 5
    }, unlockMessage = UnlockMessages.UNLOCK_MINE_BURST),

    RAID_BATTLE(1, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 6
    }, unlockMessage = UnlockMessages.UNLOCK_RAID_BATTLE),

    MANA(2, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 10
    }, unlockAction = { player ->
        player.manipulate(CatalogPlayerCache.MANA) {
            it.display()
            // TODO make massage (unlockMessage)
//            PlayerMessages.LEVEL_UP_MANA(prevMax, it.max).sendTo(player)
        }
    }),

    FLASH(3, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 4
    }, unlockMessage = UnlockMessages.UNLOCK_FLASH),

    HEAL(4, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 3
    }, unlockMessage = UnlockMessages.UNLOCK_HEAL),

    SWITCH(5, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 2
    }, unlockAction = { player ->
        player.manipulate(CatalogPlayerCache.BELT_SWITCHER) {
            it.setCanSwitch(Belt.DIG, true)
            it.setCanSwitch(Belt.MINE, true)
            it.setCanSwitch(Belt.CUT, true)
        }
    }, unlockMessage = UnlockMessages.UNLOCK_SWITCH),

    EXPLOSION(6, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 10
    }, unlockMessage = UnlockMessages.UNLOCK_EXPLOSION),

    TERRA_DRAIN(7, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 7
    }, unlockMessage = UnlockMessages.UNLOCK_TERRA_DRAIN),

    WILL_O_THE_WISP(8, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 1
    }, unlockMessage = UnlockMessages.UNLOCK_WILL_O_THE_WISP)
    ;

    fun canUnlocked(player: Player) = canUnlocking(player)

    fun isUnlocked(player: Player) = player.getOrPut(Keys.LOCKED_FUNCTION_MAP[this]!!)
}