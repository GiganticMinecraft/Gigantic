package click.seichi.gigantic.language

import click.seichi.gigantic.extension.sendActionBar
import org.bukkit.entity.Player

/**
 * @author unicroak
 */
enum class ChatMessageProtocol(val sendTo: (Player, String) -> Unit) {

    ACTION_BAR({ player, message -> player.sendActionBar(message) }),

    CHAT({ player, message -> player.sendMessage(message) }),

    SUB_TITLE({ player, message -> player.sendTitle("", message, 10, 80, 10) }),

    TITLE({ player, message -> player.sendTitle(message, "", 10, 80, 10) }),

    ;

}