package click.seichi.gigantic.player

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object Defaults {
    const val MANA = 0
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
    // 意志の発生確率
    const val WILL_SPAWN_PROBABILITY = 0.01

    // will spirit Settings
    // 意志が埋まってから消滅するまでの時間(ticks)
    const val WILL_SPIRIT_DEATH_DURATION = 100L

    // achievement Settings
    // ブロック破壊による実績更新の間隔（ブロック数）
    const val ACHIEVEMENT_BLOCK_BREAK_UPDATE_COUNT = 10

    // レリック生成に必要なエーテル数
    const val RELIC_GENERATOR_REQUIRE_ETHEL = 100L

    const val RELIC_MUL_DIFFX = 0.7
    const val RELIC_MUL_BASE = 1.7

    // ホームポイントの最大数
    const val MAX_HOME = 5

    // スカイウォークで設置されるブロック
    val SKY_WALK_AIR_MATERIAL = Material.LIGHT_GRAY_STAINED_GLASS
    val SKY_WALK_WATER_MATERIAL = Material.BLUE_STAINED_GLASS
    val SKY_WALK_LAVA_MATERIAL = Material.RED_STAINED_GLASS
    val SKY_WALK_TORCH_MATERIAL = Material.YELLOW_STAINED_GLASS

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

    // 標準の移動速度
    val WALK_SPEED = 0.2.toBigDecimal()
    // ルナフレックスの最大段階
    val LUNA_FLEX_MAX_DEGREE = 10.minus(WALK_SPEED.times(10.toBigDecimal()).toInt())


    // コンボの1間隔ごとの最大減少コンボ数
    val MAX_DECREASE_COMBO_PER_STEP = 50
    // コンボの最大待ち時間（時間）
    val MAX_COMBO_CONTINUATION_HOUR = 1

    val TIPS_PREFIX = "${ChatColor.YELLOW}" + "[TIPS] " + "${ChatColor.RESET}"

    // [Belt]のトーテムスロット
    val TOTEM_SLOT = 6

    // スキル　フォーカストーテムの形成に必要な欠片の数
    val MAX_TOTEM_PIECE = 100
    // 欠片の発生確率
    val PIECE_PROBABILITY = 0.02

    // プレイヤーリストの更新頻度(tick)
    val PLAYERLIST_UPDATE_INTERVAL = 100L

    // プレイヤーデータ保存時のdelay
    val PLAYER_DATA_SAVE_DELAY = 20L * 3L

    // ランクデータ更新時のdelay
    val RANK_DATA_SAVE_DELAY = 20L * 60L + PLAYER_DATA_SAVE_DELAY

    // 総合ランキングの取得レコード数
    val RANKING_BOUND = 10000

    // TickEventを発火する際のdelay
    val TICK_EVENT_DELAY = 20L * 3L

    // 露天掘りで得られる経験値量
    val STRIP_BONUS = 100L

    // サイドバー更新の確認頻度
    val SIDEBAR_UPDATE_INTERVAL = 1L
}