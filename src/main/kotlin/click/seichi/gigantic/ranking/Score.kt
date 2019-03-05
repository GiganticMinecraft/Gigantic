package click.seichi.gigantic.ranking

import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.cache.RankingPlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.database.RankingEntity
import click.seichi.gigantic.database.table.ranking.RankingScoreTable
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SortOrder
import java.util.*

/**
 * @author tar0ss
 */
enum class Score(
        val column: Column<Long>,
        val sortOrder: SortOrder,
        private val material: Material,
        private val localizedName: LocalizedText
) {
    EXP(
            RankingScoreTable.exp,
            SortOrder.DESC,
            Material.EXPERIENCE_BOTTLE,
            LocalizedText(
                    Locale.JAPANESE to "獲得経験値ランキング"
            )
    ) {
        override fun write(entity: RankingEntity, cache: PlayerCache) {
            var exp = 0.toBigDecimal()
            Keys.EXP_MAP.values.forEach { exp += cache.getOrDefault(it) }
            entity.rankingScore.exp = exp.toLong()
        }

        override fun read(entity: RankingEntity, cache: RankingPlayerCache) {
            Keys.RANK_EXP.let {
                cache.force(it, it.read(entity))
            }
        }
    },
    ;

    fun getIcon() = ItemStack(material)
    fun getName(locale: Locale) = localizedName.asSafety(locale)

    abstract fun write(entity: RankingEntity, cache: PlayerCache)

    abstract fun read(entity: RankingEntity, cache: RankingPlayerCache)
}