package click.seichi.gigantic.cache.cache

import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.database.dao.*
import click.seichi.gigantic.database.table.*
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
class PlayerCache(private val uniqueId: UUID, private val playerName: String) : Cache<PlayerCache>() {

    init {
        registerKey(Keys.BAG)
        registerKey(Keys.MANA)
        registerKey(Keys.DEATH_MESSAGE)
        register(CatalogPlayerCache.LEVEL)
        register(CatalogPlayerCache.MANA)
        register(CatalogPlayerCache.MEMORY)
        register(CatalogPlayerCache.APTITUDE)
        register(CatalogPlayerCache.MINE_BURST)
        register(CatalogPlayerCache.MINE_BLOCK)
        register(CatalogPlayerCache.MINE_COMBO)
        register(CatalogPlayerCache.RAID_DATA)
        register(CatalogPlayerCache.AFK_LOCATION)
        register(CatalogPlayerCache.MENU_DATA)
        register(CatalogPlayerCache.FLASH)
        register(CatalogPlayerCache.HEALTH)
        register(CatalogPlayerCache.BELT_SWITCHER)
        register(CatalogPlayerCache.EXPLOSION)
    }

    override fun read() {
        transaction {
            UserEntityData(uniqueId, playerName).run {
                Keys.IS_FIRST_JOIN.let {
                    registerKey(it, it.read(user))
                }
                Keys.LOCALE.let {
                    registerKey(it, it.read(user))
                }
                Keys.MANA.let {
                    registerKey(it, it.read(user))
                }
                Keys.HEALTH.let {
                    registerKey(it, it.read(user))
                }
                Keys.BELT.let {
                    registerKey(it, it.read(user))
                }
                Keys.MINEBLOCK_MAP.forEach { reason, key ->
                    registerKey(key, key.read(userMineBlockMap[reason] ?: return@forEach))
                }
                Keys.MEMORY_MAP.forEach { will, key ->
                    registerKey(key, key.read(userWillMap[will] ?: return@forEach))
                }
                Keys.APTITUDE_MAP.forEach { will, key ->
                    registerKey(key, key.read(userWillMap[will] ?: return@forEach))
                }
                Keys.BOSS_MAP.forEach { boss, key ->
                    registerKey(key, key.read(userBossMap[boss] ?: return@forEach))
                }
                Keys.RELIC_MAP.forEach { relic, key ->
                    registerKey(key, key.read(userRelicMap[relic] ?: return@forEach))
                }
                Keys.LOCKED_FUNCTION_MAP.forEach { func, key ->
                    registerKey(key, key.read(userLockedMap[func] ?: return@forEach))
                }
                Keys.BELT_MAP.forEach { belt, key ->
                    registerKey(key, key.read(userBeltMap[belt] ?: return@forEach))
                }
            }
        }
    }

    override fun write() {
        transaction {
            UserEntityData(uniqueId, playerName).run {
                // 更新時間を記録
                user.updatedDate = DateTime.now()
                Keys.IS_FIRST_JOIN.let {
                    it.store(user, getOrDefault(it))
                }
                Keys.LOCALE.let {
                    it.store(user, getOrDefault(it))
                }
                Keys.MANA.let {
                    it.store(user, getOrDefault(it))
                }
                Keys.HEALTH.let {
                    it.store(user, getOrDefault(it))
                }
                Keys.BELT.let {
                    it.store(user, getOrDefault(it))
                }
                Keys.MINEBLOCK_MAP.forEach { reason, key ->
                    key.store(userMineBlockMap[reason] ?: return@forEach, getOrDefault(key))
                }
                Keys.MEMORY_MAP.forEach { will, key ->
                    key.store(userWillMap[will] ?: return@forEach, getOrDefault(key))
                }
                Keys.APTITUDE_MAP.forEach { will, key ->
                    key.store(userWillMap[will] ?: return@forEach, getOrDefault(key))
                }
                Keys.BOSS_MAP.forEach { boss, key ->
                    key.store(userBossMap[boss] ?: return@forEach, getOrDefault(key))
                }
                Keys.RELIC_MAP.forEach { relic, key ->
                    key.store(userRelicMap[relic] ?: return@forEach, getOrDefault(key))
                }
                Keys.LOCKED_FUNCTION_MAP.forEach { func, key ->
                    key.store(userLockedMap[func] ?: return@forEach, getOrDefault(key))
                }
                Keys.BELT_MAP.forEach { belt, key ->
                    key.store(userBeltMap[belt] ?: return@forEach, getOrDefault(key))
                }
            }
        }
    }

    /**
     * [UUID]に紐づけられたエンティティデータクラス
     *
     * @author tar0ss
     */
    class UserEntityData(uniqueId: UUID, playerName: String) {

        val user = User.findById(uniqueId)?.apply {
            name = playerName
        } ?: User.new(uniqueId) {
            name = playerName
        }

        val userWillMap = Will.values().map { will ->
            will to (UserWill
                    .find { (UserWillTable.userId eq uniqueId) and (UserWillTable.willId eq will.id) }
                    .firstOrNull() ?: UserWill.new {
                user = this@UserEntityData.user
                willId = will.id
            })
        }.toMap()

        val userMineBlockMap = MineBlockReason.values().map { reason ->
            reason to (UserMineBlock
                    .find { (UserMineBlockTable.userId eq uniqueId) and (UserMineBlockTable.reasonId eq reason.id) }
                    .firstOrNull() ?: UserMineBlock.new {
                user = this@UserEntityData.user
                reasonId = reason.id
            })
        }.toMap()

        val userBossMap = Boss.values().map { boss ->
            boss to (UserBoss
                    .find { (UserBossTable.userId eq uniqueId) and (UserBossTable.bossId eq boss.id) }
                    .firstOrNull() ?: UserBoss.new {
                user = this@UserEntityData.user
                bossId = boss.id
            })
        }.toMap()

        val userRelicMap = Relic.values().map { relic ->
            relic to (UserRelic
                    .find { (UserRelicTable.userId eq uniqueId) and (UserRelicTable.relicId eq relic.id) }
                    .firstOrNull() ?: UserRelic.new {
                user = this@UserEntityData.user
                relicId = relic.id
            })
        }.toMap()

        val userLockedMap = LockedFunction.values().map { func ->
            func to (UserLocked
                    .find { (UserLockedTable.userId eq uniqueId) and (UserLockedTable.lockedId eq func.id) }
                    .firstOrNull() ?: UserLocked.new {
                user = this@UserEntityData.user
                lockedId = func.id
            })
        }.toMap()

        val userBeltMap = Belt.values().map { belt ->
            belt to (UserBelt
                    .find { (UserBeltTable.userId eq uniqueId) and (UserBeltTable.beltId eq belt.id) }
                    .firstOrNull() ?: UserBelt.new {
                user = this@UserEntityData.user
                beltId = belt.id
            })
        }.toMap()
    }

}