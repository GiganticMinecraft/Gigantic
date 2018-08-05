package click.seichi.gigantic.data.caches

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.data.keys.Keys
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
class PlayerCache(private val uniqueId: UUID, private val playerName: String) : Cache<PlayerCache>() {

    init {
        registerKey(Keys.LOCALE)
        registerKey(Keys.MANA)
        Keys.MINEBLOCK_MAP.values.forEach {
            registerKey(it)
        }
        Keys.MEMORY_MAP.values.forEach {
            registerKey(it)
        }
        Keys.APTITUDE_MAP.values.forEach {
            registerKey(it)
        }
        Keys.BOSS_MAP.values.forEach {
            registerKey(it)
        }
        Keys.RELIC_MAP.values.forEach {
            registerKey(it)
        }
        registerKey(Keys.BELT)
        registerKey(Keys.BAG)
        registerKey(Keys.LEVEL)
        registerKey(Keys.EXP)
    }

    override fun read() {
        UserEntityData(uniqueId, playerName).run {
            Keys.LOCALE.let {
                offer(it, it.read(user))
            }
            Keys.MANA.let {
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
            Keys.BOSS_MAP.forEach { boss, key ->
                offer(key, key.read(userBossMap[boss] ?: return@forEach))
            }
            Keys.RELIC_MAP.forEach { relic, key ->
                offer(key, key.read(userRelicMap[relic] ?: return@forEach))
            }
        }
    }

    override fun write() {
        UserEntityData(uniqueId, playerName).run {
            // 更新時間を記録
            user.updatedDate = DateTime.now()
            Keys.LOCALE.let {
                it.store(user, getOrDefault(it))
            }
            Keys.MANA.let {
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