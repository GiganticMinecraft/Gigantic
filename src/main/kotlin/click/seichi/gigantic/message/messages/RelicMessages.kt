package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.monster.SoulMonster
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object RelicMessages {

    val DROP_TEXT = { drop: SoulMonster.DropRelic ->
        val relic = drop.relic
        LocalizedText(
                Locale.JAPANESE.let { it to "${ChatColor.LIGHT_PURPLE}${relic.getName(it)}を手に入れた!!" }
        )
    }

    val DROP = { drop: SoulMonster.DropRelic ->
        ChatMessage(ChatMessageProtocol.CHAT, DROP_TEXT(drop))
    }

    val SPELL_BOOK_EXPLOSION = LocalizedText(
            Locale.JAPANESE to "魔導書-エクスプロージョン-"
    )

    val GOLDEN_APPLE = LocalizedText(
            Locale.JAPANESE to "黄金の林檎"
    )

    val SPELL_BOOK_AQUA_LINEA = LocalizedText(
            Locale.JAPANESE to "水の魔導書-アクア・リネア-"
    )

    val WILL_CRYSTAL_SAPPHIRE = LocalizedText(
            Locale.JAPANESE to "水の意志結晶-サファイア-"
    )

    val SPELL_BOOK_IGNIS_VOLCANO = LocalizedText(
            Locale.JAPANESE to "火の魔導書-イグニス・ヴォルケーノ-"
    )

    val WILL_CRYSTAL_RUBY = LocalizedText(
            Locale.JAPANESE to "火の意志結晶-ルビー-"
    )

    val SPELL_BOOK_AER_SLASH = LocalizedText(
            Locale.JAPANESE to "空の魔導書-エアル・スラッシュ-"
    )

    val WILL_CRYSTAL_FLUORITE = LocalizedText(
            Locale.JAPANESE to "空の意志結晶-フローライト-"
    )

    val SPELL_BOOK_TERRA_DRAIN = LocalizedText(
            Locale.JAPANESE to "土の魔導書-テラ・ドレイン-"
    )

    val WILL_CRYSTAL_ANDALUSITE = LocalizedText(
            Locale.JAPANESE to "土の意志結晶-アンダルサイト-"
    )

    val SPELL_BOOK_GRAND_NATURA = LocalizedText(
            Locale.JAPANESE to "自然の魔導書-グランド・ナトラ-"
    )

    val WILL_CRYSTAL_JADE = LocalizedText(
            Locale.JAPANESE to "自然の意志結晶-ヒスイ-"
    )

    val PIGS_FEATHER = LocalizedText(
            Locale.JAPANESE to "豚の羽根"
    )

    val PIGS_FEATHER_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "スキル フラッシュ"
            )
    )

    val BLUE_BLAZE_POWDER = LocalizedText(
            Locale.JAPANESE to "ブルーブレイズのパウダー"
    )

    val BLUE_BLAZE_POWDER_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "スキル マインバースト"
            )
    )

    val CHICKEN_KING_CROWN = LocalizedText(
            Locale.JAPANESE to "鶏王の王冠"
    )

    val CHICKEN_KING_CROWN_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "10コンボ以上の攻撃時に"
            ),
            LocalizedText(
                    Locale.JAPANESE to "1の追加ダメージ"
            )
    )

    val TURTLE_KING_CROWN = LocalizedText(
            Locale.JAPANESE to "亀王の王冠"
    )

    val TURTLE_KING_CROWN_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "30コンボ以上の攻撃時に"
            ),
            LocalizedText(
                    Locale.JAPANESE to "1の追加ダメージ"
            )
    )

    val SPIDER_KING_CROWN = LocalizedText(
            Locale.JAPANESE to "蜘蛛王の王冠"
    )

    val SPIDER_KING_CROWN_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "70コンボ以上の攻撃時に"
            ),
            LocalizedText(
                    Locale.JAPANESE to "1の追加ダメージ"
            )
    )

    val ZOMBIE_KING_CROWN = LocalizedText(
            Locale.JAPANESE to "ゾンビ王の王冠"
    )

    val ZOMBIE_KING_CROWN_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "150コンボ以上の攻撃時に"
            ),
            LocalizedText(
                    Locale.JAPANESE to "1の追加ダメージ"
            )
    )

    val SKELETON_KING_CROWN = LocalizedText(
            Locale.JAPANESE to "スケルトン王の王冠"
    )

    val SKELETON_KING_CROWN_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "350コンボ以上の攻撃時に"
            ),
            LocalizedText(
                    Locale.JAPANESE to "1の追加ダメージ"
            )
    )

    val ORC_KING_CROWN = LocalizedText(
            Locale.JAPANESE to "オーク王の王冠"
    )

    val ORC_KING_CROWN_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "800コンボ以上の攻撃時に"
            ),
            LocalizedText(
                    Locale.JAPANESE to "1の追加ダメージ"
            )
    )

    val GHOST_KING_CROWN = LocalizedText(
            Locale.JAPANESE to "ゴースト王の王冠"
    )

    val GHOST_KING_CROWN_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "1200コンボ以上の攻撃時に"
            ),
            LocalizedText(
                    Locale.JAPANESE to "1の追加ダメージ"
            )
    )

    val CHIP_OF_WOOD = LocalizedText(
            Locale.JAPANESE to "精なる木の屑"
    )

    val CHIP_OF_WOOD_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "スキル コダマ・ドレイン"
            )
    )

    val MOISTENED_SLIME_BOLL = LocalizedText(
            Locale.JAPANESE to "潤沢なべとべと"
    )

    val MOISTENED_SLIME_BOLL_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "整地効率が１上がる"
            )
    )

    val FADING_ENDER_PEARL = LocalizedText(
            Locale.JAPANESE to "色褪せたエンダーパール"
    )

    val FADING_ENDER_PEARL_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "整地効率が１上がる"
            )
    )

    val MANA_STONE = LocalizedText(
            Locale.JAPANESE to "マナ・ストーン"
    )

    val WITHER_SKELETON_SKULL = LocalizedText(
            Locale.JAPANESE to "ウィザースケルトンの頭"
    )

    val ROTTEN_FLESH = LocalizedText(
            Locale.JAPANESE to "村人ゾンビの肉"
    )

    val BEAST_HORN = LocalizedText(Locale.JAPANESE to "魔獣の角")
    val ELIXIR = LocalizedText(Locale.JAPANESE to "エリクサー")
    val SHELL = LocalizedText(Locale.JAPANESE to "貝殻")
    val CACAO_WATERMELON = LocalizedText(Locale.JAPANESE to "カカオスイカ")
    val RUSTED_COMPASS = LocalizedText(Locale.JAPANESE to "錆びた羅針盤")
    val USED_COIN = LocalizedText(Locale.JAPANESE to "すり減った金貨")
    val SUMMER_DAY = LocalizedText(Locale.JAPANESE to "夏の思い出")
    val A_PIECE_OF_CHALK = LocalizedText(Locale.JAPANESE to "白亜のかけら")
    val OLD_MESSAGE_BOTTLE = LocalizedText(Locale.JAPANESE to "古びたメッセージボトル")
    val BROKEN_BOW = LocalizedText(Locale.JAPANESE to "朽ちかけた弓")
    val BIRCH_MUSHROOM = LocalizedText(Locale.JAPANESE to "白樺茸")
    val ADVENTURER_SOLE = LocalizedText(Locale.JAPANESE to "冒険家の靴底")
    val BEAUTIFUL_WING = LocalizedText(Locale.JAPANESE to "きれいな羽根")
    val WHITE_APPLE = LocalizedText(Locale.JAPANESE to "白いリンゴ")
    val TIME_CAPSEL = LocalizedText(Locale.JAPANESE to "タイムカプセル")
    val BEAST_BONE = LocalizedText(Locale.JAPANESE to "猛獣の骨")
    val THIN_TOOTH = LocalizedText(Locale.JAPANESE to "鋭利な歯")
    val BROKEN_IVORY = LocalizedText(Locale.JAPANESE to "くだけた象牙")
    val BOTTLED_LIQUID = LocalizedText(Locale.JAPANESE to "瓶詰めの謎の液体")
    val WILL_O_WISP = LocalizedText(Locale.JAPANESE to "ウィル・オ・ウィスプ")
    val SLIME_LEES = LocalizedText(Locale.JAPANESE to "スライムの残滓")
    val TUMBLEWEED = LocalizedText(Locale.JAPANESE to "タンブルウィード")
    val SUPER_MUSHROOM = LocalizedText(Locale.JAPANESE to "大きくなるキノコ")
    val PURPLE_CHEESE = LocalizedText(Locale.JAPANESE to "紫チーズ")
    val HORN = LocalizedText(Locale.JAPANESE to "角笛")
    val BROKEN_FORCE_FLAG = LocalizedText(Locale.JAPANESE to "破れた軍旗")
    val SYLPH_LEAFE = LocalizedText(Locale.JAPANESE to "シルフの葉")
    val BROKEN_TRAP = LocalizedText(Locale.JAPANESE to "壊れたトラップ")
    val RAINBOW_CLAY = LocalizedText(Locale.JAPANESE to "虹色粘土")
    val SHADE_ARMOR = LocalizedText(Locale.JAPANESE to "シェイドの暗鎧")
    val CLAY_IMAGE = LocalizedText(Locale.JAPANESE to "埴輪")
    val BIG_FUNG = LocalizedText(Locale.JAPANESE to "巨大なキバ")
    val FLUX_FOSSIL = LocalizedText(Locale.JAPANESE to "フンの化石")
    val BITTEN_LEATHER_BOOT = LocalizedText(Locale.JAPANESE to "歯形のついた革靴")
    val BUDDHIST_STATUE = LocalizedText(Locale.JAPANESE to "仏像")
    val BROKEN_LEAD = LocalizedText(Locale.JAPANESE to "壊れたリード")
    val EGGPLANT = LocalizedText(Locale.JAPANESE to "茄子")
    val OLD_AXE = LocalizedText(Locale.JAPANESE to "古びた斧")
    val CRYSTAL_OF_SNOW = LocalizedText(Locale.JAPANESE to "雪と氷の結晶")
    val FROSTED_FISH = LocalizedText(Locale.JAPANESE to "氷漬けの魚")
    val VODKA_BOTTLE = LocalizedText(Locale.JAPANESE to "ウォッカの瓶")
    val SAIL = LocalizedText(Locale.JAPANESE to "帆船の帆")
    val TREASURE_CASKET = LocalizedText(Locale.JAPANESE to "たまてばこ")
    val DEEP_SEA_FISH_DIODE = LocalizedText(Locale.JAPANESE to "深海魚の発光器")
    val SEICHI_MACKEREL = LocalizedText(Locale.JAPANESE to "セイチ鯖")
    val NOT_MELTTED_ICE = LocalizedText(Locale.JAPANESE to "とけないこおり")
    val WHITE_FLOWER = LocalizedText(Locale.JAPANESE to "白い花")
    val FROSTED_ORE = LocalizedText(Locale.JAPANESE to "凍った鉱石")
    val MAMMMOTH_RAW_MEET = LocalizedText(Locale.JAPANESE to "マンモスの肉")
    val TENT_CLOTH = LocalizedText(Locale.JAPANESE to "テントの布")
    val CAMP_FIRE_TRACE = LocalizedText(Locale.JAPANESE to "焚き火の跡")
    val FROSTED_PINECONE = LocalizedText(Locale.JAPANESE to "凍った松ぼっくり")
    val FROSTED_CRAFTBOX = LocalizedText(Locale.JAPANESE to "凍った作業台")
    val MUSH_FISH = LocalizedText(Locale.JAPANESE to "ムーシュフィッシュ")
    val MYCELIUM_PICKAXE = LocalizedText(Locale.JAPANESE to "菌糸だらけのツルハシ")
    val ACID_GEAR = LocalizedText(Locale.JAPANESE to "錆びた歯車")
    val DESERT_CRYSTAL = LocalizedText(Locale.JAPANESE to "デザートクリスタル")
    val CAT_SAND = LocalizedText(Locale.JAPANESE to "猫砂")
    val ORICHALCUM = LocalizedText(Locale.JAPANESE to "オリハルコン")
    val BEAUTIFUL_ORE = LocalizedText(Locale.JAPANESE to "綺麗な鉱石")
    val BANANA_SKIN = LocalizedText(Locale.JAPANESE to "バナナの皮")
    val INSECT_HORN = LocalizedText(Locale.JAPANESE to "虫のツノ")
    val ICICLE = LocalizedText(Locale.JAPANESE to "つらら")
    val FROSTED_WHEEL = LocalizedText(Locale.JAPANESE to "凍った車輪")
    val DARK_MATTER = LocalizedText(Locale.JAPANESE to "ダークマター")
    val STEERING_WHEEL = LocalizedText(Locale.JAPANESE to "船の舵輪")
    val SOFT_RIME = LocalizedText(Locale.JAPANESE to "樹氷")
    val JIZO = LocalizedText(Locale.JAPANESE to "地蔵")
    val CRAMPONS = LocalizedText(Locale.JAPANESE to "アイゼン")
    val SLICED_ROPE = LocalizedText(Locale.JAPANESE to "ちぎれたロープ")
    val BLACK_CLOTH = LocalizedText(Locale.JAPANESE to "黒い布")
    val LIGHTNING_MOSS = LocalizedText(Locale.JAPANESE to "光る苔")
    val BROWN_SAP = LocalizedText(Locale.JAPANESE to "茶色の樹液")
    val BLOODSTAINED_SWORD = LocalizedText(Locale.JAPANESE to "血のついた剣")
    val WEB_SCRAP = LocalizedText(Locale.JAPANESE to "網の切れ端")
    val DOWN_TREE = LocalizedText(Locale.JAPANESE to "倒木")
    val CARNIVORE_BONE = LocalizedText(Locale.JAPANESE to "肉食獣の骸骨")
    val IRON_ARMOR = LocalizedText(Locale.JAPANESE to "黒鉄の塊")
    val INDIGO = LocalizedText(Locale.JAPANESE to "インディゴ")
    val DIAMOND_STONE = LocalizedText(Locale.JAPANESE to "金剛石")
    val ULURU_SCRAP = LocalizedText(Locale.JAPANESE to "ウルルのかけら")
    val RED_DUST = LocalizedText(Locale.JAPANESE to "赤い粉塵")
    val BLUE_DUST = LocalizedText(Locale.JAPANESE to "青い粉塵")
    val FRIED_POTATO = LocalizedText(Locale.JAPANESE to "フライドポテト")
    val SPHINX = LocalizedText(Locale.JAPANESE to "スフィンクス像")
    val PRICKLE = LocalizedText(Locale.JAPANESE to "棘")
    val WOOD_SLAB = LocalizedText(Locale.JAPANESE to "木板")
    val BROKEN_WATERMELON = LocalizedText(Locale.JAPANESE to "砕けたスイカ")
    val RLYEH_TEXT = LocalizedText(Locale.JAPANESE to "ルルイエ異本")
    val CUTE_WATERING_POT = LocalizedText(Locale.JAPANESE to "素敵なじょうろ")
    val WING = LocalizedText(Locale.JAPANESE to "鳥獣の翼")
    val NIDUS_AVIS = LocalizedText(Locale.JAPANESE to "鳥の巣")
    val BEAST_HORN_LORE = listOf(LocalizedText(Locale.JAPANESE to "蝸牛のように湾曲している角"))
    val ELIXIR_LORE = listOf(LocalizedText(Locale.JAPANESE to "遠い土地から流されてきた"))
    val SHELL_LORE = listOf(LocalizedText(Locale.JAPANESE to "綺麗な模様の貝殻", Locale.JAPANESE to "誰かが食べた跡が見える"))
    val CACAO_WATERMELON_LORE = listOf(LocalizedText(Locale.JAPANESE to "カカオとスイカが混ざっている", Locale.JAPANESE to "美味しいのかよくわからない"))
    val RUSTED_COMPASS_LORE = listOf(LocalizedText(Locale.JAPANESE to "かろうじて原型を留めている羅針盤", Locale.JAPANESE to "針は動かない"))
    val USED_COIN_LORE = listOf(LocalizedText(Locale.JAPANESE to "どこかの国で使われていただろう金貨", Locale.JAPANESE to ""))
    val SUMMER_DAY_LORE = listOf(LocalizedText(Locale.JAPANESE to "砂や小さな貝殻がつまった瓶"))
    val A_PIECE_OF_CHALK_LORE = listOf(LocalizedText(Locale.JAPANESE to "それは一つの像だったのかも知れない"))
    val OLD_MESSAGE_BOTTLE_LORE = listOf(LocalizedText(Locale.JAPANESE to "文章は滲んで読めなくなっている"))
    val BROKEN_BOW_LORE = listOf(LocalizedText(Locale.JAPANESE to "今にも折れそうな弓", Locale.JAPANESE to "見た事のない素材が弦として使われている", Locale.JAPANESE to "原住民の遺物"))
    val BIRCH_MUSHROOM_LORE = listOf(LocalizedText(Locale.JAPANESE to "キノコ、のこのこ、元気の子"))
    val ADVENTURER_SOLE_LORE = listOf(LocalizedText(Locale.JAPANESE to "かつてここを通った冒険家の靴底", Locale.JAPANESE to "さぞ歩きにくかったに違いない"))
    val BEAUTIFUL_WING_LORE = listOf(LocalizedText(Locale.JAPANESE to "照らすと青が映える綺麗な羽根"))
    val WHITE_APPLE_LORE = listOf(LocalizedText(Locale.JAPANESE to "ごくまれに実ると言われる貴重な林檎"))
    val TIME_CAPSEL_LORE = listOf(LocalizedText(Locale.JAPANESE to "中には石や枝がごちゃごちゃ入っている"))
    val BEAST_BONE_LORE = listOf(LocalizedText(Locale.JAPANESE to "トラのような骨", Locale.JAPANESE to "狩りをしたときの取り残しだろうか"))
    val THIN_TOOTH_LORE = listOf(LocalizedText(Locale.JAPANESE to "大きくて鋭い歯", Locale.JAPANESE to "先が割れてしまっている"))
    val BROKEN_IVORY_LORE = listOf(LocalizedText(Locale.JAPANESE to "多くの傷が付いている象牙"))
    val BOTTLED_LIQUID_LORE = listOf(LocalizedText(Locale.JAPANESE to "魔女のおとしもの"))
    val WILL_O_WISP_LORE = listOf(LocalizedText(Locale.JAPANESE to "光の精の魂"))
    val SLIME_LEES_LORE = listOf(LocalizedText(Locale.JAPANESE to "さわやかな味がする"))
    val TUMBLEWEED_LORE = listOf(LocalizedText(Locale.JAPANESE to "西部劇でよく見るアレ"))
    val SUPER_MUSHROOM_LORE = listOf(LocalizedText(Locale.JAPANESE to "とある王国から発見されたキノコ"))
    val PURPLE_CHEESE_LORE = listOf(LocalizedText(Locale.JAPANESE to "毒々しく特有の腐臭がする"))
    val HORN_LORE = listOf(LocalizedText(Locale.JAPANESE to "動物の角でできたよくある笛", Locale.JAPANESE to "いい音色が出そう"))
    val BROKEN_FORCE_FLAG_LORE = listOf(LocalizedText(Locale.JAPANESE to "持ち手に少し傷がつき、", Locale.JAPANESE to "布も虫に食われている"))
    val SYLPH_LEAFE_LORE = listOf(LocalizedText(Locale.JAPANESE to "風の精霊が落とした葉っぱ。", Locale.JAPANESE to "魔力が込められている。"))
    val BROKEN_TRAP_LORE = listOf(LocalizedText(Locale.JAPANESE to "昔の遺跡に仕掛けられたものだろうか", Locale.JAPANESE to "トラバサミによく似ている"))
    val RAINBOW_CLAY_LORE = listOf(LocalizedText(Locale.JAPANESE to "長い年月をかけ虹色になった"))
    val SHADE_ARMOR_LORE = listOf(LocalizedText(Locale.JAPANESE to "闇の精の装備品", Locale.JAPANESE to "見るだけで震えが止まらない…"))
    val CLAY_IMAGE_LORE = listOf(LocalizedText(Locale.JAPANESE to "上質な堅焼き粘土で出来ている", Locale.JAPANESE to ""))
    val BIG_FUNG_LORE = listOf(LocalizedText(Locale.JAPANESE to "キバの持ち主は", Locale.JAPANESE to "想像がつかないほど大きいだろう"))
    val FLUX_FOSSIL_LORE = listOf(LocalizedText(Locale.JAPANESE to "フンの化石"))
    val BITTEN_LEATHER_BOOT_LORE = listOf(LocalizedText(Locale.JAPANESE to "まさか動物が食べようとしたのだろうか"))
    val BUDDHIST_STATUE_LORE = listOf(LocalizedText(Locale.JAPANESE to "古い仏像らしい", Locale.JAPANESE to "もうとっくに錆びている", Locale.JAPANESE to "あとで供養をしてあげよう"))
    val BROKEN_LEAD_LORE = listOf(LocalizedText(Locale.JAPANESE to "壊れたリード", Locale.JAPANESE to "散歩の途中にでも切れたのだろうか"))
    val EGGPLANT_LORE = listOf(LocalizedText(Locale.JAPANESE to "ナス科ナス属の植物またその果実", Locale.JAPANESE to "ただの茄子"))
    val OLD_AXE_LORE = listOf(LocalizedText(Locale.JAPANESE to "刃が欠けていて", Locale.JAPANESE to "もう使用することはできない。"))
    val CRYSTAL_OF_SNOW_LORE = listOf(LocalizedText(Locale.JAPANESE to "雪の結晶"))
    val FROSTED_FISH_LORE = listOf(LocalizedText(Locale.JAPANESE to "大きい魚が小さい魚に", Locale.JAPANESE to "喰らい付いたまま凍っている", Locale.JAPANESE to "とても芸術的"))
    val VODKA_BOTTLE_LORE = listOf(LocalizedText(Locale.JAPANESE to "北の国から流れてきた空き瓶"))
    val SAIL_LORE = listOf(LocalizedText(Locale.JAPANESE to "大航海時代", Locale.JAPANESE to "商船の帆の切れ端", Locale.JAPANESE to "少し胡椒の匂いがする"))
    val TREASURE_CASKET_LORE = listOf(LocalizedText(Locale.JAPANESE to "これであなたもおじいちゃんに…？"))
    val DEEP_SEA_FISH_DIODE_LORE = listOf(LocalizedText(Locale.JAPANESE to "深海魚の頭部に生えた発光器のようだ", Locale.JAPANESE to "これで餌を集めているのであろう"))
    val SEICHI_MACKEREL_LORE = listOf(LocalizedText(Locale.JAPANESE to "鯖", Locale.JAPANESE to "それ以外の何者でもない"))
    val NOT_MELTTED_ICE_LORE = listOf(LocalizedText(Locale.JAPANESE to "とても冷たい", Locale.JAPANESE to "持っているだけで凍りつきそうだ。"))
    val WHITE_FLOWER_LORE = listOf(LocalizedText(Locale.JAPANESE to "雪のように白い"))
    val FROSTED_ORE_LORE = listOf(LocalizedText(Locale.JAPANESE to "貴重なダイヤモンドが凍り", Locale.JAPANESE to "氷の中できらめいている。"))
    val MAMMMOTH_RAW_MEET_LORE = listOf(LocalizedText(Locale.JAPANESE to "凍った伝説のﾏｿﾓｽのお肉", Locale.JAPANESE to "なんだかとってもおいしそう"))
    val TENT_CLOTH_LORE = listOf(LocalizedText(Locale.JAPANESE to "既に放棄された拠点のようだ", Locale.JAPANESE to "布は霜に覆われている"))
    val CAMP_FIRE_TRACE_LORE = listOf(LocalizedText(Locale.JAPANESE to "既に放棄された拠点のようだ", Locale.JAPANESE to "炭は乾かせばまだ使えそうにも見える"))
    val FROSTED_PINECONE_LORE = listOf(LocalizedText(Locale.JAPANESE to "松ぼっくりに白い霜が付いている", Locale.JAPANESE to "太陽にかざすとキラキラして綺麗だ"))
    val FROSTED_CRAFTBOX_LORE = listOf(LocalizedText(Locale.JAPANESE to "とあるクラフターが使ったと言われる作業台", Locale.JAPANESE to "ver1.3と刻印されている"))
    val MUSH_FISH_LORE = listOf(LocalizedText(Locale.JAPANESE to "キノコ島の菌にやられた魚", Locale.JAPANESE to "見た目と裏腹にかなり美味しい"))
    val MYCELIUM_PICKAXE_LORE = listOf(LocalizedText(Locale.JAPANESE to "整地しようとしたが", Locale.JAPANESE to "菌糸に飲まれたらしい"))
    val ACID_GEAR_LORE = listOf(LocalizedText(Locale.JAPANESE to "どこかにたくさんのゴーレムが", Locale.JAPANESE to "隠されているらしい"))
    val DESERT_CRYSTAL_LORE = listOf(LocalizedText(Locale.JAPANESE to "琥珀とはまた違った輝きを持った石だ"))
    val CAT_SAND_LORE = listOf(LocalizedText(Locale.JAPANESE to "にゃあ"))
    val ORICHALCUM_LORE = listOf(LocalizedText(Locale.JAPANESE to "とても珍しい鉱石", Locale.JAPANESE to "まだ見つけられたことはないらしい"))
    val BEAUTIFUL_ORE_LORE = listOf(LocalizedText(Locale.JAPANESE to "琥珀色の鉱石"))
    val BANANA_SKIN_LORE = listOf(LocalizedText(Locale.JAPANESE to "ジャングルの動物達に", Locale.JAPANESE to "人気な果実である"))
    val INSECT_HORN_LORE = listOf(LocalizedText(Locale.JAPANESE to "黒光りしている"))
    val ICICLE_LORE = listOf(LocalizedText(Locale.JAPANESE to "不思議な色をしていて", Locale.JAPANESE to "太陽に照らすと神秘的な光を放つ"))
    val FROSTED_WHEEL_LORE = listOf(LocalizedText(Locale.JAPANESE to "馬車の車輪だろうか", Locale.JAPANESE to "細かい彫刻がある"))
    val DARK_MATTER_LORE = listOf(LocalizedText(Locale.JAPANESE to "暗黒物質", Locale.JAPANESE to "手に入れた者には不幸が訪れるという"))
    val STEERING_WHEEL_LORE = listOf(LocalizedText(Locale.JAPANESE to "海賊船の舵輪"))
    val SOFT_RIME_LORE = listOf(LocalizedText(Locale.JAPANESE to "付近の樹木とはまったく違う凍り方だ"))
    val JIZO_LORE = listOf(LocalizedText(Locale.JAPANESE to "笠をかけてあげよう"))
    val CRAMPONS_LORE = listOf(LocalizedText(Locale.JAPANESE to "まだ新しい", Locale.JAPANESE to "雪山もしっかり登れそうだ"))
    val SLICED_ROPE_LORE = listOf(LocalizedText(Locale.JAPANESE to "巨木に登ろうとしたのだろうか", Locale.JAPANESE to "無残にもちぎれている"))
    val BLACK_CLOTH_LORE = listOf(LocalizedText(Locale.JAPANESE to "何やら怪しいオーラの漂う布"))
    val LIGHTNING_MOSS_LORE = listOf(LocalizedText(Locale.JAPANESE to "その苔は光も受けずに", Locale.JAPANESE to "不気味に輝いていた"))
    val BROWN_SAP_LORE = listOf(LocalizedText(Locale.JAPANESE to "透き通ったきれいな樹液", Locale.JAPANESE to "蟻がたかっている"))
    val BLOODSTAINED_SWORD_LORE = listOf(LocalizedText(Locale.JAPANESE to "時間が経ち真っ黒になった血が", Locale.JAPANESE to "べっとりとこびりついている"))
    val WEB_SCRAP_LORE = listOf(LocalizedText(Locale.JAPANESE to "漁業で使われる大きな網の切れ端"))
    val DOWN_TREE_LORE = listOf(LocalizedText(Locale.JAPANESE to "虫に食われたような穴があるが", Locale.JAPANESE to "まだ腐ってはいない"))
    val CARNIVORE_BONE_LORE = listOf(LocalizedText(Locale.JAPANESE to "大きな頭の骨", Locale.JAPANESE to "きっと食物連鎖の頂点だったに違いない"))
    val IRON_ARMOR_LORE = listOf(LocalizedText(Locale.JAPANESE to "真っ黒の鉄の塊"))
    val INDIGO_LORE = listOf(LocalizedText(Locale.JAPANESE to "黒い染料として使われていた"))
    val DIAMOND_STONE_LORE = listOf(LocalizedText(Locale.JAPANESE to "昔はダイヤのことをそう呼んでいた"))
    val ULURU_SCRAP_LORE = listOf(LocalizedText(Locale.JAPANESE to "大きな一枚岩のひとかけら"))
    val RED_DUST_LORE = listOf(LocalizedText(Locale.JAPANESE to "触れると少しだけチクッとする"))
    val BLUE_DUST_LORE = listOf(LocalizedText(Locale.JAPANESE to "魔法が使えそうな気がする"))
    val FRIED_POTATO_LORE = listOf(LocalizedText(Locale.JAPANESE to "見てるだけでもよだれが出てくる"))
    val SPHINX_LORE = listOf(LocalizedText(Locale.JAPANESE to "ピラミッドの近くにありそう"))
    val PRICKLE_LORE = listOf(LocalizedText(Locale.JAPANESE to "木のトゲ", Locale.JAPANESE to "小さいがよく尖っている"))
    val WOOD_SLAB_LORE = listOf(LocalizedText(Locale.JAPANESE to "ボートに使ったものらしい", Locale.JAPANESE to "乗っていた人は無事だろうか"))
    val BROKEN_WATERMELON_LORE = listOf(LocalizedText(Locale.JAPANESE to "太古の昔誰かが割ったスイカの化石"))
    val RLYEH_TEXT_LORE = listOf(LocalizedText(Locale.JAPANESE to "人の皮で装丁されている", Locale.JAPANESE to "読まないほうがよさそうだ"))
    val CUTE_WATERING_POT_LORE = listOf(LocalizedText(Locale.JAPANESE to "使っていた人は", Locale.JAPANESE to "さぞ大事にしていたことだろう"))
    val WING_LORE = listOf(LocalizedText(Locale.JAPANESE to "見たこともない大きさの翼"))
    val NIDUS_AVIS_LORE = listOf(LocalizedText(Locale.JAPANESE to "小さな枝が沢山敷き詰められている"))

    val BEAST_HORN_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "冷帯な環境"))
    val ELIXIR_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "快適な気温"))
    val SHELL_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通の砂浜"))
    val CACAO_WATERMELON_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "ジャングル"))
    val RUSTED_COMPASS_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "海洋"))
    val USED_COIN_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "金鉱石"))
    val SUMMER_DAY_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "暑い"))
    val A_PIECE_OF_CHALK_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "高度が低い"))
    val OLD_MESSAGE_BOTTLE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "石の海岸"))
    val BROKEN_BOW_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "高度が普通"))
    val BIRCH_MUSHROOM_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "白樺の森"))
    val ADVENTURER_SOLE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "芝生"))
    val BEAUTIFUL_WING_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "高度が高い"))
    val WHITE_APPLE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "白樺の森の丘陵"))
    val TIME_CAPSEL_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "花の森"))
    val BEAST_BONE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "荒廃したサバンナ"))
    val THIN_TOOTH_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "荒廃したサバンナ台地"))
    val BROKEN_IVORY_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "荒野台地"))
    val BOTTLED_LIQUID_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "湿地帯"))
    val WILL_O_WISP_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "かぼちゃ"))
    val SLIME_LEES_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "湿地帯の丘陵"))
    val TUMBLEWEED_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "乾燥した環境"))
    val SUPER_MUSHROOM_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "平原"))
    val PURPLE_CHEESE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "菌糸"))
    val HORN_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "温帯な環境"))
    val BROKEN_FORCE_FLAG_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "苔石"))
    val SYLPH_LEAFE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通の森"))
    val BROKEN_TRAP_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "虫食い"))
    val RAIBOW_CLAY_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "粘土"))
    val SHADE_ARMOR_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "薄暗い森の丘陵"))
    val CLAY_IMAGE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通の荒野"))
    val BIG_FUNG_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通の砂漠"))
    val FLUX_FOSSIL_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "凍った海"))
    val BITTEN_LEATHER_BOOT_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通のサバンナ台地"))
    val BUDDHIST_STATUE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通の山"))
    val BROKEN_LEAD_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "森のある丘陵"))
    val EGGPLANT_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "森のある山"))
    val OLD_AXE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通のタイガ"))
    val CRYSTAL_OF_SNOW_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "寒い"))
    val FROSTED_FISH_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "凍った川"))
    val VODKA_BOTTLE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "冷たい海"))
    val SAIL_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "ぬるい深海"))
    val TREASURE_CASKET_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通の深海"))
    val DEEP_SEA_FISH_DIODE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "冷たい深海"))
    val SEICHI_MACKEREL_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通の海"))
    val NOT_MELTTED_ICE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "とても寒い"))
    val WHITE_FLOWER_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "雪のツンドラ"))
    val FROSTED_ORE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "青氷"))
    val MAMMMOTH_RAW_MEET_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "雪のタイガ"))
    val TENT_CLOTH_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "雪山"))
    val CAMP_FIRE_TRACE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "タイガの丘陵"))
    val FROSTED_PINECONE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "巨大樹のタイガ"))
    val FROSTED_CRAFTBOX_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "雪の降る砂浜"))
    val MUSH_FISH_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "キノコ島"))
    val MYCELIUM_PICKAXE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "キノコ島の海岸"))
    val ACID_GEAR_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通のジャングルの丘陵"))
    val DESERT_CRYSTAL_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "砂漠の丘陵"))
    val CAT_SAND_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "粗い土"))
    val ORICHALCUM_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "高度がとても低い"))
    val BEAUTIFUL_ORE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "エメラルド鉱石"))
    val BANANA_SKIN_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "変異ジャングル"))
    val INSECT_HORN_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "巨大な白樺の森"))
    val ICICLE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "氷雪な環境"))
    val FROSTED_WHEEL_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "巨大樹のタイガの丘陵"))
    val DARK_MATTER_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "巨大マツのタイガ"))
    val STEERING_WHEEL_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "暖かい海"))
    val SOFT_RIME_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "樹氷"))
    val JIZO_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "砂利の山"))
    val CRAMPONS_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "雪のタイガの丘陵"))
    val SLICED_ROPE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通のタイガの山"))
    val BLACK_CLOTH_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "凍った深海"))
    val LIGHTNING_MOSS_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "巨大マツのタイガの丘陵"))
    val BROWN_SAP_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "巨大な白樺の森の丘陵"))
    val BLOODSTAINED_SWORD_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "浸食された荒野"))
    val WEB_SCRAP_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "ぬるい海"))
    val DOWN_TREE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "雪のタイガの山"))
    val CARNIVORE_BONE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "サバンナ"))
    val IRON_ARMOR_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "鉄鉱石"))
    val INDIGO_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "石炭"))
    val DIAMOND_STONE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "ダイヤ鉱石"))
    val ULURU_SCRAP_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "変異した荒野の高原"))
    val RED_DUST_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "レッドストーン鉱石"))
    val BLUE_DUST_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "ラピスラズリ鉱石"))
    val FRIED_POTATO_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "とても暑い"))
    val SPHINX_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "砂漠の湖"))
    val PRICKLE_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "変異した木が生えている荒野の高原"))
    val WOOD_SLAB_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "普通の川"))
    val BROKEN_WATERMELON_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "スイカ"))
    val RLYEH_TEXT_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "薄暗い森"))
    val CUTE_WATERING_POT_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "ひまわり平原"))
    val WING_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "変異した砂利の山"))
    val NIDUS_AVIS_BONUS_LORE = listOf(LocalizedText(Locale.JAPANESE to "木が生えている荒野の高原"))

}