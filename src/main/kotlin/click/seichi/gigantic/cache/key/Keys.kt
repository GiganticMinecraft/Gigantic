package click.seichi.gigantic.cache.key

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.bag.Bag
import click.seichi.gigantic.bag.bags.MainBag
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.belt.belts.MineBelt
import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.database.dao.*
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.player.MineBlockReason
import click.seichi.gigantic.player.components.Mana
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.skill.SkillParameters
import click.seichi.gigantic.skill.SkillTimer
import click.seichi.gigantic.will.Will
import org.bukkit.boss.BossBar
import org.jetbrains.exposed.dao.Entity
import java.util.*

/**
 * @author tar0ss
 */
object Keys {

    val IS_FIRST_JOIN = object : DatabaseKey<PlayerCache, Boolean> {
        override val default: Boolean
            get() = false

        override fun read(entity: Entity<*>): Boolean {
            val user = entity as User
            return user.isFirstJoin
        }

        override fun store(entity: Entity<*>, value: Boolean) {
            val user = entity as User
            user.isFirstJoin = value
        }

        override fun satisfyWith(value: Boolean): Boolean {
            return true
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

    val MANA = object : DatabaseKey<PlayerCache, Mana> {
        override val default: Mana
            get() = Mana()

        override fun read(entity: Entity<*>): Mana {
            val user = entity as User
            return Mana(user.mana)
        }

        override fun store(entity: Entity<*>, value: Mana) {
            val user = entity as User
            user.mana = value.current
        }

        override fun satisfyWith(value: Mana): Boolean {
            return false
        }

    }

    val MANA_BAR = object : Key<PlayerCache, BossBar> {
        override val default: BossBar
            get() = Gigantic.createInvisibleBossBar()

        override fun satisfyWith(value: BossBar): Boolean {
            return false
        }

    }

    val MINEBLOCK_MAP: Map<MineBlockReason, DatabaseKey<PlayerCache, Long>> = MineBlockReason.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Long> {
                    override val default: Long
                        get() = 0L

                    override fun read(entity: Entity<*>): Long {
                        val userMineBlock = entity as UserMineBlock
                        return userMineBlock.mineBlock
                    }

                    override fun store(entity: Entity<*>, value: Long) {
                        val userMineBlock = entity as UserMineBlock
                        userMineBlock.mineBlock = value
                    }

                    override fun satisfyWith(value: Long): Boolean {
                        return value >= 0L
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

    val BOSS_MAP: Map<Boss, DatabaseKey<PlayerCache, Long>> = Boss.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Long> {
                    override val default: Long
                        get() = 0L

                    override fun read(entity: Entity<*>): Long {
                        val userBoss = entity as UserBoss
                        return userBoss.defeat
                    }

                    override fun store(entity: Entity<*>, value: Long) {
                        val userBoss = entity as UserBoss
                        userBoss.defeat = value
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

    val HAS_UNLOCKED_MAP: Map<LockedFunction, DatabaseKey<PlayerCache, Boolean>> = LockedFunction.values()
            .map {
                it to object : DatabaseKey<PlayerCache, Boolean> {
                    override val default: Boolean
                        get() = false

                    override fun read(entity: Entity<*>): Boolean {
                        val userLocked = entity as UserLocked
                        return userLocked.hasUnlocked
                    }

                    override fun store(entity: Entity<*>, value: Boolean) {
                        val userLocked = entity as UserLocked
                        userLocked.hasUnlocked = value
                    }

                    override fun satisfyWith(value: Boolean): Boolean {
                        return true
                    }

                }
            }
            .toMap()

    val LEVEL = object : Key<PlayerCache, Int> {
        override val default: Int
            get() = 0

        override fun satisfyWith(value: Int): Boolean {
            return value >= 0
        }

    }

    val EXP = object : Key<PlayerCache, Long> {
        override val default: Long
            get() = 0L

        override fun satisfyWith(value: Long): Boolean {
            return value >= 0L
        }

    }

    val MINE_COMBO = object : Key<PlayerCache, Long> {
        override val default: Long
            get() = 0L

        override fun satisfyWith(value: Long): Boolean {
            return value >= 0L
        }

    }

    val BELT = object : Key<PlayerCache, Belt> {
        override val default: Belt
            get() = MineBelt

        override fun satisfyWith(value: Belt): Boolean {
            return true
        }

    }

    val BAG = object : Key<PlayerCache, Bag> {
        override val default: Bag
            get() = MainBag

        override fun satisfyWith(value: Bag): Boolean {
            return true
        }

    }

    val MINE_BURST_TIMER = object : Key<PlayerCache, SkillTimer> {
        override val default: SkillTimer
            get() = SkillTimer(SkillParameters.MINE_BURST_DURATION, SkillParameters.MINE_BURST_COOLTIME)

        override fun satisfyWith(value: SkillTimer): Boolean {
            return false
        }

    }

}