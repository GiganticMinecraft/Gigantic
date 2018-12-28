package click.seichi.gigantic.command

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.database.dao.DonateHistory
import click.seichi.gigantic.database.dao.User
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.command.PointMessages
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.scheduler.BukkitRunnable
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * @author tar0ss
 */
class DonateCommand : TabExecutor {

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

        val uuid = UUID.fromString(args[0]) ?: return false
        val increase = args[1].toIntOrNull() ?: return false

        // 非同期に実行
        object : BukkitRunnable() {
            override fun run() {
                val messages = mutableListOf<LocalizedText>()
                // UserDao を取得
                transaction {
                    val user = User.findById(uuid)
                    if (user == null) {
                        messages.add(PointMessages.NO_USER(uuid))
                        return@transaction
                    }
                    // プレイヤーがオンライン，オフライン関係なく書き換え
                    user.donatePoint += increase

                    // Historyに追加
                    DonateHistory.new {
                        this@new.user = user
                        this@new.amount = increase
                    }

                    messages.add(PointMessages.COMPLETE_STORE)
                }

                // 全てのメッセージを同期送信
                object : BukkitRunnable() {
                    override fun run() {
                        sender.sendMessage(PointMessages.DETECT_DONATE.asSafety(Gigantic.DEFAULT_LOCALE))
                        sender.sendMessage("UUID : $uuid ")
                        sender.sendMessage("NUM : $increase")
                        messages.forEach { message ->
                            sender.sendMessage(message.asSafety(Gigantic.DEFAULT_LOCALE))
                        }
                    }
                }.runTask(Gigantic.PLUGIN)
            }
        }.runTaskAsynchronously(Gigantic.PLUGIN)

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