package click.seichi.gigantic.command

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.message.messages.command.LiveMessages
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

/**
 * @author tar0ss
 */
class LiveCommand : TabExecutor {

    override fun onCommand(
            sender: CommandSender,
            command: Command,
            label: String,
            args: Array<out String>
    ): Boolean {
        // 引数なしであれば除外
        if (args.isEmpty()) return false
        // 引数が2でなければ除外
        if (args.size != 1) return false
        // メッセージ
        val flagString = args[0]

        return when (flagString.toLowerCase()) {
            "on" -> {
                Gigantic.IS_LIVE = true
                sender.sendMessage(LiveMessages.ON.asSafety(Gigantic.DEFAULT_LOCALE))
                true
            }
            "off" -> {
                Gigantic.IS_LIVE = false
                sender.sendMessage(LiveMessages.OFF.asSafety(Gigantic.DEFAULT_LOCALE))
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onTabComplete(
            sender: CommandSender,
            command: Command,
            label: String,
            args: Array<out String>
    ): MutableList<String> {
        return mutableListOf()
    }
}