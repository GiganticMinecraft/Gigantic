package click.seichi.gigantic.message.messages

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.cache.manipulator.manipulators.Health
import click.seichi.gigantic.cache.manipulator.manipulators.Level
import click.seichi.gigantic.cache.manipulator.manipulators.WillAptitude
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.raid.RaidBattle
import click.seichi.gigantic.relic.RelicRarity
import click.seichi.gigantic.skill.SkillParameters
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object MenuMessages {

    val LINE = (1..23).joinToString("") { "-" }

    val PROFILE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}${ChatColor.UNDERLINE}プロフィールを見る"
    )

    val PROFILE_PROFILE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}プロフィール"
    )

    val RAID_BOSS = LocalizedText(
            Locale.JAPANESE to "${ChatColor.DARK_RED}${ChatColor.BOLD}${ChatColor.UNDERLINE}レイドボスと戦う"
    )

    val REST = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}${ChatColor.BOLD}${ChatColor.UNDERLINE}休憩する"
    )

    val BACK_FROM_REST = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.UNDERLINE}戻る"
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
        val color = when (boss.maxHealth) {
            in 0L..9999L -> ChatColor.GOLD
            in 10000L..99999L -> ChatColor.RED
            in 100000L..999999L -> ChatColor.LIGHT_PURPLE
            else -> ChatColor.DARK_PURPLE
        }
        LocalizedText(
                Locale.JAPANESE.let {
                    it to "$color${ChatColor.BOLD}${boss.localizedName.asSafety(it)}"
                }
        )
    }

    val BATTLE_BUTTON_LORE = { raidBattle: RaidBattle, health: Health ->
        val afterHealth = health.current.minus(raidBattle.boss.attackDamage).coerceAtLeast(0L)
        val bossDrop = raidBattle.boss.dropRelicSet
        mutableListOf<LocalizedText>().apply {
            if (afterHealth == 0L) {
                add(
                        LocalizedText(
                                Locale.JAPANESE to "${ChatColor.RED}${ChatColor.BOLD}" +
                                        "非推奨!!一撃で死亡!!"
                        )
                )
            }
            addAll(
                    mutableListOf(
                            LocalizedText(
                                    Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.BOLD}戦闘中 : ${raidBattle.getJoinedPlayerSet().size}人"
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
                                    Locale.JAPANESE to "${ChatColor.GRAY}落とすもの : "
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
            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.BOLD}${ChatColor.UNDERLINE}クリックで参加"
    )

    val BATTLE_BUTTON_LEFT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.UNDERLINE}クリックで離脱"
    )

    val BATTLE_BUTTON_DROPPED = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}復帰不可"
    )

    val BATTLE_BUTTON_JOINED = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}他の戦闘に参加中"
    )

    val PROFILE_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}${ChatColor.UNDERLINE}プロフィール"
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
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}倒したレイドボス"
    )

    val PROFILE_RAID_RELIC = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}手に入れたレリック"
    )

    val PROFILE_SKILL = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}覚えたスキル"
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
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}${ChatColor.UNDERLINE}スイッチ詳細設定"
    )

    val BELT_SWITCHER_SETTING_BUTTON_LORE = { canSwitch: Boolean ->
        if (canSwitch) {
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GREEN}選択済"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "クリックで切り替え"
                    )
            )

        } else {
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.RED}未選択"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "クリックで切り替え"
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
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
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
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                    "移動スキル: フラッシュ"
    )

    val HEAL =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: ブロックを破壊して回復"
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
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
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
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                    "切り替えスキル: スイッチ"
    )

    val TERRA_DRAIN =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: 木を倒し、自身を回復する"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量(原木): 最大体力の %.1f ".format(SkillParameters.TERRA_DRAIN_LOG_HEAL_PERCENT).plus("%")
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量(葉): 最大体力の %.1f ".format(SkillParameters.TERRA_DRAIN_LEAVES_HEAL_PERCENT).plus("%")
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "原木を破壊して発動"
                    )
            )

    val TERRA_DRAIN_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                    "回復スキル: テラドレイン"
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
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                    "スキル: ウィルオウィスプ"
    )

}