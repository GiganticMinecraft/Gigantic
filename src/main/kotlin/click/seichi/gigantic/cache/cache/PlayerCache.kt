package click.seichi.gigantic.cache.cache

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.MineBlockReason
import click.seichi.gigantic.database.dao.*
import click.seichi.gigantic.database.table.*
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.soul.SoulMonster
import click.seichi.gigantic.will.Will
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
class PlayerCache(private val uniqueId: UUID, private val playerName: String) : Cache<PlayerCache>() {

    override fun read() {
        transaction {
            UserEntityData(uniqueId, playerName).run {
                Keys.IS_FIRST_JOIN.let {
                    offer(it, it.read(user))
                }
                Keys.MAX_COMBO.let {
                    offer(it, it.read(user))
                }
                Keys.LOCALE.let {
                    offer(it, it.read(user))
                }
                Keys.MANA.let {
                    offer(it, it.read(user))
                }
                Keys.HEALTH.let {
                    offer(it, it.read(user))
                }
                Keys.BELT.let {
                    offer(it, it.read(user))
                }
                Keys.SPELL_TOGGLE.let {
                    offer(it, it.read(user))
                }
                Keys.TELEPORT_TOGGLE.let {
                    offer(it, it.read(user))
                }
                Keys.TERRA_DRAIN_TOGGLE.let {
                    offer(it, it.read(user))
                }
                Keys.GRAND_NATURA_TOGGLE.let {
                    offer(it, it.read(user))
                }
                Keys.AQUA_LINEA_TOGGLE.let {
                    offer(it, it.read(user))
                }
                Keys.MINEBLOCK_MAP.forEach { reason, key ->
                    offer(key, key.read(userMineBlockMap[reason] ?: return@forEach))
                }
                Keys.MEMORY_MAP.forEach { will, key ->
                    offer(key, key.read(userWillMap[will] ?: return@forEach))
                }
                Keys.APTITUDE_MAP.forEach { will, key ->
                    offer(key, key.read(userWillMap[will] ?: return@forEach))
                }
                Keys.SOUL_MONSTER.forEach { boss, key ->
                    offer(key, key.read(userMonsterMap[boss] ?: return@forEach))
                }
                Keys.RELIC_MAP.forEach { relic, key ->
                    offer(key, key.read(userRelicMap[relic] ?: return@forEach))
                }
                Keys.ACHIEVEMENT_MAP.forEach { func, key ->
                    offer(key, key.read(userAchievementMap[func] ?: return@forEach))
                }
                Keys.BELT_TOGGLE_MAP.forEach { belt, key ->
                    offer(key, key.read(userBeltMap[belt] ?: return@forEach))
                }
                Keys.BELT_UNLOCK_MAP.forEach { belt, key ->
                    offer(key, key.read(userBeltMap[belt] ?: return@forEach))
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
                Keys.MAX_COMBO.let {
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
                Keys.SPELL_TOGGLE.let {
                    it.store(user, getOrDefault(it))
                }
                Keys.TELEPORT_TOGGLE.let {
                    it.store(user, getOrDefault(it))
                }
                Keys.TERRA_DRAIN_TOGGLE.let {
                    it.store(user, getOrDefault(it))
                }
                Keys.GRAND_NATURA_TOGGLE.let {
                    it.store(user, getOrDefault(it))
                }
                Keys.AQUA_LINEA_TOGGLE.let {
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
                Keys.SOUL_MONSTER.forEach { boss, key ->
                    key.store(userMonsterMap[boss] ?: return@forEach, getOrDefault(key))
                }
                Keys.RELIC_MAP.forEach { relic, key ->
                    key.store(userRelicMap[relic] ?: return@forEach, getOrDefault(key))
                }
                Keys.ACHIEVEMENT_MAP.forEach { func, key ->
                    key.store(userAchievementMap[func] ?: return@forEach, getOrDefault(key))
                }
                Keys.BELT_TOGGLE_MAP.forEach { belt, key ->
                    key.store(userBeltMap[belt] ?: return@forEach, getOrDefault(key))
                }
                Keys.BELT_UNLOCK_MAP.forEach { belt, key ->
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

        val userMonsterMap = SoulMonster.values().map { monster ->
            monster to (UserMonster
                    .find { (UserMonsterTable.userId eq uniqueId) and (UserMonsterTable.monsterId eq monster.id) }
                    .firstOrNull() ?: UserMonster.new {
                user = this@UserEntityData.user
                monsterId = monster.id
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

        val userAchievementMap = Achievement.values().map { func ->
            func to (UserAchievement
                    .find { (UserAchievementTable.userId eq uniqueId) and (UserAchievementTable.achievementId eq func.id) }
                    .firstOrNull() ?: UserAchievement.new {
                user = this@UserEntityData.user
                achievementId = func.id
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