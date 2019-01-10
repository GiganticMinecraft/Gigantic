package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.RelicGeneratorButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.RelicGeneratorMenuMessages
import click.seichi.gigantic.sound.DetailedSound
import click.seichi.gigantic.sound.sounds.MenuSounds
import click.seichi.gigantic.will.Will
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object RelicGeneratorMenu : Menu() {

    override val size: Int
        get() = 5 * 9

    override val openSound: DetailedSound
        get() = MenuSounds.SPECIAL_MENU_OPEN
    override val closeSound: DetailedSound
        get() = MenuSounds.SPECIAL_MENU_CLOSE

    override fun getTitle(player: Player): String {
        return RelicGeneratorMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(2, RelicGeneratorButtons.SELECT_ETHEL(Will.TERRA))
        registerButton(10, RelicGeneratorButtons.SELECT_ETHEL(Will.AQUA))
        registerButton(18, RelicGeneratorButtons.SELECT_ETHEL(Will.IGNIS))
        registerButton(28, RelicGeneratorButtons.SELECT_ETHEL(Will.NATURA))
        registerButton(38, RelicGeneratorButtons.SELECT_ETHEL(Will.AER))
        registerButton(6, RelicGeneratorButtons.SELECT_ETHEL(Will.GLACIES))
        registerButton(16, RelicGeneratorButtons.SELECT_ETHEL(Will.SOLUM))
        registerButton(26, RelicGeneratorButtons.SELECT_ETHEL(Will.VENTUS))
        registerButton(34, RelicGeneratorButtons.SELECT_ETHEL(Will.LUX))
        registerButton(42, RelicGeneratorButtons.SELECT_ETHEL(Will.UMBRA))
    }

}