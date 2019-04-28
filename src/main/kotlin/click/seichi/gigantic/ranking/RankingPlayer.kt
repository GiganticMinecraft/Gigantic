package click.seichi.gigantic.ranking

import click.seichi.gigantic.cache.cache.RankingPlayerCache
import click.seichi.gigantic.cache.key.Keys
import java.util.*

/**
 * @author tar0ss
 */
class RankingPlayer(
        val uniqueId: UUID,
        val rank: Int,
        cache: RankingPlayerCache
) {

    val name = cache.getOrDefault(Keys.RANK_PLAYER_NAME)

    val level = cache.getOrDefault(Keys.RANK_LEVEL)

    val exp = cache.getOrDefault(Keys.RANK_EXP)

    val breakBlock = cache.getOrDefault(Keys.RANK_BREAK_BLOCK)

    val multiBreakBlock = cache.getOrDefault(Keys.RANK_MULTI_BREAK_BLOCK)

    val relicBonus = cache.getOrDefault(Keys.RANK_RELIC_BONUS)

    val maxCombo = cache.getOrDefault(Keys.RANK_MAX_COMBO)

    val relic = cache.getOrDefault(Keys.RANK_RELIC)

    val stripMine = cache.getOrDefault(Keys.RANK_STRIP_MINE)

}