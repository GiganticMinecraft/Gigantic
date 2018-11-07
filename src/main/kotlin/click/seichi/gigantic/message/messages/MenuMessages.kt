package click.seichi.gigantic.message.messages

import click.seichi.gigantic.cache.manipulator.manipulators.*
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object MenuMessages {

    val LINE = (1..23).joinToString("") { "-" }

    val PROFILE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.UNDERLINE}プロフィールを見る"
    )

    val PROFILE_PROFILE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}プロフィール"
    )

    val RAID_BOSS = LocalizedText(
            Locale.JAPANESE to "レイドボスと戦う"
    )

    val REST = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}${ChatColor.UNDERLINE}休憩する"
    )

    val BACK_FROM_REST = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.UNDERLINE}戻る"
    )

    val BACK_BUTTON = { menuTitle: String ->
        LocalizedText(
                Locale.JAPANESE to "$menuTitle${ChatColor.RESET}${ChatColor.WHITE}に戻る"
        )
    }

    val NEXT_BUTTON = LocalizedText(
            Locale.JAPANESE to "次へ"
    )

    val PREV_BUTTON = LocalizedText(
            Locale.JAPANESE to "前へ"
    )


    val BATTLE_BUTTON_TITLE = { boss: Boss ->
        val color = when (boss.rank) {
            1 -> ChatColor.GOLD
            2 -> ChatColor.RED
            3 -> ChatColor.LIGHT_PURPLE
            else -> ChatColor.DARK_PURPLE
        }
        LocalizedText(
                Locale.JAPANESE.let {
                    it to "${color}Rank${boss.rank} ${ChatColor.BOLD}${boss.localizedName.asSafety(it)}"
                }
        )
    }

    val BATTLE_BUTTON_LORE = { raidBattle: RaidBattle, health: Health ->
        val afterHealth = health.current.minus(raidBattle.boss.attackDamage).coerceAtLeast(0L)
        val bossDrop = raidBattle.boss.dropRelicSet
        mutableListOf<LocalizedText>().apply {
            if (afterHealth == 0L) {
                addAll(
                        listOf(
                                LocalizedText(
                                        Locale.JAPANESE to "${ChatColor.RED}" +
                                                "非推奨!!一撃で死亡!!"
                                ),
                                LocalizedText(
                                        Locale.JAPANESE to "${ChatColor.RED}" +
                                                "(現在の体力:${health.current}/${health.max})"
                                )
                        )
                )
            }
            addAll(
                    mutableListOf(
                            LocalizedText(
                                    Locale.JAPANESE to "${ChatColor.WHITE}戦闘中 : ${raidBattle.getJoinedPlayerSet().size}人"
                            ),
                            LocalizedText(
                                    Locale.JAPANESE to "${ChatColor.GRAY}残りHP : ${raidBattle.raidBoss.health}"
                            ),
                            LocalizedText(
                                    Locale.JAPANESE to "${ChatColor.GRAY}攻撃力 : ${raidBattle.boss.attackDamage}"
                            ),
                            LocalizedText(
                                    Locale.JAPANESE to "${ChatColor.GRAY}攻撃間隔 : ${raidBattle.boss.attackInterval}秒"
                            ),
                            LocalizedText(
                                    Locale.JAPANESE to "${ChatColor.GRAY}落とすレリック : "
                            )
                    )
            )
            bossDrop.forEach { drop ->
                val color = when (drop.relic.rarity) {
                    RelicRarity.NORMAL -> ChatColor.GRAY
                    RelicRarity.RARE -> ChatColor.DARK_PURPLE
                }
                add(
                        LocalizedText(
                                Locale.JAPANESE.let { locale ->
                                    locale to "$color${drop.relic.localizedName.asSafety(locale)}"
                                }
                        )
                )
            }
        }
    }

    val BATTLE_BUTTON_JOIN = LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで参加"
    )

    val BATTLE_BUTTON_LEFT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.UNDERLINE}クリックで離脱(※復帰できません)"
    )

    val BATTLE_BUTTON_DROPPED = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}復帰不可"
    )

    val BATTLE_BUTTON_JOINED = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}他の戦闘に参加中"
    )

    val PROFILE_TITLE = LocalizedText(
            Locale.JAPANESE to "プロフィール"
    )

    val SPECIAL_THANKS_TITLE = LocalizedText(
            Locale.JAPANESE to "Special Thanks"
    )

    val PROFILE_LEVEL = { level: Level ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}整地レベル: ${ChatColor.WHITE}${level.current}"
        )
    }

    val PROFILE_EXP = { level: Level ->
        val isMax = level.current == PlayerLevelConfig.MAX
        if (isMax) {
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}経験値: ${ChatColor.WHITE}${level.exp} / ${level.exp}"
            )
        } else {
            val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level.current + 1]
                    ?: PlayerLevelConfig.LEVEL_MAP[PlayerLevelConfig.MAX]!!
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}経験値: ${ChatColor.WHITE}${level.exp} / $expToNextLevel"
            )
        }
    }

    val PROFILE_HEALTH = { health: Health ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}体力: ${ChatColor.WHITE}${health.current} / ${health.max}"
        )
    }
    val PROFILE_MANA = { mana: Mana ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}マナ: ${ChatColor.WHITE}${mana.current} / ${mana.max}"
        )
    }

    val PROFILE_MAX_COMBO = { mineCombo: MineCombo ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}最大コンボ数: ${ChatColor.WHITE}${mineCombo.maxCombo} combo"
        )
    }

    val PROFILE_WILL_APTITUDE = { aptitude: WillAptitude ->
        arrayOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GREEN}適正遺志"
                ),
                LocalizedText(
                        Locale.JAPANESE.let { locale ->
                            locale to Will.values()
                                    .filter { it.grade == WillGrade.BASIC }
                                    .joinToString(" ") {
                                        if (aptitude.has(it))
                                            "${ChatColor.WHITE}${it.localizedName.asSafety(locale)}"
                                        else
                                            "${ChatColor.DARK_GRAY}${it.localizedName.asSafety(locale)}"
                                    }
                        }
                ),
                LocalizedText(
                        Locale.JAPANESE.let { locale ->
                            locale to Will.values()
                                    .filter { it.grade == WillGrade.ADVANCED }
                                    .joinToString(" ") {
                                        if (aptitude.has(it))
                                            "${ChatColor.WHITE}${it.localizedName.asSafety(locale)}"
                                        else
                                            "${ChatColor.DARK_GRAY}${it.localizedName.asSafety(locale)}"
                                    }
                        }
                )

        )
    }

    val PROFILE_RAID_BOSS = LocalizedText(
            Locale.JAPANESE to "倒したレイドボス"
    )

    val PROFILE_RAID_RELIC = LocalizedText(
            Locale.JAPANESE to "手に入れたレリック"
    )

    val PROFILE_SKILL = LocalizedText(
            Locale.JAPANESE to "覚えたスキル"
    )

    val PROFILE_SPELL = LocalizedText(
            Locale.JAPANESE to "覚えた魔法"
    )

    val PROFILE_RAID_BOSS_DEFEATED = { defeatCount: Long ->
        LocalizedText(
                Locale.JAPANESE to "討伐数: $defeatCount"
        )
    }

    val PROFILE_RAID_RELIC_AMOUNT = { amount: Long ->
        LocalizedText(
                Locale.JAPANESE to "$amount 個"
        )
    }

    val BELT_SWITCHER_SETTING = LocalizedText(
            Locale.JAPANESE to "スイッチ詳細設定"
    )

    val BELT_SWITCHER_SETTING_BUTTON_LORE = { canSwitch: Boolean ->
        if (canSwitch) {
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GREEN}選択済"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}----------------"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
                    )
            )

        } else {
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.RED}未選択"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}----------------"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
                    )
            )
        }
    }

    val MINE_BURST =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "アクティブ効果: 少しの間だけ掘る速度が上昇"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "持続時間: ${SkillParameters.MINE_BURST_DURATION}秒"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "クールタイム: ${SkillParameters.MINE_BURST_COOLTIME}秒"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "\"${SkillParameters.MINE_BURST_KEY}\" キー を押して発動"
                    )
            )

    val MINE_BURST_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "バフスキル: マインバースト"
    )

    val FLASH =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "アクティブ効果: 視点方向にワープ"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "クールタイム: ${SkillParameters.FLASH_COOLTIME}秒"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "\"${SkillParameters.FLASH_KEY}\" キー を押して発動"
                    )
            )

    val FLASH_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "移動スキル: フラッシュ"
    )

    val HEAL =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: ブロックを破壊して体力を回復"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "発動確率: ${SkillParameters.HEAL_PROBABILITY_PERCENT} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量: 最大体力の${SkillParameters.HEAL_AMOUNT_PERCENT} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "全ての通常破壊で発動"
                    )
            )

    val HEAL_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "回復スキル: ヒール"
    )

    val SWITCH =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "アクティブ効果: 持ち物を入れ替える"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "\"${SkillParameters.SWITCH_KEY}\" キー を押して発動"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.BLUE}" +
                                    "---\"${SkillParameters.SWITCH_SETTING_KEY}\" キー を押して詳細設定を開く"
                    )
            )

    val SWITCH_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "切り替えスキル: スイッチ"
    )

    val TERRA_DRAIN =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: 木を倒し、自身の体力を回復する"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "マナ消費量 ${SpellParameters.TERRA_DRAIN_MANA_PER_BLOCK} / ブロック"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量(原木): 最大体力の %.1f ".format(SpellParameters.TERRA_DRAIN_LOG_HEAL_PERCENT).plus("%")
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量(葉): 最大体力の %.1f ".format(SpellParameters.TERRA_DRAIN_LEAVES_HEAL_PERCENT).plus("%")
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "原木を破壊して発動"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.BLUE}" +
                                    "---スニークで通常破壊"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}----------------"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
                    )
            )

    val TERRA_DRAIN_TITLE_ON = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "回復魔法: テラ・ドレイン"
    )

    val TERRA_DRAIN_TITLE_OFF = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "回復魔法: テラ・ドレイン -封印-"
    )

    val WILL_O_THE_WISP =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: ブロックを破壊すると、稀に遺志が現れる"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "遺志と交感すると記憶を獲得"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.BLUE}" +
                                    "---遺志は${Will.values().size}種類存在する"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.DARK_GRAY}" +
                                    "レベルを上げてたくさんの遺志と交感しよう"
                    )
            )

    val WILL_O_THE_WISP_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "スキル: ウィルオウィスプ"
    )


    val STELLA_CLAIR =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: ブロックを破壊してマナを回復"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "発動確率: ${SpellParameters.STELLA_CLAIR_PROBABILITY_PERCENT} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量: 最大マナの${SpellParameters.STELLA_CLAIR_AMOUNT_PERCENT} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "全ての通常破壊で発動"
                    )
            )

    val STELLA_CLAIR_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "回復魔法: ステラ・クレア"
    )

    val GRAND_NATURA =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: 植物を吸収する"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "マナ消費量 ${SpellParameters.GRAND_NATURA_MANA_PER_BLOCK} / ブロック"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "芝生又はキノコブロックを破壊時に発動"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.BLUE}" +
                                    "---スニークで通常破壊"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}----------------"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
                    )
            )

    val GRAND_NATURA_TITLE_ON = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "破壊魔法: グランド・ナトラ"
    )

    val GRAND_NATURA_TITLE_OFF = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "破壊魔法: グランド・ナトラ -封印-"
    )


    val AQUA_LINEA =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: ブロックが泡となって消える"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "マナ消費量 ${SpellParameters.AQUA_LINEA_MANA_PER_BLOCK} / ブロック"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "破壊時に発動"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.BLUE}" +
                                    "---スニークで通常破壊"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}----------------"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
                    )
            )

    val AQUA_LINEA_TITLE_ON = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "破壊魔法: アクア・リネーア"
    )

    val AQUA_LINEA_TITLE_OFF = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "破壊魔法: アクア・リネーア -封印-"
    )

    val TELEPORT_TITLE = LocalizedText(
            Locale.JAPANESE to "テレポート対象を選択"
    )

    val TELEPORT_TO_PLAYER = LocalizedText(
            Locale.JAPANESE to "プレイヤーを選択"
    )

    val TELEPORT_TO_PLAYER_TITLE = LocalizedText(
            Locale.JAPANESE to "テレポートするプレイヤーを選ぶ"
    )

    val TELEPORT_PLAYER_INVALID_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED} プレイヤーが存在しません"
            )
    )

    val TELEPORT_PLAYER_AFK_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED} 休憩中のためテレポート不可"
            )
    )

    val TELEPORT_PLAYER_NOT_SURVIVAL_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED} プレイヤーがサバイバルモードではありません"
            )
    )

    val TELEPORT_PLAYER_INVALID_WORLD_LORE = { world: World ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.RED} 別のワールド(${world.name})にいます"
                )
        )
    }

    val TELEPORT_PLAYER_FLYING_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED} Fly中のためテレポート不可"
            )
    )

    val TELEPORT_PLAYER_TOGGLE_OFF_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED} テレポートが許可されていません"
            )
    )

    val TELEPORT_PLAYER_LORE = { to: Player ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.WHITE} ワールド: ${to.world.name}"
                )
        )
    }

    val TELEPORT_TOGGLE_ON = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}他プレイヤーのテレポートを常に許可"
    )

    val TELEPORT_TOGGLE_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}----------------"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
            )
    )

    val TELEPORT_TOGGLE_OFF = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}他プレイヤーのテレポートを常に拒否"
    )

}