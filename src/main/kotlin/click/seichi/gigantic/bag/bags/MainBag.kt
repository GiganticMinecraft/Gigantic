package click.seichi.gigantic.bag.bags

import click.seichi.gigantic.bag.Bag
import click.seichi.gigantic.item.items.BagButtons

/**
 * @author tar0ss
 */
object MainBag : Bag() {
    init {
        registerButton(9, BagButtons.PROFILE)
        registerButton(10, BagButtons.SETTINGS)
        registerButton(11, BagButtons.DONATE_HISTORY)
        registerButton(12, BagButtons.SKILL)
        registerButton(13, BagButtons.SPELL)
        registerButton(15, BagButtons.EFFECT)
        registerButton(16, BagButtons.SHOP)
//        registerButton(19, BagButtons.QUEST)
        registerButton(20, BagButtons.WILL)
        registerButton(21, BagButtons.RELIC)
        registerButton(23, BagButtons.TELEPORT_DOOR)
        registerButton(25, BagButtons.AFK)
        registerButton(26, BagButtons.SIDEBAR_CHANGE)
        registerButton(27, BagButtons.FOLLOW_SETTING)
        registerButton(29, BagButtons.VOTE_BONUS)
        registerButton(31, BagButtons.RELIC_GENERATOR)
        registerButton(33, BagButtons.RANKING)
        registerButton(35, BagButtons.SPECIAL_THANKS)
    }

}