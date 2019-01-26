package click.seichi.gigantic.command

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.message.messages.HomeMessages
import click.seichi.gigantic.message.messages.command.TellMessages
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class HomeCommand : TabExecutor {

    override fun onCommand(
            sender: CommandSender,
            command: Command,
            label: String,
            args: Array<out String>
    ): Boolean {
        // 引数なしであれば除外
        if (args.isEmpty()) return false
        // 引数が2でなければ除外
        if (args.size != 2) return false

        // consoleなら除外
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.GRAY}" +
                    TellMessages.CONSOLE.asSafety(Gigantic.DEFAULT_LOCALE))
            return true
        }
        // id
        val homeId = args[0].toIntOrNull()?.minus(1) ?: return false
        // name
        val newName = args[1].take(20)

        val homeMap = sender.getOrPut(Keys.HOME_MAP)

        if (!homeMap.contains(homeId)) {
            // 存在しない
            sender.sendMessage("${ChatColor.RED}" +
                    HomeMessages.NO_HOME.asSafety(Gigantic.DEFAULT_LOCALE))
            return true
        }
        val home = homeMap[homeId]!!
        home.name = newName
        sender.sendMessage("${ChatColor.GREEN}" +
                HomeMessages.COMPLETE.asSafety(Gigantic.DEFAULT_LOCALE))
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