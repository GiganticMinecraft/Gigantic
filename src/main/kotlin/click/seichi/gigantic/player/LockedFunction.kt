package click.seichi.gigantic.player

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.messages.UnlockMessages

/**
 * @author tar0ss
 */
enum class LockedFunction(
        val unlockLevel: Int,
        val unlockMessage: ChatMessage? = null
) {
    MINE_BURST(5, UnlockMessages.UNLOCK_MINE_BURST),
    RAID_BATTLE(6, UnlockMessages.UNLOCK_RAID_BATTLE),
    MANA(10),
    ;

    fun isUnlocked(gPlayer: GiganticPlayer) = gPlayer.level.current >= unlockLevel
}