package click.seichi.gigantic.belt.belts

import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.button.buttons.FixedButtons
import click.seichi.gigantic.button.buttons.HotButtons

/**
 * @author tar0ss
 */
object MineBelt : Belt() {
    init {
        registerFixedButton(0, FixedButtons.PICKEL)
        registerHotButton(1, HotButtons.MINE_BURST_BOOK)
    }
}