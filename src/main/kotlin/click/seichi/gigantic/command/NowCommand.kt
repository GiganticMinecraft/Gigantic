package click.seichi.gigantic.command

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.command.NowMessages
import click.seichi.gigantic.util.Junishi
import click.seichi.gigantic.util.MoonPhase
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
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
        sender.sendMessage(NowMessages.TIME_IN_REAL(DateTime.now()).asSafety(Gigantic.DEFAULT_LOCALE))
        if (sender is Player) {
            val world = sender.world
            val junishi = Junishi.now(world).getName(sender.wrappedLocale)
            val moonPhase = MoonPhase.now(world).getName(sender.wrappedLocale)
            // マイクラ時刻
            sender.sendMessage(NowMessages.TIME_IN_MINECRAFT(world.time).asSafety(sender.wrappedLocale))
            // 十二支
            sender.sendMessage(NowMessages.JUNISHI_IN_MINECRAFT(junishi).asSafety(sender.wrappedLocale))
            // 月齢
            sender.sendMessage(NowMessages.MOONPHASE_IN_MINECRAFT(moonPhase).asSafety(sender.wrappedLocale))
        }
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