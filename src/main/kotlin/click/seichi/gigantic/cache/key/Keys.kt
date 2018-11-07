package click.seichi.gigantic.cache.key

import click.seichi.gigantic.bag.Bag
import click.seichi.gigantic.bag.bags.MainBag
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.MineBlockReason
import click.seichi.gigantic.database.dao.*
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import org.bukkit.block.Block
import org.jetbrains.exposed.dao.Entity
import java.math.BigDecimal
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

    val LOCKED_FUNCTION_MAP: Map<LockedFunction, DatabaseKey<PlayerCache, Boolean>> = LockedFunction.values()
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

    val DEATH_MESSAGE = object : Key<PlayerCache, LocalizedText?> {
        override val default: LocalizedText?
            get() = null

        override fun satisfyWith(value: LocalizedText?): Boolean {
            return true
        }

    }

    val HEAL_SKILL_BLOCK = object : Key<PlayerCache, Block?> {
        override val default: Block?
            get() = null

        override fun satisfyWith(value: Block?): Boolean {
            return true
        }

    }

    val TERRA_DRAIN_SKILL_BLOCK = object : Key<PlayerCache, Block?> {
        override val default: Block?
            get() = null

        override fun satisfyWith(value: Block?): Boolean {
            return true
        }

    }

    val STELLA_CLAIR_SKILL_BLOCK = object : Key<PlayerCache, Block?> {
        override val default: Block?
            get() = null

        override fun satisfyWith(value: Block?): Boolean {
            return true
        }

    }

    val GRAND_NATURA_SKILL_BLOCK = object : Key<PlayerCache, Block?> {
        override val default: Block?
            get() = null

        override fun satisfyWith(value: Block?): Boolean {
            return true
        }

    }

    val AQUA_LINEA_SKILL_BLOCK = object : Key<PlayerCache, Block?> {
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
            get() = false

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

    val TERRA_DRAIN_TOGGLE = object : DatabaseKey<PlayerCache, Boolean> {
        override val default: Boolean
            get() = false

        override fun read(entity: Entity<*>): Boolean {
            val user = entity as User
            return user.terraDrainToggle
        }

        override fun store(entity: Entity<*>, value: Boolean) {
            val user = entity as User
            user.terraDrainToggle = value
        }

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }

    }

    val GRAND_NATURA_TOGGLE = object : DatabaseKey<PlayerCache, Boolean> {
        override val default: Boolean
            get() = false

        override fun read(entity: Entity<*>): Boolean {
            val user = entity as User
            return user.grandNaturaToggle
        }

        override fun store(entity: Entity<*>, value: Boolean) {
            val user = entity as User
            user.grandNaturaToggle = value
        }

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }

    }

    val AQUA_LINEA_TOGGLE = object : DatabaseKey<PlayerCache, Boolean> {
        override val default: Boolean
            get() = false

        override fun read(entity: Entity<*>): Boolean {
            val user = entity as User
            return user.aquaLineaToggle
        }

        override fun store(entity: Entity<*>, value: Boolean) {
            val user = entity as User
            user.aquaLineaToggle = value
        }

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }

    }


    val MANA_STONE_CAN_TOGGLE = object : Key<PlayerCache, Boolean> {

        override val default: Boolean
            get() = true

        override fun satisfyWith(value: Boolean): Boolean {
            return true
        }

    }

}