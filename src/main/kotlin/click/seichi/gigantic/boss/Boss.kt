package click.seichi.gigantic.boss

import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.BossMessages
import click.seichi.gigantic.relic.Relic

/**
 * @author tar0ss
 *
 * TODO ボスの難易度を分けて難易度別に出現割合を固定する
 * TODO 全てboss.ymlで編集可能にする
 */
enum class Boss(
        val id: Int,
        // 代表の頭
        val head: Head,
        // 名前
        val localizedName: LocalizedText,
        // 最大体力
        val maxHealth: Long,
        // 攻撃力
        val attackDamage: Long,
        // 攻撃間隔（秒）
        val attackInterval: Long,
        // ドロップする遺物群
        vararg dropRelic: DropRelic
) {
    PIG(0, Head.PIG, BossMessages.PIG_NAME, 100L, 40, 10, DropRelic(Relic.PIG_TAILS, 1.0)),
    MOLE(1, Head.MOLE, BossMessages.MOLE_NAME, 12000L, 6000, 5, DropRelic(Relic.MOLE_FUR, 0.8)),
    FROG(2, Head.FROG, BossMessages.FROG_NAME, 12000L, 12000, 12, DropRelic(Relic.FROG_OIL, 0.8)),
    THE_EARTH(3, Head.THE_EARTH, BossMessages.THE_EARTH_NAME, 7000000000L, 100000000, 10, DropRelic(Relic.EARTH_CORE, 0.4)),
    STEEL(4, Head.STEEL, BossMessages.STEEL_NAME, 10000L, 10000, 10, DropRelic(Relic.STEEL_INGOT, 0.8)),
    BIRD(5, Head.BIRD, BossMessages.BIRD_NAME, 50L, 70, 20, DropRelic(Relic.FEATHERS, 1.0)),
    GUARDIAN_OF_THE_FOREST(6, Head.GUARDIAN_OF_THE_FOREST, BossMessages.GUARDIAN_OF_THE_FOREST_NAME, 4000L, 10000, 30, DropRelic(Relic.BEAR_HAND, 0.3)),
    TURKEY(7, Head.TURKEY, BossMessages.TURKEY_NAME, 450L, 100, 3, DropRelic(Relic.GRILLED_TURKEY, 0.6)),
    PINK_MUSHROOM(8, Head.PINK_MUSHROOM, BossMessages.PINK_MUSHROOM_NAME, 200L, 130, 7, DropRelic(Relic.PINK_SPORES, 0.9)),
    RAINBOW(9, Head.RAINBOW, BossMessages.RAINBOW_NAME, 123456L, 12345, 2, DropRelic(Relic.A_PIECE_OF_RAINBOW, 0.28)),
    MERMAID(10, Head.MERMAID, BossMessages.MERMAID_NAME, 136000L, 80000, 6, DropRelic(Relic.MERMAID_TEARS, 0.33)),
    MERMAN(11, Head.MERMAN, BossMessages.MERMAN_NAME, 182000L, 140000, 8, DropRelic(Relic.MERMAN_SCALES, 0.88)),
    TRITON(12, Head.TRITON, BossMessages.TRITON_NAME, 132000000L, 100000000, 10, DropRelic(Relic.TRITON_TRIDENT, 0.42)),
    BISMARCK(13, Head.BISMARCK, BossMessages.BISMARCK_NAME, 4800L, 1000, 11, DropRelic(Relic.BISMARCK_IRON_KNUCKLE, 0.75)),
    STONE(14, Head.STONE, BossMessages.STONE_NAME, 140L, 80, 4, DropRelic(Relic.COBBLE_STONE, 0.3)),
    MOTHER(15, Head.MOTHER, BossMessages.MOTHER_NAME, 80000L, 1, 30, DropRelic(Relic.LOVE_OF_MOTHER, 1.00)),

    ;

    val dropRelicSet = dropRelic.toSet()

    data class DropRelic(
            val relic: Relic,
            val probability: Double
    )

}