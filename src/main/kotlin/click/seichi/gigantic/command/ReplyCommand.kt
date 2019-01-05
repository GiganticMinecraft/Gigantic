package click.seichi.gigantic.command

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.command.TellMessages
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class ReplyCommand : TabExecutor {

    override fun onCommand(
            sender: CommandSender,
            command: Command,
            label: String,
            args: Array<out String>
    ): Boolean {
        // 引数なしでなければ除外
        if (args.isEmpty()) return false
        // consoleなら除外
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.GRAY}" +
                    TellMessages.CONSOLE.asSafety(Gigantic.DEFAULT_LOCALE))
            return true
        }
        val id = sender.getOrPut(Keys.LAST_TELL_ID)

        // 存在しない場合は除外
        if (id == null) {
            sender.sendMessage("${ChatColor.GRAY}" +
                    TellMessages.NO_ID.asSafety(sender.wrappedLocale))
            return true
        }

        // 送り先を取得
        val to = Bukkit.getPlayer(id)
        // メッセージ
        val msg = args[1]

        // 存在しない場合は除外
        if (to == null || !to.isValid) {
            sender.sendMessage("${ChatColor.GRAY}" +
                    TellMessages.NO_PLAYER.asSafety(sender.wrappedLocale))
            return true
        }

        to.sendMessage("${ChatColor.GRAY}" +
                sender.name + TellMessages.TELL_PREFIX.asSafety(to.wrappedLocale) +
                " " + msg)

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