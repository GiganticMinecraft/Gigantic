package click.seichi.gigantic.player

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object Defaults {
    const val MANA = 0
    const val MANA_BAR_NUM = 10
    const val MANA_CHAR = "★"
    const val MANA_LOST_CHAR = "☆"
    val ITEM = ItemStack(Material.AIR)
    const val TOOL_ID = 2
    const val BELT_ID = 1
    const val EFFECT_ID = 0
    // プロフィール更新にかかる時間（秒）
    const val PROFILE_UPDATE_TIME = 1L
    // 寄付履歴表示にかかる時間（秒）
    const val DONATE_HISTORY_LOAD_TIME = 1L
    // Elytra Settings
    const val ELYTRA_BASE_SPEED = 0.05
    const val ELYTRA_BASE_LAUNCH = 3
    // will Settings
    const val WILL_BASIC_UNLOCK_AMOUNT = 1000
    const val WILL_ADVANCED_UNLOCK_AMOUNT = 4000

    // will spirit Settings
    // 意志が消滅するまでの時間(ticks)
    const val WILL_SPIRIT_DEATH_DURATION = 100L

    // achievement Settings
    // ブロック破壊による実績更新の間隔（ブロック数）
    const val ACHIEVEMENT_BLOCK_BREAK_UPDATE_COUNT = 10

    // レリック生成に必要なエーテル数
    const val RELIC_GENERATOR_REQUIRE_ETHEL = 100

    const val RELIC_MUL_DIFFX = 0.7
    const val RELIC_MUL_BASE = 1.7

    // ホームポイントの最大数
    const val MAX_HOME = 5

    // スカイウォークで設置されるブロック
    val SKY_WALK_AIR_MATERIAL = Material.LIGHT_GRAY_STAINED_GLASS
    val SKY_WALK_WATER_MATERIAL = Material.BLUE_STAINED_GLASS
    val SKY_WALK_LAVA_MATERIAL = Material.RED_STAINED_GLASS

    // 1回の投票でもらえる投票p
    val VOTE_POINT_PER_VOTE = 10

    // 寄付1p獲得に必要な寄付額
    val DONATITON_PER_DONATE_POINT = 100

    // 投票特典で1種類につき貰えるエーテル量
    val VOTE_BONUS_ETHEL = 100L
    // 投票得点で通常意志をもらえる種類数
    val VOTE_BONUS_BASIC_WILL_NUM = 3
    // 投票得点で高度意志をもらえる種類数
    val VOTE_BONUS_ADVANCED_WILL_NUM = 1
}