package click.seichi.gigantic.database.cache.keys

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.database.cache.CacheKey
import click.seichi.gigantic.database.cache.caches.PlayerCache
import click.seichi.gigantic.database.dao.*
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import org.jetbrains.exposed.dao.Entity
import java.util.*

/**
 * @author tar0ss
 */
object PlayerCacheKeys {

    val LOCALE = object : PlayerCacheKey<Locale> {
        override val default
            get() = Locale.JAPANESE

        override fun read(entity: Entity<*>): Locale? {
            val user = entity as User
            return Locale(user.localeString)
        }

        override fun store(entity: Entity<*>, value: Locale) {
            val user = entity as User
            user.localeString = value.language.substringBefore("_")
        }
    }

    val MANA = object : PlayerCacheKey<Long> {
        override val default: Long
            get() = 0L

        override fun read(entity: Entity<*>): Long? {
            val user = entity as User
            return user.mana
        }

        override fun store(entity: Entity<*>, value: Long) {
            val user = entity as User
            user.mana = value
        }
    }

    val MINEBLOCK_MAP = MineBlockReason.values()
            .map { it to MineBlockCacheKey(it) }
            .toMap()

    val MEMORY_MAP = Will.values()
            .map { it to MemoryCacheKey(it) }
            .toMap()

    val APTITUDE_MAP = Will.values()
            .map { it to AptitudeCacheKey(it) }
            .toMap()

    val BOSS_MAP = Boss.values()
            .map { it to BossCacheKey(it) }
            .toMap()

    val RELIC_MAP = Relic.values()
            .map { it to RelicCacheKey(it) }
            .toMap()

    class MineBlockCacheKey(mineBlockReason: MineBlockReason) : PlayerCacheKey<Long> {
        override val default: Long
            get() = 0L

        override fun read(entity: Entity<*>): Long? {
            val userMineBlock = entity as UserMineBlock
            return userMineBlock.mineBlock
        }

        override fun store(entity: Entity<*>, value: Long) {
            val userMineBlock = entity as UserMineBlock
            userMineBlock.mineBlock = value
        }
    }

    class MemoryCacheKey(will: Will) : PlayerCacheKey<Long> {
        override val default: Long
            get() = 0L

        override fun read(entity: Entity<*>): Long? {
            val userWill = entity as UserWill
            return userWill.memory
        }

        override fun store(entity: Entity<*>, value: Long) {
            val userWill = entity as UserWill
            userWill.memory = value
        }
    }

    class AptitudeCacheKey(will: Will) : PlayerCacheKey<Boolean> {
        override val default: Boolean
            get() = false

        override fun read(entity: Entity<*>): Boolean? {
            val userWill = entity as UserWill
            return userWill.hasAptitude
        }

        override fun store(entity: Entity<*>, value: Boolean) {
            val userWill = entity as UserWill
            userWill.hasAptitude = value
        }
    }

    class BossCacheKey(boss: Boss) : PlayerCacheKey<Long> {
        override val default: Long
            get() = 0L

        override fun read(entity: Entity<*>): Long? {
            val userBoss = entity as UserBoss
            return userBoss.defeat
        }

        override fun store(entity: Entity<*>, value: Long) {
            val userBoss = entity as UserBoss
            userBoss.defeat = value
        }
    }

    class RelicCacheKey(relic: Relic) : PlayerCacheKey<Long> {
        override val default: Long
            get() = 0L

        override fun read(entity: Entity<*>): Long? {
            val userRelic = entity as UserRelic
            return userRelic.amount
        }

        override fun store(entity: Entity<*>, value: Long) {
            val userRelic = entity as UserRelic
            userRelic.amount = value
        }
    }

    interface PlayerCacheKey<V> : CacheKey<PlayerCache, V>
}