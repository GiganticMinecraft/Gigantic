package click.seichi.gigantic.database.cache.caches

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.database.cache.keys.PlayerCacheKeys
import click.seichi.gigantic.database.dao.*
import click.seichi.gigantic.database.table.UserBossTable
import click.seichi.gigantic.database.table.UserMineBlockTable
import click.seichi.gigantic.database.table.UserRelicTable
import click.seichi.gigantic.database.table.UserWillTable
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import org.jetbrains.exposed.sql.and
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
class PlayerCache(private val uniqueId: UUID, private val playerName: String) : DatabaseCache<PlayerCache>(
        PlayerCacheKeys.LOCALE,
        PlayerCacheKeys.MANA,
        *PlayerCacheKeys.MINEBLOCK_MAP.values.toTypedArray(),
        *PlayerCacheKeys.MEMORY_MAP.values.toTypedArray(),
        *PlayerCacheKeys.APTITUDE_MAP.values.toTypedArray(),
        *PlayerCacheKeys.BOSS_MAP.values.toTypedArray(),
        *PlayerCacheKeys.RELIC_MAP.values.toTypedArray()
) {
    override fun read() {
        UserEntityData(uniqueId, playerName).run {
            PlayerCacheKeys.LOCALE.let {
                put(it, it.read(user))
            }
            PlayerCacheKeys.MANA.let {
                put(it, it.read(user))
            }
            PlayerCacheKeys.MINEBLOCK_MAP.forEach { reason, key ->
                put(key, key.read(userMineBlockMap[reason] ?: return@forEach))
            }
            PlayerCacheKeys.MEMORY_MAP.forEach { will, key ->
                put(key, key.read(userWillMap[will] ?: return@forEach))
            }
            PlayerCacheKeys.APTITUDE_MAP.forEach { will, key ->
                put(key, key.read(userWillMap[will] ?: return@forEach))
            }
            PlayerCacheKeys.BOSS_MAP.forEach { boss, key ->
                put(key, key.read(userBossMap[boss] ?: return@forEach))
            }
            PlayerCacheKeys.RELIC_MAP.forEach { relic, key ->
                put(key, key.read(userRelicMap[relic] ?: return@forEach))
            }
        }
    }

    override fun write() {
        UserEntityData(uniqueId, playerName).run {
            // 更新時間を記録
            user.updatedDate = DateTime.now()
            PlayerCacheKeys.LOCALE.let {
                it.store(user, get(it))
            }
            PlayerCacheKeys.MANA.let {
                it.store(user, get(it))
            }
            PlayerCacheKeys.MINEBLOCK_MAP.forEach { reason, key ->
                key.store(userMineBlockMap[reason] ?: return@forEach, get(key))
            }
            PlayerCacheKeys.MEMORY_MAP.forEach { will, key ->
                key.store(userWillMap[will] ?: return@forEach, get(key))
            }
            PlayerCacheKeys.APTITUDE_MAP.forEach { will, key ->
                key.store(userWillMap[will] ?: return@forEach, get(key))
            }
            PlayerCacheKeys.BOSS_MAP.forEach { boss, key ->
                key.store(userBossMap[boss] ?: return@forEach, get(key))
            }
            PlayerCacheKeys.RELIC_MAP.forEach { relic, key ->
                key.store(userRelicMap[relic] ?: return@forEach, get(key))
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

    }

}