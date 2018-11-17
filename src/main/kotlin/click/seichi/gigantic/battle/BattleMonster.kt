package click.seichi.gigantic.battle

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.message.messages.BattleMessages
import click.seichi.gigantic.message.messages.DeathMessages
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.ai.AttackBlock
import click.seichi.gigantic.monster.ai.SoulMonsterState
import click.seichi.gigantic.sound.sounds.SoulMonsterSounds
import click.seichi.gigantic.topbar.bars.BattleBars
import click.seichi.gigantic.util.Random
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.boss.BossBar
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.EulerAngle
import java.math.BigDecimal
import java.util.*

/**
 * @author tar0ss
 */
class BattleMonster(
        private val monster: SoulMonster,
        private val spawner: Player,
        private val chunk: Chunk,
        private val locale: Locale
) {
    // モンスターAI
    private val ai = monster.createAIInstance()
    // 攻撃ブロック
    private val attackBlocks: MutableSet<AttackBlock> = mutableSetOf()
    // モンスターのスポーン位置
    private val spawnLocation: Location = ai.searchSpawnLocation(spawner, chunk)
    // モンスターの実体
    private val entity = spawnLocation.world.spawn(spawnLocation, ArmorStand::class.java) {
        it.run {
            isVisible = false
            setBasePlate(false)
            setArms(true)
            isMarker = true
            isInvulnerable = true
            canPickupItems = false
            setGravity(false)
            isCustomNameVisible = false
            isSmall = true
            helmet = monster.getIcon()
        }
    }
    // モンスターのボスバー
    private val bossBar: BossBar = Gigantic.createInvisibleBossBar()

    // 攻撃対象プレイヤー
    var attackTarget: Player = spawner
        private set
    // 状態遷移用
    var state: SoulMonsterState = SoulMonsterState.SEAL
        private set(value) {
            field = when (field) {
                SoulMonsterState.DISAPPEAR,
                SoulMonsterState.DEATH -> field
                else -> value
            }
        }


    // 目的地
    var destination: Location? = null
        private set

    var health = monster.parameter.health.toBigDecimal()

    var location: Location = spawnLocation

    val eyeLocation: Location
        get() = location.clone().add(0.0, 0.9, 0.0)
    val color: Color
        get() = monster.color

    private var disappearCount = 0

    private val totalDamageMap = mutableMapOf<Player, BigDecimal>()

    private val attackBlockData = Bukkit.createBlockData(monster.parameter.attackMaterial)

    fun spawn() {
        SoulMonsterSounds.SPAWN.play(spawnLocation)
    }

    fun join(player: Player) {
        bossBar.addPlayer(player)
    }

    fun leave(player: Player) {
        bossBar.removePlayer(player)
    }

    fun remove() {
        bossBar.removeAll()
        entity.remove()
        attackBlocks.forEach {
            it.target.sendBlockChange(it.block.location, it.block.blockData)
        }
    }

    // 戦闘中のみ呼び出し。プレイヤーが戦闘中に抜けた際にターゲットを更新
    fun updateTargets(joinedPlayers: Set<Player>): Boolean {
        attackTarget = joinedPlayers.shuffled().firstOrNull() ?: return false
        return true
    }

    // 起こしているとき
    fun updateAwakeProgress(nextProgress: Double) {
        BattleBars.SEAL(nextProgress, monster, locale).show(bossBar)
        MonsterSpiritAnimations.AWAKE.start(eyeLocation)
    }

    fun resetAwakeProgress() {
        BattleBars.RESET(monster, locale).show(bossBar)
    }

    fun awake() {
        BattleBars.AWAKE(monster.parameter.health.toBigDecimal(), monster, locale).show(bossBar)
        state = SoulMonsterState.MOVE
        destination = ai.searchDestination(chunk, attackTarget, location)
    }

    fun update(elapsedTick: Long) {
        when (state) {
            SoulMonsterState.MOVE -> move(elapsedTick)
            SoulMonsterState.ATTACK -> attack(elapsedTick)
            else -> {
            }
        }
        updateLocation()
    }

    private fun updateLocation() {
        val fixedLocation = location.clone().apply {
            if (attackTarget.isValid) {
                this.direction = attackTarget.eyeLocation.clone()
                        .subtract(eyeLocation.clone())
                        .toVector().normalize()
                val diffY = attackTarget.eyeLocation.y - eyeLocation.y
                val distance = attackTarget.eyeLocation.distance(eyeLocation)
                val sin = diffY / distance
                val cos = 1.0 - Math.pow(sin, 2.0)
                val diffX = distance * cos
                val rad = Math.atan2(diffY, diffX)
                entity.headPose = EulerAngle(-rad, 0.0, 0.0)
            }
        }
        entity.teleport(fixedLocation)
    }

    private fun move(elapsedTick: Long) {
        val des = destination ?: error("destination must not be null")
        val speed = monster.parameter.speed
        val diff = des.clone().subtract(location).toVector().normalize().multiply(speed)
        location.add(diff)
        if (des.distance(location) >= 0.1) return
        state = SoulMonsterState.ATTACK
    }

    fun getAttackBlocks() = attackBlocks.toSet()

    private fun attack(elapsedTick: Long) {
        // set attack blocks
        (1..monster.parameter.attackTimes).forEach { index ->
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                val attackBlocks = ai.getAttackBlocks(chunk, getAttackBlocks(), attackTarget, elapsedTick)
                if (attackBlocks == null) {
                    disappearCount++
                    if (disappearCount > 5 * monster.parameter.attackTimes) {
                        state = SoulMonsterState.DISAPPEAR
                    }
                    return@runTaskLater
                }
                attackBlocks.forEach { attack(it) }
            }, index * 10L)
        }
        Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
            state = SoulMonsterState.MOVE
            destination = ai.searchDestination(chunk, attackTarget, location)
        }, monster.parameter.attackTimes * 10L + 60L)
        state = SoulMonsterState.WAIT
    }

    private fun attack(attackBlock: AttackBlock) {
        val player = attackBlock.target
        val block = attackBlock.block

        if (!entity.isValid || !player.isValid) return
        // send attack ready particle

        // effects
        SoulMonsterSounds.ATTACK_READY.play(entity.eyeLocation)
        MonsterSpiritAnimations.ATTACK_READY(monster.color).exhaust(entity, block.centralLocation, meanY = 0.9)

        attackBlocks.add(attackBlock)

        object : BukkitRunnable() {
            var ticks = 0L
            override fun run() {
                if (ticks++ > 100L) {
                    if (player.isOnline) {
                        player.sendBlockChange(block.location, block.blockData)
                    }
                    cancel()
                    return
                }

                if (!entity.isValid || !player.isValid) return

                if (block.isEmpty || !attackBlocks.contains(attackBlock)) {
                    player.sendBlockChange(block.location, block.blockData)
                    return
                }

                // effects
                MonsterSpiritAnimations.ATTACK_READY_BLOCK(attackBlockData)
                        .start(block.centralLocation)
                player.sendBlockChange(block.location, attackBlockData)
                if (ticks % 10 == 0L) {
                    SoulMonsterSounds.ATTACK_READY_SUB.play(block.centralLocation)
                }

            }

        }.runTaskTimer(Gigantic.PLUGIN, 20, 1L)

        // attack
        Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
            if (!entity.isValid || !player.isValid || player.isDead) return@runTaskLater

            if (!attackBlocks.remove(attackBlock)) return@runTaskLater
            if (block.isEmpty) return@runTaskLater

            // health
            player.manipulate(CatalogPlayerCache.HEALTH) { health ->
                health.decrease(monster.parameter.attackDamage)
                if (health.isZero) {
                    player.offer(Keys.DEATH_MESSAGE, DeathMessages.BY_MONSTER(player.name, monster))
                }
                PlayerMessages.HEALTH_DISPLAY(health).sendTo(player)
                BattleMessages.DAMEGE(monster, monster.parameter.attackDamage).sendTo(player)
            }
            // effects
            SoulMonsterSounds.ATTACK.play(block.centralLocation)

            block.type = Material.AIR
        }, 20L + 100L)

    }

    fun defencedByPlayer(block: Block) {
        if (!attackBlocks.removeIf { block == it.block }) return
        SoulMonsterSounds.DEFENCE.play(block.centralLocation)
        MonsterSpiritAnimations.DEFENCE(monster.color).absorb(entity, block.centralLocation, meanY = 0.9)
    }

    fun damageByPlayer(player: Player, damage: BigDecimal): BigDecimal {
        val trueDamage = damage.coerceIn(0.toBigDecimal(), health)
        totalDamageMap.compute(player) { _, amount ->
            amount?.plus(trueDamage) ?: trueDamage
        }
        health -= trueDamage
        if (health == 0.toBigDecimal()) {
            state = SoulMonsterState.DEATH
        }
        BattleBars.AWAKE(health, monster, locale).show(bossBar)
        return trueDamage
    }

    fun randomDrops(): SoulMonster.DropRelic? {
        return monster.dropRelicSet.firstOrNull { it.probability > Random.nextDouble() }
    }

    fun defeatedBy(player: Player) {
        monster.defeatedBy(player)
    }

}