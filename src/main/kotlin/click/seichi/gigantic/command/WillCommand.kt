package click.seichi.gigantic.command

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.message.messages.command.CommandMessages
import click.seichi.gigantic.message.messages.command.WillMessages
import click.seichi.gigantic.spirit.SpiritManager
import click.seichi.gigantic.spirit.spawnreason.WillSpawnReason
import click.seichi.gigantic.spirit.spirits.WillSpirit
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class WillCommand : TabExecutor {

    override fun onCommand(
            sender: CommandSender,
            command: Command,
            label: String,
            args: Array<out String>
    ): Boolean {
        // 引数なしであれば除外
        if (args.isEmpty()) return false
        // 引数が1でなければ除外
        if (args.size != 1) return false

        // consoleなら除外
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.GRAY}" +
                    CommandMessages.CONSOLE.asSafety(Gigantic.DEFAULT_LOCALE))
            return true
        }

        val willName = args[0]

        // 指定した名前が存在しないとき
        if (!Will.values().map { it.name }.contains(willName)) {
            sender.sendMessage(
                    Will.values()
                            .map {
                                WillMessages.WILL_INFO(it).asSafety(Gigantic.DEFAULT_LOCALE)
                            }.toTypedArray()
            )
            return true
        }

        // will
        val will = Will.valueOf(willName)

        val direction = sender.eyeLocation.direction
        val spawnLocation = sender.eyeLocation.clone().add(direction)
        val spirit = WillSpirit(WillSpawnReason.AWAKE, spawnLocation, will, sender)
        SpiritManager.spawn(spirit)
        sender.sendMessage("${ChatColor.GREEN}" +
                WillMessages.SPAWN(will).asSafety(Gigantic.DEFAULT_LOCALE))
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