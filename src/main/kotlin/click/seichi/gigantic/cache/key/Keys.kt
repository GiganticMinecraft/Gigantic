package click.seichi.gigantic.cache.key

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.bag.Bag
import click.seichi.gigantic.bag.bags.MainBag
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.breaker.BreakArea
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.cache.RankCache
import click.seichi.gigantic.cache.manipulator.ExpReason
import click.seichi.gigantic.config.DebugConfig
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.database.RankEntity
import click.seichi.gigantic.database.UserEntity
import click.seichi.gigantic.database.dao.user.User
import click.seichi.gigantic.database.dao.user.UserFollow
import click.seichi.gigantic.database.dao.user.UserHome
import click.seichi.gigantic.database.dao.user.UserMute
import click.seichi.gigantic.database.table.user.UserFollowTable
import click.seichi.gigantic.database.table.user.UserHomeTable
import click.seichi.gigantic.database.table.user.UserMuteTable
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.menu.RefineItem
import click.seichi.gigantic.menu.RelicCategory
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.player.DonateTicket
import click.seichi.gigantic.player.Home
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.quest.QuestClient
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.spirit.spirits.QuestMonsterSpirit
import click.seichi.gigantic.timer.LingeringTimer
import click.seichi.gigantic.timer.SimpleTimer
import click.seichi.gigantic.tool.Tool
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillRelationship
import org.bukkit.Bukkit
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

    val ETHEL_MAP: Map<Will, DatabaseKey<PlayerCache, Long>> = Will.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Long> {
                    override val default: Long
                        get() = 0L

                    override fun read(entity: UserEntity): Long {
                        val userWill = entity.userWillMap[it]!!

                        return if (Gigantic.IS_DEBUG && DebugConfig.WILL_SPIRIT) 1000 else userWill.ethel
                    }

                    override fun store(entity: UserEntity, value: Long) {
                        val userWill = entity.userWillMap[it]!!
                        userWill.ethel = value
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
                        return if (Gigantic.IS_DEBUG && DebugConfig.WILL_SPIRIT) 100L else userRelic.amount
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

    val MENU_PLAYER_LIST = object : Key<PlayerCache, List<Player>> {
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
    val SPELL_MULTI_BREAK_AREA = object : DatabaseKey<PlayerCache, BreakArea> {

        override val default: BreakArea
            get() = BreakArea(1, 1, 1)

        override fun read(entity: UserEntity): BreakArea {
            val user = entity.user
            return BreakArea(
                    user.multiBreakWidth,
                    user.multiBreakHeight,
                    user.multiBreakDepth
            )
        }

        override fun store(entity: UserEntity, value: BreakArea) {
            val user = entity.user
            user.run {
                multiBreakWidth = value.width
                multiBreakHeight = value.height
                multiBreakDepth = value.depth
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

    val COMBO = object : DatabaseKey<PlayerCache, Long> {
        override val default: Long
            get() = 0L

        override fun read(entity: UserEntity): Long {
            val user = entity.user
            return user.combo
        }

        override fun store(entity: UserEntity, value: Long) {
            val user = entity.user
            user.combo = value
        }

        override fun satisfyWith(value: Long): Boolean {
            return value >= 0L
        }

    }

    val LAST_COMBO_TIME = object : DatabaseKey<PlayerCache, Long> {
        override val default: Long
            get() = System.currentTimeMillis()

        override fun read(entity: UserEntity): Long {
            val user = entity.user
            return user.lastComboTime
        }

        override fun store(entity: UserEntity, value: Long) {
            val user = entity.user
            user.lastComboTime = value
        }

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

    val DONATE_TICKET_LIST = object : Key<PlayerCache, List<DonateTicket>> {
        override val default: List<DonateTicket>
            get() = listOf()

        override fun satisfyWith(value: List<DonateTicket>): Boolean {
            return true
        }
    }

    val SPELL_MULTI_BREAK_BLOCKS = object : Key<PlayerCache, Set<Block>> {
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

    val MUTE_SET = object : DatabaseKey<PlayerCache, Set<UUID>> {
        override val default: Set<UUID>
            get() = setOf()

        override fun read(entity: UserEntity): Set<UUID> {
            return entity.userMuteList.map { it.mute.id.value }.toSet()
        }

        override fun store(entity: UserEntity, value: Set<UUID>) {
            val oldSet = read(entity)
            // 新規Muteを登録
            value.forEach { muteId ->
                // 既にMuteしていたら終了
                if (oldSet.contains(muteId)) return@forEach
                // ミュートするユーザーを検索
                val muteUser = User.findById(muteId) ?: return@forEach
                // 存在すれば追加
                UserMute.new {
                    user = entity.user
                    mute = muteUser
                }
            }
            // ミュートを外したプレイヤーを削除
            oldSet.forEach { muteId ->
                // 現在ミュートしているなら終了
                if (value.contains(muteId)) return@forEach
                // 削除するユーザーを検索
                val muteUser = User.findById(muteId) ?: return@forEach
                // 存在すれば削除
                UserMuteTable.deleteWhere {
                    (UserMuteTable.userId eq entity.user.id).and(UserMuteTable.muteId eq muteUser.id)
                }
            }
        }

        override fun satisfyWith(value: Set<UUID>): Boolean {
            return true
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

    val SELECTED_WILL = object : Key<PlayerCache, Will?> {
        override val default: Will?
            get() = null

        override fun satisfyWith(value: Will?): Boolean {
            return true
        }
    }

    val MENU_RELIC_CATEGORY = object : Key<PlayerCache, RelicCategory> {
        override val default: RelicCategory
            get() = RelicCategory.ALL

        override fun satisfyWith(value: RelicCategory): Boolean {
            return true
        }
    }

    val GENERATED_RELIC = object : Key<PlayerCache, Relic?> {
        override val default: Relic?
            get() = null

        override fun satisfyWith(value: Relic?): Boolean {
            return true
        }
    }

    val BREAK_COUNT = object : Key<PlayerCache, Int> {
        override val default: Int
            get() = 0

        override fun satisfyWith(value: Int): Boolean {
            return true
        }
    }

    val RELIC_BONUS = object : Key<PlayerCache, Double> {
        override val default: Double
            get() = 0.0

        override fun satisfyWith(value: Double): Boolean {
            return true
        }
    }

    val WILL_RELATIONSHIP_MAP: Map<Will, Key<PlayerCache, WillRelationship>> = Will.values().map { will ->
        will to
                object : Key<PlayerCache, WillRelationship> {
                    override val default: WillRelationship
                        get() = WillRelationship.FRESH

                    override fun satisfyWith(value: WillRelationship): Boolean {
                        return true
                    }
                }
    }.toMap()

    val SERVER_NAME = object : Key<PlayerCache, String?> {
        override val default: String?
            get() = null

        override fun satisfyWith(value: String?): Boolean {
            // 強制的に書き換えを拒否
            Gigantic.PLUGIN.logger.warning("サーバーネームの書き換えは禁止されています")
            return false
        }
    }

    val HOME_MAP = object : DatabaseKey<PlayerCache, Map<Int, Home>> {
        override val default: Map<Int, Home>
            get() = mapOf()

        override fun read(entity: UserEntity): Map<Int, Home> {
            val userHomeList = entity.userHomeList
            return userHomeList.map {
                it.homeId to Home(
                        it.homeId,
                        Location(Bukkit.getWorld(it.worldId), it.x, it.y, it.z),
                        it.name
                )
            }.toMap()
        }

        override fun store(entity: UserEntity, value: Map<Int, Home>) {
            UserHomeTable.deleteWhere { (UserHomeTable.userId eq entity.user.id.value) }
            value.forEach { homeId, home ->
                UserHome.new {
                    this.user = entity.user
                    this.homeId = homeId
                    this.worldId = home.location.world.uid
                    this.x = home.location.x
                    this.y = home.location.y
                    this.z = home.location.z
                    this.name = home.name
                }
            }
        }

        override fun satisfyWith(value: Map<Int, Home>): Boolean {
            return true
        }
    }

    val SPELL_SKY_WALK_PLACE_BLOCKS = object : Key<PlayerCache, Set<Block>> {
        override val default: Set<Block>
            get() = setOf()

        override fun satisfyWith(value: Set<Block>): Boolean {
            return true
        }
    }

    val SPELL_SKY_WALK_TOGGLE = object : DatabaseKey<PlayerCache, Boolean> {
        override val default: Boolean
            get() = false

        override fun read(entity: UserEntity): Boolean {
            val user = entity.user
            return user.skyWalkToggle
        }

        override fun store(entity: UserEntity, value: Boolean) {
            val user = entity.user
            user.skyWalkToggle = value
        }

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }
    }

    val GIVEN_VOTE_BONUS = object : DatabaseKey<PlayerCache, Int> {
        override val default: Int
            get() = 0

        override fun read(entity: UserEntity): Int {
            val user = entity.user
            return user.givenVoteBonus
        }

        override fun store(entity: UserEntity, value: Int) {
            val user = entity.user
            user.givenVoteBonus = value
        }

        override fun satisfyWith(value: Int): Boolean {
            return value >= 0
        }
    }

    val GIVEN_WILL_SET = object : Key<PlayerCache, Set<Will>?> {
        override val default: Set<Will>?
            get() = null

        override fun satisfyWith(value: Set<Will>?): Boolean {
            return true
        }
    }

    val WALK_SPEED = object : DatabaseKey<PlayerCache, BigDecimal> {
        override val default: BigDecimal
            get() = Defaults.WALK_SPEED

        override fun read(entity: UserEntity): BigDecimal {
            val user = entity.user
            return user.walkSpeed
        }

        override fun store(entity: UserEntity, value: BigDecimal) {
            val user = entity.user
            user.walkSpeed = value
        }

        override fun satisfyWith(value: BigDecimal): Boolean {
            return value in 0.2.toBigDecimal()..1.0.toBigDecimal()
        }
    }

    val PREVIOUS_LOCATION = object : Key<PlayerCache, Location?> {
        override val default: Location?
            get() = null

        override fun satisfyWith(value: Location?): Boolean {
            return true
        }
    }

    val TOGGLE_SETTING_MAP: Map<ToggleSetting, DatabaseKey<PlayerCache, Boolean>> = ToggleSetting.values().map { display ->
        display to object : DatabaseKey<PlayerCache, Boolean> {
            override val default: Boolean
                get() = true

            override fun read(entity: UserEntity): Boolean {
                val userToggle = entity.userToggleMap.getValue(display)
                return userToggle.toggle
            }

            override fun store(entity: UserEntity, value: Boolean) {
                val userToggle = entity.userToggleMap.getValue(display)
                userToggle.toggle = value
            }

            override fun satisfyWith(value: Boolean): Boolean {
                return true
            }
        }
    }.toMap()

    val TITLE = object : Key<PlayerCache, String?> {
        override val default: String?
            get() = null

        override fun satisfyWith(value: String?): Boolean {
            return true
        }
    }

    val SUBTITLE = object : Key<PlayerCache, String?> {
        override val default: String?
            get() = null

        override fun satisfyWith(value: String?): Boolean {
            return true
        }
    }

    val MENU_EFFECT_LIST = object : Key<PlayerCache, List<GiganticEffect>> {
        override val default: List<GiganticEffect>
            get() = listOf()

        override fun satisfyWith(value: List<GiganticEffect>): Boolean {
            return true
        }
    }

    val MENU_RELIC_LIST = object : Key<PlayerCache, List<Relic>> {
        override val default: List<Relic>
            get() = listOf()

        override fun satisfyWith(value: List<Relic>): Boolean {
            return true
        }
    }

    val TOTEM = object : DatabaseKey<PlayerCache, Int> {
        override val default: Int
            get() = 0

        override fun read(entity: UserEntity): Int {
            val user = entity.user
            return user.totem
        }

        override fun store(entity: UserEntity, value: Int) {
            val user = entity.user
            user.totem = value
        }

        override fun satisfyWith(value: Int): Boolean {
            return value >= 0
        }
    }

    val TOTEM_PIECE = object : DatabaseKey<PlayerCache, Int> {
        override val default: Int
            get() = 0

        override fun read(entity: UserEntity): Int {
            val user = entity.user
            return user.totemPiece
        }

        override fun store(entity: UserEntity, value: Int) {
            val user = entity.user
            user.totemPiece = value
        }

        override fun satisfyWith(value: Int): Boolean {
            return value >= 0
        }
    }

    val EVENT_JMS_KING_GIVEN_AT = object : DatabaseKey<PlayerCache, DateTime> {
        override val default: DateTime
            get() = DateTime.now()

        override fun read(entity: UserEntity): DateTime {
            val user = entity.user
            return user.eventJmsKingGivenAt
        }

        override fun store(entity: UserEntity, value: DateTime) {
            val user = entity.user
            user.eventJmsKingGivenAt = value
        }

        override fun satisfyWith(value: DateTime): Boolean {
            return true
        }

    }

    val ACTIVE_RELICS = object : Key<PlayerCache, Set<Relic>> {
        override val default: Set<Relic>
            get() = setOf()

        override fun satisfyWith(value: Set<Relic>): Boolean {
            return true
        }
    }

    val MENU_WILL_LIST = object : Key<PlayerCache, List<Will>> {
        override val default: List<Will>
            get() = listOf()

        override fun satisfyWith(value: List<Will>): Boolean {
            return true
        }
    }

    val IS_NORMAL_TEXTURE = object : DatabaseKey<PlayerCache, Boolean> {
        override val default: Boolean
            get() = true

        override fun read(entity: UserEntity): Boolean {
            val user = entity.user
            return user.isNormalTexture
        }

        override fun store(entity: UserEntity, value: Boolean) {
            val user = entity.user
            user.isNormalTexture = value
        }

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }

    }

    val RANK_EXP = object : RankKey<RankCache, Long> {
        override val default: Long
            get() = 0L

        override fun read(entity: RankEntity): Long {
            val rankValue = entity.rankValue
            return rankValue.exp
        }

        override fun satisfyWith(value: Long): Boolean {
            return true
        }
    }

}