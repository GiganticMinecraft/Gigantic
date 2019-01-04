package click.seichi.gigantic.util

/**
 * プレイヤーリストの絞り込み項目
 * slotは1..8を指定すること
 * @author tar0ss
 */
enum class RefineItem(val slot: Int) {
    // オンラインプレイヤーのみを絞り込む
    ONLINE(1),
    ;
}