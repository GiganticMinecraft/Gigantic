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

    val MINEBLOCK_MAP: Map<MineBlockReason, PlayerCacheKey<Long>> = MineBlockReason.values()
            .map {
                it to object : PlayerCacheKey<Long> {
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
            }
            .toMap()

    val MEMORY_MAP: Map<Will, PlayerCacheKey<Long>> = Will.values()
            .map {
                it to object : PlayerCacheKey<Long> {
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
            }
            .toMap()

    val APTITUDE_MAP: Map<Will, PlayerCacheKey<Boolean>> = Will.values()
            .map {
                it to object : PlayerCacheKey<Boolean> {
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
            }
            .toMap()

    val BOSS_MAP: Map<Boss, PlayerCacheKey<Long>> = Boss.values()
            .map {
                it to object : PlayerCacheKey<Long> {
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
            }
            .toMap()

    val RELIC_MAP: Map<Relic, PlayerCacheKey<Long>> = Relic.values()
            .map {
                it to object : PlayerCacheKey<Long> {
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
            }
            .toMap()

    interface PlayerCacheKey<V> : CacheKey<PlayerCache, V>

}