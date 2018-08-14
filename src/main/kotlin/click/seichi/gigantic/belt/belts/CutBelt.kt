package click.seichi.gigantic.belt.belts

import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.button.buttons.FixedButtons
import click.seichi.gigantic.button.buttons.HotButtons

/**
 * @author tar0ss
 */
object CutBelt : Belt() {
    init {
        registerFixedButton(0, FixedButtons.AXE)
        registerHotButton(1, HotButtons.FLASH_BOOK)
        registerHotButton(2, HotButtons.MINE_BURST_BOOK)
    }
}