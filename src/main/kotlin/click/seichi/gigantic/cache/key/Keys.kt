package click.seichi.gigantic.cache.key

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.bag.Bag
import click.seichi.gigantic.bag.bags.MainBag
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.breaker.BreakArea
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.ExpReason
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.database.dao.*
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.quest.QuestClient
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.spirit.spirits.QuestMonsterSpirit
import click.seichi.gigantic.timer.LingeringTimer
import click.seichi.gigantic.timer.SimpleTimer
import click.seichi.gigantic.tool.Tool
import click.seichi.gigantic.will.Will
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.block.Block
import org.jetbrains.exposed.dao.Entity
import java.math.BigDecimal
import java.util.*

/**
 * @author tar0ss
 */
object Keys {

    val MAX_COMBO = object : DatabaseKey<PlayerCache, Long> {
        override val default: Long
            get() = 0L

        override fun read(entity: Entity<*>): Long {
            val user = entity as User
            return user.maxCombo
        }

        override fun store(entity: Entity<*>, value: Long) {
            val user = entity as User
            user.maxCombo = value
        }

        override fun satisfyWith(value: Long): Boolean {
            return value >= 0L
        }

    }

    val LOCALE = object : DatabaseKey<PlayerCache, Locale> {
        override val default
            get() = Locale.JAPANESE

        override fun read(entity: Entity<*>): Locale {
            val user = entity as User
            return Locale(user.localeString)
        }

        override fun store(entity: Entity<*>, value: Locale) {
            val user = entity as User
            user.localeString = value.language.substringBefore("_")
        }

        override fun satisfyWith(value: Locale): Boolean {
            return true
        }

    }

    val MANA = object : DatabaseKey<PlayerCache, BigDecimal> {
        override val default: BigDecimal
            get() = Defaults.MANA.toBigDecimal()

        override fun read(entity: Entity<*>): BigDecimal {
            val user = entity as User
            return user.mana
        }

        override fun store(entity: Entity<*>, value: BigDecimal) {
            val user = entity as User
            user.mana = value
        }

        override fun satisfyWith(value: BigDecimal): Boolean {
            return true
        }

    }

    val MAX_MANA = object : Key<PlayerCache, BigDecimal> {
        override val default: BigDecimal
            get() = Defaults.MANA.toBigDecimal()

        override fun satisfyWith(value: BigDecimal): Boolean {
            return true
        }

    }

    val HEALTH = object : DatabaseKey<PlayerCache, Long> {

        override val default: Long
            get() = 100L

        override fun read(entity: Entity<*>): Long {
            val user = entity as User
            return user.health
        }

        override fun store(entity: Entity<*>, value: Long) {
            val user = entity as User
            user.health = value
        }

        override fun satisfyWith(value: Long): Boolean {
            return value >= 0
        }

    }

    val MAX_HEALTH = object : Key<PlayerCache, Long> {

        override val default: Long
            get() = 100L

        override fun satisfyWith(value: Long): Boolean {
            return value >= 0
        }

    }

    val EXP_MAP: Map<ExpReason, DatabaseKey<PlayerCache, BigDecimal>> = ExpReason.values()
            .map {
                it to object : DatabaseKey<PlayerCache, BigDecimal> {
                    override val default: BigDecimal
                        get() = BigDecimal.ZERO

                    override fun read(entity: Entity<*>): BigDecimal {
                        val userExp = entity as UserExp
                        return userExp.exp
                    }

                    override fun store(entity: Entity<*>, value: BigDecimal) {
                        val userExp = entity as UserExp
                        userExp.exp = value
                    }

                    override fun satisfyWith(value: BigDecimal): Boolean {
                        return true
                    }

                }

            }
            .toMap()

    val MEMORY_MAP: Map<Will, DatabaseKey<PlayerCache, Long>> = Will.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Long> {
                    override val default: Long
                        get() = 0L

                    override fun read(entity: Entity<*>): Long {
                        val userWill = entity as UserWill
                        return userWill.memory
                    }

                    override fun store(entity: Entity<*>, value: Long) {
                        val userWill = entity as UserWill
                        userWill.memory = value
                    }

                    override fun satisfyWith(value: Long): Boolean {
                        return value >= 0L
                    }

                }
            }
            .toMap()

    val APTITUDE_MAP: Map<Will, DatabaseKey<PlayerCache, Boolean>> = Will.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Boolean> {
                    override val default: Boolean
                        get() = false

                    override fun read(entity: Entity<*>): Boolean {
                        val userWill = entity as UserWill
                        return userWill.hasAptitude
                    }

                    override fun store(entity: Entity<*>, value: Boolean) {
                        val userWill = entity as UserWill
                        userWill.hasAptitude = value
                    }

                    override fun satisfyWith(value: Boolean): Boolean {
                        return true
                    }

                }
            }
            .toMap()

    val SOUL_MONSTER: Map<SoulMonster, DatabaseKey<PlayerCache, Long>> = SoulMonster.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Long> {
                    override val default: Long
                        get() = 0L

                    override fun read(entity: Entity<*>): Long {
                        val userMonster = entity as UserMonster
                        return userMonster.defeat
                    }

                    override fun store(entity: Entity<*>, value: Long) {
                        val userMonster = entity as UserMonster
                        userMonster.defeat = value
                    }

                    override fun satisfyWith(value: Long): Boolean {
                        return value >= 0L
                    }

                }
            }
            .toMap()

    val RELIC_MAP: Map<Relic, DatabaseKey<PlayerCache, Long>> = Relic.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Long> {
                    override val default: Long
                        get() = 0L

                    override fun read(entity: Entity<*>): Long {
                        val userRelic = entity as UserRelic
                        return userRelic.amount
                    }

                    override fun store(entity: Entity<*>, value: Long) {
                        val userRelic = entity as UserRelic
                        userRelic.amount = value
                    }

                    override fun satisfyWith(value: Long): Boolean {
                        return value >= 0L
                    }

                }
            }
            .toMap()

    val ACHIEVEMENT_MAP: Map<Achievement, DatabaseKey<PlayerCache, Boolean>> = Achievement.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Boolean> {
                    override val default: Boolean
                        get() = false

                    override fun read(entity: Entity<*>): Boolean {
                        val userAchievement = entity as UserAchievement
                        return userAchievement.hasUnlocked
                    }

                    override fun store(entity: Entity<*>, value: Boolean) {
                        val userAchievement = entity as UserAchievement
                        userAchievement.hasUnlocked = value
                    }

                    override fun satisfyWith(value: Boolean): Boolean {
                        return true
                    }

                }
            }
            .toMap()

    val BELT_TOGGLE_MAP: Map<Belt, DatabaseKey<PlayerCache, Boolean>> = Belt.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Boolean> {
                    override val default: Boolean
                        get() = false

                    override fun read(entity: Entity<*>): Boolean {
                        val userBelt = entity as UserBelt
                        return userBelt.canSwitch
                    }

                    override fun store(entity: Entity<*>, value: Boolean) {
                        val userBelt = entity as UserBelt
                        userBelt.canSwitch = value
                    }

                    override fun satisfyWith(value: Boolean): Boolean {
                        return true
                    }

                }
            }
            .toMap()

    val BELT_UNLOCK_MAP: Map<Belt, DatabaseKey<PlayerCache, Boolean>> = Belt.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Boolean> {
                    override val default: Boolean
                        get() = false

                    override fun read(entity: Entity<*>): Boolean {
                        val userBelt = entity as UserBelt
                        return userBelt.isUnlocked
                    }

                    override fun store(entity: Entity<*>, value: Boolean) {
                        val userBelt = entity as UserBelt
                        userBelt.isUnlocked = value
                    }

                    override fun satisfyWith(value: Boolean): Boolean {
                        return true
                    }

                }
            }
            .toMap()

    val BAG = object : Key<PlayerCache, Bag> {
        override val default: Bag
            get() = MainBag

        override fun satisfyWith(value: Bag): Boolean {
            return true
        }

    }

    val BELT = object : DatabaseKey<PlayerCache, Belt> {
        override val default: Belt
            get() = Belt.findById(Defaults.BELT_ID)!!

        override fun read(entity: Entity<*>): Belt {
            val user = entity as User
            return Belt.findById(user.beltId) ?: default
        }

        override fun store(entity: Entity<*>, value: Belt) {
            val user = entity as User
            user.beltId = value.id
        }

        override fun satisfyWith(value: Belt): Boolean {
            return true
        }

    }

    val TOOL = object : DatabaseKey<PlayerCache, Tool> {
        override val default: Tool
            get() = Tool.findById(Defaults.TOOL_ID)!!

        override fun read(entity: Entity<*>): Tool {
            val user = entity as User
            return Tool.findById(user.toolId) ?: default
        }

        override fun store(entity: Entity<*>, value: Tool) {
            val user = entity as User
            user.toolId = value.id
        }

        override fun satisfyWith(value: Tool): Boolean {
            return true
        }

    }

    val TOOL_TOGGLE_MAP: Map<Tool, DatabaseKey<PlayerCache, Boolean>> = Tool.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Boolean> {
                    override val default: Boolean
                        get() = false

                    override fun read(entity: Entity<*>): Boolean {
                        val userTool = entity as UserTool
                        return userTool.canSwitch
                    }

                    override fun store(entity: Entity<*>, value: Boolean) {
                        val userTool = entity as UserTool
                        userTool.canSwitch = value
                    }

                    override fun satisfyWith(value: Boolean): Boolean {
                        return true
                    }

                }
            }
            .toMap()

    val TOOL_UNLOCK_MAP: Map<Tool, DatabaseKey<PlayerCache, Boolean>> = Tool.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Boolean> {
                    override val default: Boolean
                        get() = false

                    override fun read(entity: Entity<*>): Boolean {
                        val userTool = entity as UserTool
                        return userTool.isUnlocked
                    }

                    override fun store(entity: Entity<*>, value: Boolean) {
                        val userTool = entity as UserTool
                        userTool.isUnlocked = value
                    }

                    override fun satisfyWith(value: Boolean): Boolean {
                        return true
                    }

                }
            }
            .toMap()

    val DEATH_MESSAGE = object : Key<PlayerCache, LocalizedText?> {
        override val default: LocalizedText?
            get() = null

        override fun satisfyWith(value: LocalizedText?): Boolean {
            return true
        }

    }

    val BREAK_BLOCK = object : Key<PlayerCache, Block?> {
        override val default: Block?
            get() = null

        override fun satisfyWith(value: Block?): Boolean {
            return true
        }

    }


    val SPELL_TOGGLE = object : DatabaseKey<PlayerCache, Boolean> {
        override val default: Boolean
            get() = false

        override fun read(entity: Entity<*>): Boolean {
            val user = entity as User
            return user.spellToggle
        }

        override fun store(entity: Entity<*>, value: Boolean) {
            val user = entity as User
            user.spellToggle = value
        }

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }

    }

    val TELEPORT_TOGGLE = object : DatabaseKey<PlayerCache, Boolean> {
        override val default: Boolean
            get() = true

        override fun read(entity: Entity<*>): Boolean {
            val user = entity as User
            return user.teleportToggle
        }

        override fun store(entity: Entity<*>, value: Boolean) {
            val user = entity as User
            user.teleportToggle = value
        }

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }

    }


    val IS_MANA_STONE_TOGGLE_COOLDOWN = object : Key<PlayerCache, Boolean> {

        override val default: Boolean
            get() = true

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }

    }

    val MENU_PAGE = object : Key<PlayerCache, Int> {
        override val default: Int
            get() = 1

        override fun satisfyWith(value: Int): Boolean {
            return value > 0
        }

    }


    val QUEST_MAP: Map<Quest, DatabaseKey<PlayerCache, QuestClient?>> = Quest.values()
            .map {
                it to object : DatabaseKey<PlayerCache, QuestClient?> {
                    override val default: QuestClient?
                        get() = null

                    override fun read(entity: Entity<*>): QuestClient? {
                        val userQuest = entity as UserQuest
                        return QuestClient(
                                it,
                                userQuest.isOrdered,
                                userQuest.orderedAt,
                                userQuest.isProcessed,
                                userQuest.processedDegree,
                                userQuest.clearNum
                        )

                    }

                    override fun store(entity: Entity<*>, value: QuestClient?) {
                        value ?: return
                        val userQuest = entity as UserQuest
                        userQuest.isOrdered = value.isOrdered
                        userQuest.orderedAt = value.orderedAt
                        userQuest.isProcessed = value.isProcessed
                        userQuest.processedDegree = value.processedDegree
                        userQuest.clearNum = value.clearNum
                    }

                    override fun satisfyWith(value: QuestClient?): Boolean {
                        return true
                    }

                }
            }
            .toMap()

    val LAST_DEATH_CHUNK = object : Key<PlayerCache, Chunk?> {
        override val default: Chunk?
            get() = null

        override fun satisfyWith(value: Chunk?): Boolean {
            return true
        }
    }

    val BATTLE_SPIRIT = object : Key<PlayerCache, QuestMonsterSpirit?> {
        override val default: QuestMonsterSpirit?
            get() = null

        override fun satisfyWith(value: QuestMonsterSpirit?): Boolean {
            return true
        }
    }

    /**
     * 破壊スキルの破壊範囲
     * defaultが0,0,0なのでエラー出る可能性あり
     */
    val APOSTOLUS_BREAK_AREA = object : DatabaseKey<PlayerCache, BreakArea> {

        override val default: BreakArea
            get() = BreakArea(1, 1, 1)

        override fun read(entity: Entity<*>): BreakArea {
            val user = entity as User
            return BreakArea(
                    user.apostolusWidth,
                    user.apostolusHeight,
                    user.apostolusDepth
            )
        }

        override fun store(entity: Entity<*>, value: BreakArea) {
            val user = entity as User
            user.run {
                apostolusWidth = value.width
                apostolusHeight = value.height
                apostolusDepth = value.depth
            }
        }

        override fun satisfyWith(value: BreakArea): Boolean {
            return true
        }

    }

    val LEVEL = object : Key<PlayerCache, Int> {
        override val default: Int
            get() = 1

        override fun satisfyWith(value: Int): Boolean {
            return value >= 1 && value <= PlayerLevelConfig.MAX
        }

    }

    val SKILL_FLASH = object : Key<PlayerCache, SimpleTimer> {
        override val default: SimpleTimer
            get() = SimpleTimer()

        override fun satisfyWith(value: SimpleTimer): Boolean {
            return true
        }
    }

    val SKILL_MINE_BURST = object : Key<PlayerCache, LingeringTimer> {
        override val default: LingeringTimer
            get() = LingeringTimer()

        override fun satisfyWith(value: LingeringTimer): Boolean {
            return true
        }
    }

    val AFK_LOCATION = object : Key<PlayerCache, Location?> {
        override val default: Location?
            get() = null

        override fun satisfyWith(value: Location?): Boolean {
            return true
        }

    }

    val MINE_COMBO = object : Key<PlayerCache, Long> {
        override val default: Long
            get() = 0L

        override fun satisfyWith(value: Long): Boolean {
            return value >= 0L
        }

    }

    val LAST_COMBO_TIME = object : Key<PlayerCache, Long> {
        override val default: Long
            get() = System.currentTimeMillis()

        override fun satisfyWith(value: Long): Boolean {
            return true
        }

    }

    val INCREASE_COMBO = object : Key<PlayerCache, Long> {
        override val default: Long
            get() = 0L

        override fun satisfyWith(value: Long): Boolean {
            return true
        }

    }


}