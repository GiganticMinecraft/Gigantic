package click.seichi.gigantic.player

import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.messages.UnlockMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 *
 *
 */
enum class LockedFunction(
        val id: Int,
        private val canUnlocking: (Player) -> Boolean,
        // 毎Login時とアンロック時に処理される
        val unlockAction: (Player) -> Unit = {},
        val unlockMessage: ChatMessage? = null,
        private val priority: UnlockPriority = UnlockPriority.NORMAL
) {
    SKILL_MINE_BURST(0, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 7
    }, unlockMessage = UnlockMessages.UNLOCK_MINE_BURST),

    RAID_BATTLE(1, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 8
    }, unlockMessage = UnlockMessages.UNLOCK_RAID_BATTLE),

    MANA_STONE(2, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 10
    }, unlockMessage = UnlockMessages.UNLOCK_MANA_STONE,
            priority = UnlockPriority.HIGHEST),

    SKILL_FLASH(3, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 6
    }, unlockMessage = UnlockMessages.UNLOCK_FLASH),

    SKILL_HEAL(4, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 5
    }, unlockMessage = UnlockMessages.UNLOCK_HEAL),
    SKILL_SWITCH(5, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 1
    }, unlockAction = { player ->
        player.manipulate(CatalogPlayerCache.BELT_SWITCHER) {
            it.unlock(Belt.DIG)
            it.unlock(Belt.MINE)
            it.unlock(Belt.CUT)
            it.unlock(Belt.SCOOP)
        }
    }, unlockMessage = UnlockMessages.UNLOCK_SWITCH),

    SPELL_TERRA_DRAIN(7, {
        MANA_STONE.isUnlocked(it) &&
                it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 10
    }, unlockMessage = UnlockMessages.UNLOCK_TERRA_DRAIN),

    SKILL_WILL_O_THE_WISP(8, {
        it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 3
    }, unlockMessage = UnlockMessages.UNLOCK_WILL_O_THE_WISP),

    SPELL_STELLA_CLAIR(9, {
        MANA_STONE.isUnlocked(it) &&
                it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 10
    }, unlockMessage = UnlockMessages.UNLOCK_STELLA_CLAIR),

    SPELL_GRAND_NATURA(10, {
        MANA_STONE.isUnlocked(it) &&
                it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 14
    }, unlockMessage = UnlockMessages.UNLOCK_GRAND_NATURA),

    SPELL_AQUA_LINEA(11, {
        MANA_STONE.isUnlocked(it) &&
                it.find(CatalogPlayerCache.LEVEL)?.current ?: 0 >= 18
    }, unlockMessage = UnlockMessages.UNLOCK_AQUA_LINEA),
    ;

    /**1から順に [update] される**/
    enum class UnlockPriority(val amount: Int) {
        HIGHEST(1), HIGH(2), NORMAL(3), LOW(4), LOWEST(5)
    }

    companion object {
        fun update(player: Player, isAction: Boolean = false) {
            values().sortedByDescending { it -> it.priority.amount }
                    .forEach {
                        it.update(player, isAction)
                    }
        }
    }


    private fun update(player: Player, isAction: Boolean) {
        if (canUnlocked(player)) {
            if (isUnlocked(player)) {
                // 現在も解除可能で既に解除済みの時
                if (isAction) {
                    unlockAction
                }
            } else {
                // 現在も解除可能で解除していない時
                unlock(player)
            }
        } else {
            if (isUnlocked(player)) {
                // 現在解除できないがすでに解除しているとき
                lock(player)
            }
        }
    }

    private fun unlock(player: Player) {
        // 解除処理
        player.transform(Keys.LOCKED_FUNCTION_MAP[this] ?: return) { hasUnlocked ->
            if (!hasUnlocked) {
                unlockAction(player)
                unlockMessage?.sendTo(player)
            }
            true
        }
    }

    private fun lock(player: Player) {
        // ロック処理
        player.transform(Keys.LOCKED_FUNCTION_MAP[this] ?: return) {
            false
        }
    }

    private fun canUnlocked(player: Player) = canUnlocking(player)

    fun isUnlocked(player: Player) = canUnlocked(player) && player.getOrPut(Keys.LOCKED_FUNCTION_MAP[this]!!)
}