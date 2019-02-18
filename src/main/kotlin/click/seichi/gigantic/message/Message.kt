package click.seichi.gigantic.message

import org.bukkit.entity.Player

/**
 * @author unicroak
 * @author tar0ss
 *
 * TODO 全てのメッセージオブジェクトをmessage.ymlに移動
 */
interface Message {

    // メッセージの送信時間
    val duration: Long

    fun sendTo(player: Player)

}