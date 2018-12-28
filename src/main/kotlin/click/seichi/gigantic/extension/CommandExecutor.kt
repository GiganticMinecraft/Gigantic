package click.seichi.gigantic.extension

import click.seichi.gigantic.Gigantic
import org.bukkit.command.CommandExecutor

/**
 * @author tar0ss
 */

/**
 * plugin.ymlで登録したコマンドを[executor]に紐づけする
 */
fun CommandExecutor.bind(id: String) {
    Gigantic.PLUGIN.run {
        getCommand(id).also { pluginCommand ->
            if (pluginCommand == null) {
                logger.warning("${this@bind::class.simpleName}を登録できませんでした")
                logger.warning("plugin.ymlに$id を登録してください")
            } else {
                pluginCommand.executor = this@bind
            }
        }
    }

}