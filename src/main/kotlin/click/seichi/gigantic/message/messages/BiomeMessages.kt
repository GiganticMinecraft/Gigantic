package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.block.Biome
import java.util.*

/**
 * @author tar0ss
 */
object BiomeMessages {

    val BIOME = { biome: Biome ->
        when (biome) {
            Biome.OCEAN -> LocalizedText(
                    Locale.JAPANESE to "海洋"
            )
            Biome.PLAINS -> LocalizedText(
                    Locale.JAPANESE to "平原"
            )
            Biome.DESERT -> LocalizedText(
                    Locale.JAPANESE to "砂漠"
            )
            Biome.MOUNTAINS -> LocalizedText(
                    Locale.JAPANESE to "山岳"
            )
            Biome.FOREST -> LocalizedText(
                    Locale.JAPANESE to "森林"
            )
            Biome.TAIGA -> LocalizedText(
                    Locale.JAPANESE to "タイガ"
            )
            Biome.SWAMP -> LocalizedText(
                    Locale.JAPANESE to "湿原"
            )
            Biome.RIVER -> LocalizedText(
                    Locale.JAPANESE to "河川"
            )
            Biome.NETHER -> LocalizedText(
                    Locale.JAPANESE to "ネザー"
            )
            Biome.THE_END -> LocalizedText(
                    Locale.JAPANESE to "ジ・エンド"
            )
            Biome.FROZEN_OCEAN -> LocalizedText(
                    Locale.JAPANESE to "凍った海"
            )
            Biome.FROZEN_RIVER -> LocalizedText(
                    Locale.JAPANESE to "凍った川"
            )
            Biome.SNOWY_TUNDRA -> LocalizedText(
                    Locale.JAPANESE to "雪のツンドラ"
            )
            Biome.SNOWY_MOUNTAINS -> LocalizedText(
                    Locale.JAPANESE to "雪山"
            )
            Biome.MUSHROOM_FIELDS -> LocalizedText(
                    Locale.JAPANESE to "キノコ島"
            )
            Biome.MUSHROOM_FIELD_SHORE -> LocalizedText(
                    Locale.JAPANESE to "キノコ島の海岸"
            )
            Biome.BEACH -> LocalizedText(
                    Locale.JAPANESE to "砂浜"
            )
            Biome.DESERT_HILLS -> LocalizedText(
                    Locale.JAPANESE to "砂漠の丘陵"
            )
            Biome.WOODED_HILLS -> LocalizedText(
                    Locale.JAPANESE to "森のある丘陵"
            )
            Biome.TAIGA_HILLS -> LocalizedText(
                    Locale.JAPANESE to "タイガの丘陵"
            )
            Biome.MOUNTAIN_EDGE -> LocalizedText(
                    Locale.JAPANESE to "山の麓"
            )
            Biome.JUNGLE -> LocalizedText(
                    Locale.JAPANESE to "ジャングル"
            )
            Biome.JUNGLE_HILLS -> LocalizedText(
                    Locale.JAPANESE to "ジャングルの丘陵"
            )
            Biome.JUNGLE_EDGE -> LocalizedText(
                    Locale.JAPANESE to "ジャングルの端"
            )
            Biome.DEEP_OCEAN -> LocalizedText(
                    Locale.JAPANESE to "深海"
            )
            Biome.STONE_SHORE -> LocalizedText(
                    Locale.JAPANESE to "石の海岸"
            )
            Biome.SNOWY_BEACH -> LocalizedText(
                    Locale.JAPANESE to "雪の降る砂浜"
            )
            Biome.BIRCH_FOREST -> LocalizedText(
                    Locale.JAPANESE to "白樺の森"
            )
            Biome.BIRCH_FOREST_HILLS -> LocalizedText(
                    Locale.JAPANESE to "白樺の森の丘陵"
            )
            Biome.DARK_FOREST -> LocalizedText(
                    Locale.JAPANESE to "薄暗い森"
            )
            Biome.SNOWY_TAIGA -> LocalizedText(
                    Locale.JAPANESE to "雪のタイガ"
            )
            Biome.SNOWY_TAIGA_HILLS -> LocalizedText(
                    Locale.JAPANESE to "雪のタイガの丘陵"
            )
            Biome.GIANT_TREE_TAIGA -> LocalizedText(
                    Locale.JAPANESE to "巨大樹のタイガ"
            )
            Biome.GIANT_TREE_TAIGA_HILLS -> LocalizedText(
                    Locale.JAPANESE to "巨大樹のタイガの丘陵"
            )
            Biome.WOODED_MOUNTAINS -> LocalizedText(
                    Locale.JAPANESE to "森のある山"
            )
            Biome.SAVANNA -> LocalizedText(
                    Locale.JAPANESE to "サバンナ"
            )
            Biome.SAVANNA_PLATEAU -> LocalizedText(
                    Locale.JAPANESE to "サバンナの台地"
            )
            Biome.BADLANDS -> LocalizedText(
                    Locale.JAPANESE to "荒野"
            )
            Biome.WOODED_BADLANDS_PLATEAU -> LocalizedText(
                    Locale.JAPANESE to "木の生えた荒地高原"
            )
            Biome.BADLANDS_PLATEAU -> LocalizedText(
                    Locale.JAPANESE to "荒地高原"
            )
            Biome.SMALL_END_ISLANDS -> LocalizedText(
                    Locale.JAPANESE to "小さなエンド島"
            )
            Biome.END_MIDLANDS -> LocalizedText(
                    Locale.JAPANESE to "エンドの内陸部"
            )
            Biome.END_HIGHLANDS -> LocalizedText(
                    Locale.JAPANESE to "エンドの高地"
            )
            Biome.END_BARRENS -> LocalizedText(
                    Locale.JAPANESE to "エンドのやせ地"
            )
            Biome.WARM_OCEAN -> LocalizedText(
                    Locale.JAPANESE to "暖かい海"
            )
            Biome.LUKEWARM_OCEAN -> LocalizedText(
                    Locale.JAPANESE to "ぬるい海"
            )
            Biome.COLD_OCEAN -> LocalizedText(
                    Locale.JAPANESE to "冷たい海"
            )
            Biome.DEEP_WARM_OCEAN -> LocalizedText(
                    Locale.JAPANESE to "暖かい深海"
            )
            Biome.DEEP_LUKEWARM_OCEAN -> LocalizedText(
                    Locale.JAPANESE to "ぬるい深海"
            )
            Biome.DEEP_COLD_OCEAN -> LocalizedText(
                    Locale.JAPANESE to "冷たい深海"
            )
            Biome.DEEP_FROZEN_OCEAN -> LocalizedText(
                    Locale.JAPANESE to "凍った深海"
            )
            Biome.THE_VOID -> LocalizedText(
                    Locale.JAPANESE to "奈落"
            )
            Biome.SUNFLOWER_PLAINS -> LocalizedText(
                    Locale.JAPANESE to "ひまわり平原"
            )
            Biome.DESERT_LAKES -> LocalizedText(
                    Locale.JAPANESE to "砂漠の湖"
            )
            Biome.GRAVELLY_MOUNTAINS -> LocalizedText(
                    Locale.JAPANESE to "砂利の山"
            )
            Biome.FLOWER_FOREST -> LocalizedText(
                    Locale.JAPANESE to "花の森"
            )
            Biome.TAIGA_MOUNTAINS -> LocalizedText(
                    Locale.JAPANESE to "タイガの山"
            )
            Biome.SWAMP_HILLS -> LocalizedText(
                    Locale.JAPANESE to "湿地の丘陵"
            )
            Biome.ICE_SPIKES -> LocalizedText(
                    Locale.JAPANESE to "樹氷"
            )
            Biome.MODIFIED_JUNGLE -> LocalizedText(
                    Locale.JAPANESE to "ジャングル亜種"
            )
            Biome.MODIFIED_JUNGLE_EDGE -> LocalizedText(
                    Locale.JAPANESE to "変異したジャングルの端"
            )
            Biome.TALL_BIRCH_FOREST -> LocalizedText(
                    Locale.JAPANESE to "巨大な白樺の森"
            )
            Biome.TALL_BIRCH_HILLS -> LocalizedText(
                    Locale.JAPANESE to "巨大な白樺の丘陵"
            )
            Biome.DARK_FOREST_HILLS -> LocalizedText(
                    Locale.JAPANESE to "薄暗い森の丘陵"
            )
            Biome.SNOWY_TAIGA_MOUNTAINS -> LocalizedText(
                    Locale.JAPANESE to "雪の降るタイガの山"
            )
            Biome.GIANT_SPRUCE_TAIGA -> LocalizedText(
                    Locale.JAPANESE to "巨大松のタイガ"
            )
            Biome.GIANT_SPRUCE_TAIGA_HILLS -> LocalizedText(
                    Locale.JAPANESE to "巨大松のタイガの丘陵"
            )
            Biome.MODIFIED_GRAVELLY_MOUNTAINS -> LocalizedText(
                    Locale.JAPANESE to "砂利の山亜種"
            )
            Biome.SHATTERED_SAVANNA -> LocalizedText(
                    Locale.JAPANESE to "荒廃したサバンナ"
            )
            Biome.SHATTERED_SAVANNA_PLATEAU -> LocalizedText(
                    Locale.JAPANESE to "荒廃したサバンナ高原"
            )
            Biome.ERODED_BADLANDS -> LocalizedText(
                    Locale.JAPANESE to "侵食された荒野"
            )
            Biome.MODIFIED_WOODED_BADLANDS_PLATEAU -> LocalizedText(
                    Locale.JAPANESE to "変異した木が生えている荒野高原"
            )
            Biome.MODIFIED_BADLANDS_PLATEAU -> LocalizedText(
                    Locale.JAPANESE to "変異した荒野高原"
            )
            Biome.BAMBOO_JUNGLE -> LocalizedText(
                    Locale.JAPANESE to "竹林"
            )
            Biome.BAMBOO_JUNGLE_HILLS -> LocalizedText(
                    Locale.JAPANESE to "竹林の丘陵"
            )
        }
    }
}