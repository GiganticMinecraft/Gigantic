package click.seichi.gigantic.command

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.database.dao.User
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.command.VoteMessages
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.scheduler.BukkitRunnable
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * @author tar0ss
 */
class VoteCommand : TabExecutor {

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
        // 投票数
        val increase = args[1].toIntOrNull() ?: return false

        // 非同期に実行
        object : BukkitRunnable() {
            override fun run() {
                val messages = mutableListOf<LocalizedText>()
                // UserDao を取得
                transaction {
                    val user = User.findById(uuid)
                    if (user == null) {
                        messages.add(VoteMessages.NO_USER(uuid))
                        return@transaction
                    }
                    // プレイヤーがオンライン，オフライン関係なく書き換え
                    user.votePoint += increase
                    messages.add(VoteMessages.COMPLETE(uuid, increase))
                }

                // 全てのメッセージを同期送信
                object : BukkitRunnable() {
                    override fun run() {
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