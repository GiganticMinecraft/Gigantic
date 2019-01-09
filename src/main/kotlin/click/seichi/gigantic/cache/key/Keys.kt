package click.seichi.gigantic.cache.key

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.bag.Bag
import click.seichi.gigantic.bag.bags.MainBag
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.breaker.BreakArea
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.ExpReason
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.database.UserEntity
import click.seichi.gigantic.database.dao.User
import click.seichi.gigantic.database.dao.UserFollow
import click.seichi.gigantic.database.table.UserFollowTable
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.menu.RefineItem
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
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.joda.time.DateTime
import java.math.BigDecimal
import java.util.*

/**
 * @author tar0ss
 */
object Keys {

    val MAX_COMBO = object : DatabaseKey<PlayerCache, Long> {
        override val default: Long
            get() = 0L

        override fun read(entity: UserEntity): Long {
            val user = entity.user
            return user.maxCombo
        }

        override fun store(entity: UserEntity, value: Long) {
            val user = entity.user
            user.maxCombo = value
        }

        override fun satisfyWith(value: Long): Boolean {
            return value >= 0L
        }

    }

    val LOCALE = object : DatabaseKey<PlayerCache, Locale> {
        override val default
            get() = Locale.JAPANESE

        override fun read(entity: UserEntity): Locale {
            val user = entity.user
            return Locale(user.localeString)
        }

        override fun store(entity: UserEntity, value: Locale) {
            val user = entity.user
            user.localeString = value.language.substringBefore("_")
        }

        override fun satisfyWith(value: Locale): Boolean {
            return true
        }

    }

    val MANA = object : DatabaseKey<PlayerCache, BigDecimal> {
        override val default: BigDecimal
            get() = Defaults.MANA.toBigDecimal()

        override fun read(entity: UserEntity): BigDecimal {
            val user = entity.user
            return user.mana
        }

        override fun store(entity: UserEntity, value: BigDecimal) {
            val user = entity.user
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

    val EXP_MAP: Map<ExpReason, DatabaseKey<PlayerCache, BigDecimal>> = ExpReason.values()
            .map {
                it to object : DatabaseKey<PlayerCache, BigDecimal> {
                    override val default: BigDecimal
                        get() = BigDecimal.ZERO

                    override fun read(entity: UserEntity): BigDecimal {
                        val userExp = entity.userExpMap[it]!!
                        return userExp.exp
                    }

                    override fun store(entity: UserEntity, value: BigDecimal) {
                        val userExp = entity.userExpMap[it]!!
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

                    override fun read(entity: UserEntity): Long {
                        val userWill = entity.userWillMap[it]!!
                        return userWill.memory
                    }

                    override fun store(entity: UserEntity, value: Long) {
                        val userWill = entity.userWillMap[it]!!
                        userWill.memory = value
                    }

                    override fun satisfyWith(value: Long): Boolean {
                        return value >= 0L
                    }

                }
            }
            .toMap()

    val WILL_SECRET_MAP: Map<Will, DatabaseKey<PlayerCache, Long>> = Will.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Long> {
                    override val default: Long
                        get() = 0L

                    override fun read(entity: UserEntity): Long {
                        val userWill = entity.userWillMap[it]!!
                        return userWill.secretAmount
                    }

                    override fun store(entity: UserEntity, value: Long) {
                        val userWill = entity.userWillMap[it]!!
                        userWill.secretAmount = value
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

                    override fun read(entity: UserEntity): Boolean {
                        val userWill = entity.userWillMap[it]!!
                        return userWill.hasAptitude
                    }

                    override fun store(entity: UserEntity, value: Boolean) {
                        val userWill = entity.userWillMap[it]!!
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

                    override fun read(entity: UserEntity): Long {
                        val userMonster = entity.userMonsterMap[it]!!
                        return userMonster.defeat
                    }

                    override fun store(entity: UserEntity, value: Long) {
                        val userMonster = entity.userMonsterMap[it]!!
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

                    override fun read(entity: UserEntity): Long {
                        val userRelic = entity.userRelicMap[it]!!
                        return userRelic.amount
                    }

                    override fun store(entity: UserEntity, value: Long) {
                        val userRelic = entity.userRelicMap[it]!!
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

                    override fun read(entity: UserEntity): Boolean {
                        val userAchievement = entity.userAchievementMap[it]!!
                        return userAchievement.hasUnlocked
                    }

                    override fun store(entity: UserEntity, value: Boolean) {
                        val userAchievement = entity.userAchievementMap[it]!!
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

                    override fun read(entity: UserEntity): Boolean {
                        val userBelt = entity.userBeltMap[it]!!
                        return userBelt.canSwitch
                    }

                    override fun store(entity: UserEntity, value: Boolean) {
                        val userBelt = entity.userBeltMap[it]!!
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

                    override fun read(entity: UserEntity): Boolean {
                        val userBelt = entity.userBeltMap[it]!!
                        return userBelt.isUnlocked
                    }

                    override fun store(entity: UserEntity, value: Boolean) {
                        val userBelt = entity.userBeltMap[it]!!
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

        override fun read(entity: UserEntity): Belt {
            val user = entity.user
            return Belt.findById(user.beltId) ?: default
        }

        override fun store(entity: UserEntity, value: Belt) {
            val user = entity.user
            user.beltId = value.id
        }

        override fun satisfyWith(value: Belt): Boolean {
            return true
        }

    }

    val TOOL = object : DatabaseKey<PlayerCache, Tool> {
        override val default: Tool
            get() = Tool.findById(Defaults.TOOL_ID)!!

        override fun read(entity: UserEntity): Tool {
            val user = entity.user
            return Tool.findById(user.toolId) ?: default
        }

        override fun store(entity: UserEntity, value: Tool) {
            val user = entity.user
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
                        get() = true

                    override fun read(entity: UserEntity): Boolean {
                        val userTool = entity.userToolMap[it]!!
                        return userTool.canSwitch
                    }

                    override fun store(entity: UserEntity, value: Boolean) {
                        val userTool = entity.userToolMap[it]!!
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

                    override fun read(entity: UserEntity): Boolean {
                        val userTool = entity.userToolMap[it]!!
                        return userTool.isUnlocked
                    }

                    override fun store(entity: UserEntity, value: Boolean) {
                        val userTool = entity.userToolMap[it]!!
                        userTool.isUnlocked = value
                    }

                    override fun satisfyWith(value: Boolean): Boolean {
                        return true
                    }

                }
            }
            .toMap()

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

        override fun read(entity: UserEntity): Boolean {
            val user = entity.user
            return user.spellToggle
        }

        override fun store(entity: UserEntity, value: Boolean) {
            val user = entity.user
            user.spellToggle = value
        }

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }

    }

    val TELEPORT_TOGGLE = object : DatabaseKey<PlayerCache, Boolean> {
        override val default: Boolean
            get() = false

        override fun read(entity: UserEntity): Boolean {
            val user = entity.user
            return user.teleportToggle
        }

        override fun store(entity: UserEntity, value: Boolean) {
            val user = entity.user
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

    val PLAYER_LIST_MENU_MAP = object : Key<PlayerCache, Map<Int, UUID>> {
        override val default: Map<Int, UUID>
            get() = mapOf()

        override fun satisfyWith(value: Map<Int, UUID>): Boolean {
            return true
        }
    }

    val PLAYER_LIST = object : Key<PlayerCache, List<Player>> {
        override val default: List<Player>
            get() = listOf()

        override fun satisfyWith(value: List<Player>): Boolean {
            return true
        }
    }

    val REFINE_ITEM_MAP: Map<RefineItem, Key<PlayerCache, Boolean>> = RefineItem.values()
            .map {
                it to object : Key<PlayerCache, Boolean> {
                    override val default: Boolean
                        get() = false

                    override fun satisfyWith(value: Boolean): Boolean {
                        return true
                    }
                }
            }.toMap()

    val QUEST_MAP: Map<Quest, DatabaseKey<PlayerCache, QuestClient?>> = Quest.values()
            .map {
                it to object : DatabaseKey<PlayerCache, QuestClient?> {
                    override val default: QuestClient?
                        get() = null

                    override fun read(entity: UserEntity): QuestClient? {
                        val userQuest = entity.userQuestMap[it]!!
                        return QuestClient(
                                it,
                                userQuest.isOrdered,
                                userQuest.orderedAt,
                                userQuest.isProcessed,
                                userQuest.processedDegree,
                                userQuest.clearNum
                        )

                    }

                    override fun store(entity: UserEntity, value: QuestClient?) {
                        value ?: return
                        val userQuest = entity.userQuestMap[it]!!
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
     */
    val SPELL_APOSTOL_BREAK_AREA = object : DatabaseKey<PlayerCache, BreakArea> {

        override val default: BreakArea
            get() = BreakArea(1, 1, 1)

        override fun read(entity: UserEntity): BreakArea {
            val user = entity.user
            return BreakArea(
                    user.apostolWidth,
                    user.apostolHeight,
                    user.apostolDepth
            )
        }

        override fun store(entity: UserEntity, value: BreakArea) {
            val user = entity.user
            user.run {
                apostolWidth = value.width
                apostolHeight = value.height
                apostolDepth = value.depth
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

    val EFFECT_BOUGHT_MAP: Map<GiganticEffect, DatabaseKey<PlayerCache, Boolean>> = GiganticEffect.values()
            .map { effect ->
                effect to
                        object : DatabaseKey<PlayerCache, Boolean> {

                            override val default: Boolean
                                get() = false

                            override fun read(entity: UserEntity): Boolean {
                                val userEffect = entity.userEffectMap[effect]!!
                                return userEffect.isBought
                            }

                            override fun store(entity: UserEntity, value: Boolean) {
                                val userEffect = entity.userEffectMap[effect]!!
                                userEffect.isBought = value
                            }

                            override fun satisfyWith(value: Boolean): Boolean {
                                return true
                            }

                        }
            }.toMap()

    val EFFECT_BOUGHT_TIME_MAP: Map<GiganticEffect, DatabaseKey<PlayerCache, DateTime>> = GiganticEffect.values()
            .map { effect ->
                effect to
                        object : DatabaseKey<PlayerCache, DateTime> {

                            override val default: DateTime
                                get() = DateTime.now()

                            override fun read(entity: UserEntity): DateTime {
                                val userEffect = entity.userEffectMap[effect]!!
                                return userEffect.boughtAt
                            }

                            override fun store(entity: UserEntity, value: DateTime) {
                                val userEffect = entity.userEffectMap[effect]!!
                                userEffect.boughtAt = value
                            }

                            override fun satisfyWith(value: DateTime): Boolean {
                                return true
                            }

                        }
            }.toMap()

    val EFFECT = object : DatabaseKey<PlayerCache, GiganticEffect> {
        override val default: GiganticEffect
            get() = GiganticEffect.DEFAULT

        override fun read(entity: UserEntity): GiganticEffect {
            val user = entity.user
            return GiganticEffect.findById(user.effectId) ?: default
        }

        override fun store(entity: UserEntity, value: GiganticEffect) {
            val user = entity.user
            user.effectId = value.id
        }

        override fun satisfyWith(value: GiganticEffect): Boolean {
            return true
        }
    }

    // データの読み込みしか行わない特殊なケース
    val VOTE = object : DatabaseKey<PlayerCache, Int> {
        override val default: Int
            get() = 0

        override fun read(entity: UserEntity): Int {
            val user = entity.user
            return user.vote
        }

        override fun store(entity: UserEntity, value: Int) {
            // データベースが書き換えられていた場合，上書き削除してしまうので
            // 書き込まなくてよい．ポイントは減ることもないし増えることもない．
            Gigantic.PLUGIN.logger.warning("投票数のデータベース書き込みは禁止されています")
        }

        override fun satisfyWith(value: Int): Boolean {
            // 強制的に書き換えを拒否
            Gigantic.PLUGIN.logger.warning("投票数の書き換えは禁止されています")
            return false
        }
    }
    // データの読み込みしか行わない特殊なケース
    val POMME = object : DatabaseKey<PlayerCache, Int> {
        override val default: Int
            get() = 0

        override fun read(entity: UserEntity): Int {
            val user = entity.user
            return user.pomme
        }

        override fun store(entity: UserEntity, value: Int) {
            // データベースが書き換えられていた場合，上書き削除してしまうので
            // 書き込まなくてよい．ポイントは減ることもないし増えることもない．
            Gigantic.PLUGIN.logger.warning("ポムのデータベース書き込みは禁止されています")
        }

        override fun satisfyWith(value: Int): Boolean {
            // 強制的に書き換えを拒否
            Gigantic.PLUGIN.logger.warning("ポムの書き換えは禁止されています")
            return false
        }
    }
    // データの読み込みしか行わない特殊なケース
    val DONATION = object : DatabaseKey<PlayerCache, Int> {
        override val default: Int
            get() = 0

        override fun read(entity: UserEntity): Int {
            val user = entity.user
            return user.donation
        }

        override fun store(entity: UserEntity, value: Int) {
            // データベースが書き換えられていた場合，上書き削除してしまうので
            // 書き込まなくてよい．ポイントは減ることもないし増えることもない．
            Gigantic.PLUGIN.logger.warning("寄付金のデータベース書き込みは禁止されています")
        }

        override fun satisfyWith(value: Int): Boolean {
            // 強制的に書き換えを拒否
            Gigantic.PLUGIN.logger.warning("寄付金の書き換えは禁止されています")
            return false
        }
    }

    val PROFILE_IS_UPDATING = object : Key<PlayerCache, Boolean> {
        override val default: Boolean
            get() = false

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }
    }

    val DONATE_TICKET_LIST = object : Key<PlayerCache, List<DonateTicket>> {
        override val default: List<DonateTicket>
            get() = listOf()

        override fun satisfyWith(value: List<DonateTicket>): Boolean {
            return true
        }
    }

    val SPELL_APOSTOL_BREAK_BLOCKS = object : Key<PlayerCache, Set<Block>> {
        override val default: Set<Block>
            get() = setOf()

        override fun satisfyWith(value: Set<Block>): Boolean {
            return true
        }
    }

    val FOLLOW_SET = object : DatabaseKey<PlayerCache, Set<UUID>> {
        override val default: Set<UUID>
            get() = setOf()

        override fun read(entity: UserEntity): Set<UUID> {
            return entity.userFollowList.map { it.follow.id.value }.toSet()
        }

        override fun store(entity: UserEntity, value: Set<UUID>) {
            val oldSet = read(entity)
            // 新規フォロワーを登録
            value.forEach { followId ->
                // 既にフォローしていたら終了
                if (oldSet.contains(followId)) return@forEach
                // フォローするユーザーを検索
                val followUser = User.findById(followId) ?: return@forEach
                // 存在すれば追加
                UserFollow.new {
                    user = entity.user
                    follow = followUser
                }
            }
            // フォローを外したプレイヤーを削除
            oldSet.forEach { followId ->
                // フォローしているなら終了
                if (value.contains(followId)) return@forEach
                // 削除するユーザーを検索
                val followedUser = User.findById(followId) ?: return@forEach
                // 存在すれば削除
                UserFollowTable.deleteWhere {
                    (UserFollowTable.userId eq entity.user.id).and(UserFollowTable.followId eq followedUser.id)
                }
            }
        }

        override fun satisfyWith(value: Set<UUID>): Boolean {
            return true
        }
    }


    val FOLLOWER_SET = object : DatabaseKey<PlayerCache, Set<UUID>> {
        override val default: Set<UUID>
            get() = setOf()

        override fun read(entity: UserEntity): Set<UUID> {
            return entity.userFollowList.map { it.user.id.value }.toSet()
        }

        override fun store(entity: UserEntity, value: Set<UUID>) {
            // 書き込まなくてよい．
            Gigantic.PLUGIN.logger.warning("フォロワーのデータベース書き込みは禁止されています")
        }

        override fun satisfyWith(value: Set<UUID>): Boolean {
            // 強制的に書き換えを拒否
            Gigantic.PLUGIN.logger.warning("フォロワーの書き換えは禁止されています")
            return false
        }
    }

    val LAST_TELL_ID = object : Key<PlayerCache, UUID?> {
        override val default: UUID?
            get() = null

        override fun satisfyWith(value: UUID?): Boolean {
            return true
        }
    }

    val LAST_BREAK_CHUNK = object : Key<PlayerCache, Chunk?> {
        override val default: Chunk?
            get() = null

        override fun satisfyWith(value: Chunk?): Boolean {
            return true
        }
    }

    val ELYTRA_CHARGE_UP_TICKS = object : Key<PlayerCache, Long> {
        override val default: Long
            get() = -1L

        override fun satisfyWith(value: Long): Boolean {
            return true
        }
    }

    val AUTO_SWITCH = object : DatabaseKey<PlayerCache, Boolean> {
        override val default: Boolean
            get() = true

        override fun read(entity: UserEntity): Boolean {
            val user = entity.user
            return user.autoSwitch
        }

        override fun store(entity: UserEntity, value: Boolean) {
            val user = entity.user
            user.autoSwitch = value
        }

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }
    }

    val UPDATE_COUNT = object : Key<PlayerCache, Int> {
        override val default: Int
            get() = 0

        override fun satisfyWith(value: Int): Boolean {
            return true
        }
    }

}