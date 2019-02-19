package click.seichi.gigantic.command

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.message.messages.command.NowMessages
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.joda.time.DateTime

/**
 * @author tar0ss
 */
class NowCommand : TabExecutor {

    override fun onCommand(
            sender: CommandSender,
            command: Command,
            label: String,
            args: Array<out String>
    ): Boolean {
        // 引数あれば除外
        if (args.isNotEmpty()) return false

        // 現在時刻を送信
        sender.sendMessage(NowMessages.NOW(DateTime.now()).asSafety(Gigantic.DEFAULT_LOCALE))

        return true
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