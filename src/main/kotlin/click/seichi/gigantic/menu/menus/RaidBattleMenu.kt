package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.button.buttons.MenuButtons
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.sound.DetailedSound
import click.seichi.gigantic.sound.sounds.MenuSounds
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

/**
 * @author tar0ss
 */
object RaidBattleMenu : Menu() {

    override val type: InventoryType
        get() = InventoryType.CHEST

    override val size: Int
        get() = 9

    override val openSound: DetailedSound
        get() = MenuSounds.BATTLE_MENU_OPEN

    override val closeSound: DetailedSound
        get() = MenuSounds.BATTLE_MENU_CLOSE

    override fun getTitle(player: Player): String {
        return MenuMessages.RAID_BOSS.asSafety(player.wrappedLocale)
    }

    init {
        (0..Boss.MAX_RANK).forEach { slot ->
            registerButton(slot, MenuButtons.RAID_BATTLE_BOSS(slot + 1))
        }
    }

}