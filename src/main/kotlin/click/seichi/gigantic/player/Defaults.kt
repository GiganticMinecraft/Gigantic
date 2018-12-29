package click.seichi.gigantic.player

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object Defaults {
    const val MANA = 0
    const val MAX_MANA = 100L
    const val MANA_BAR_NUM = 10
    const val MANA_CHAR = "★"
    const val MANA_LOST_CHAR = "☆"
    val ITEM = ItemStack(Material.AIR)
    const val HEALTH = 100L
    const val TOOL_ID = 2
    const val BELT_ID = 1
    const val EFFECT_ID = 0
    // プロフィール更新にかかる時間（秒）
    const val PROFILE_UPDATE_TIME = 1L
    // 寄付履歴表示にかかる時間（秒）
    const val DONATE_HISTORY_LOAD_TIME = 1L
}