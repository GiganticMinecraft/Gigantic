package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.VoteConfirmMenuButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.VoteConfirmMenuMessages
import org.bukkit.entity.Player

/**
 * @author Mr_IK
 */
object VoteConfirmMenu : Menu() {

    private val OK_POSITION = listOf(0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21)
    private val NG_POSITION = listOf(5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26)

    override val size: Int
        get() = 27

    override fun getTitle(player: Player): String {
        return VoteConfirmMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }



    init {
        // 0～3 9～12 18～21はOKで埋める
        OK_POSITION.forEach { registerButton(it, VoteConfirmMenuButtons.OK) }

        // 5～8 14～17 23～26はNGで埋める
        NG_POSITION.forEach { registerButton(it, VoteConfirmMenuButtons.NG) }
    }
}