package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.offer
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
        get() = 6 * 9

    override val openSound: DetailedSound
        get() = MenuSounds.RELIC_MENU_OPEN
    override val closeSound: DetailedSound
        get() = MenuSounds.RELIC_MENU_CLOSE

    override fun onOpen(player: Player, isFirst: Boolean) {
        if (isFirst)
            player.offer(Keys.SELECTED_WILL, null)
    }

    override fun getTitle(player: Player): String {
        return RelicGeneratorMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(11, RelicGeneratorButtons.SELECT_ETHEL(Will.TERRA))
        registerButton(19, RelicGeneratorButtons.SELECT_ETHEL(Will.AQUA))
        registerButton(27, RelicGeneratorButtons.SELECT_ETHEL(Will.IGNIS))
        registerButton(37, RelicGeneratorButtons.SELECT_ETHEL(Will.NATURA))
        registerButton(47, RelicGeneratorButtons.SELECT_ETHEL(Will.AER))
        registerButton(15, RelicGeneratorButtons.SELECT_ETHEL(Will.GLACIES))
        registerButton(25, RelicGeneratorButtons.SELECT_ETHEL(Will.SOLUM))
        registerButton(35, RelicGeneratorButtons.SELECT_ETHEL(Will.VENTUS))
        registerButton(43, RelicGeneratorButtons.SELECT_ETHEL(Will.LUX))
        registerButton(51, RelicGeneratorButtons.SELECT_ETHEL(Will.UMBRA))

        // 期間限定
        registerButton(1, RelicGeneratorButtons.SELECT_ETHEL(Will.SAKURA))
        registerButton(3, RelicGeneratorButtons.SELECT_ETHEL(Will.MIO))

        registerButton(31, RelicGeneratorButtons.GENERATED)
        registerButton(49, RelicGeneratorButtons.GENERATE)
    }

}