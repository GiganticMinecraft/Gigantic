package click.seichi.gigantic.battle

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.BattleMonsterAnimations
import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.BattleMessages
import click.seichi.gigantic.message.messages.DeathMessages
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.ai.AttackBlock
import click.seichi.gigantic.monster.ai.SoulMonsterState
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SoulMonsterSounds
import click.seichi.gigantic.topbar.bars.BattleBars
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.boss.BossBar
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.EulerAngle

/**
 * @author tar0ss
 */
class BattleMonster(
        private val monster: SoulMonster,
        spawner: BattlePlayer,
        private val chunk: Chunk
) {
    private val locale = spawner.player.wrappedLocale
    // モンスターAI
    private val ai = monster.createAIInstance()
    // 攻撃ブロック
    private val attackBlocks: MutableSet<AttackBlock> = mutableSetOf()
    // モンスターのボスバー
    private val bossBar: BossBar = Gigantic.createInvisibleBossBar()
    // モンスターの実体
    private lateinit var entity: ArmorStand
    private lateinit var location: Location
    private val eyeLocation: Location
        get() = entity.eyeLocation
    // 攻撃対象プレイヤー
    var attackTarget: BattlePlayer = spawner
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

    var health = monster.parameter.health

    var lastAttackTicks: Long = 0L

    private var disappearCount = 0

    private val totalDamageMap = mutableMapOf<Player, Long>()

    private val attackBlockData = Bukkit.createBlockData(monster.parameter.attackMaterial)

    fun leave(battlePlayer: BattlePlayer) {
        bossBar.removePlayer(battlePlayer.player)
    }

    fun remove() {
        bossBar.removeAll()
        entity.remove()
        attackBlocks.forEach {
            it.target.player.sendBlockChange(it.block.location, it.block.blockData)
        }
    }

    // 戦闘中のみ呼び出し。プレイヤーが戦闘中に抜けた際にターゲットを更新
    fun updateTargets(joinedPlayers: MutableSet<BattlePlayer>): Boolean {
        attackTarget = joinedPlayers.shuffled().firstOrNull() ?: return false
        return true
    }

    fun awake(spawnLocation: Location, players: Set<BattlePlayer>) {
        entity = spawnLocation.world.spawn(spawnLocation, ArmorStand::class.java) {
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
        players.forEach { bossBar.addPlayer(it.player) }
        BattleBars.AWAKE(monster.parameter.health, monster, locale).show(bossBar)

        players.map { it.player }.forEach { player ->
            if (!SoulMonster.VILLAGER.isDefeatedBy(player)) {
                BattleMessages.FIRST_AWAKE.sendTo(player)
            }
        }

        state = SoulMonsterState.MOVE
        location = entity.location
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
        MonsterSpiritAnimations.AMBIENT(monster.color).start(entity.eyeLocation)
    }

    private fun updateLocation() {
        val fixedLocation = location.clone().apply {
            if (attackTarget.player.isValid) {
                this.direction = attackTarget.player.eyeLocation.clone()
                        .subtract(eyeLocation.clone())
                        .toVector().normalize()
                val diffY = attackTarget.player.eyeLocation.y - eyeLocation.y
                val distance = attackTarget.player.eyeLocation.distance(eyeLocation)
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
        val diff = des.clone().subtract(location).toVector().normalize().multiply(0.1)
        location.add(diff)


        if (elapsedTick.minus(lastAttackTicks) >= monster.parameter.attackInterval) {
            state = SoulMonsterState.ATTACK
        } else if (des.distance(location) <= 0.1) {
            state = SoulMonsterState.MOVE
            destination = ai.searchDestination(chunk, attackTarget, location)
        }
    }

    private fun copyAttackBlocks() = attackBlocks.toSet()

    private fun attack(elapsedTick: Long) {
        // set attack blocks
        if (monster.parameter.attackTimes > 0) {
            (1..monster.parameter.attackTimes).forEach { index ->
                val shotDelay = index * monster.parameter.shotInterval
                Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                    val attackBlocks = ai.getAttackBlocks(chunk, copyAttackBlocks(), attackTarget, elapsedTick + shotDelay)
                    if (attackBlocks == null) {
                        disappearCount++
                        if (disappearCount > 5 * monster.parameter.attackTimes) {
                            state = SoulMonsterState.DISAPPEAR
                        }
                        return@runTaskLater
                    }
                    attackBlocks.forEach { attack(it) }
                }, shotDelay)
            }
        }
        val moveDelay = monster.parameter.attackTimes * 10L + 20L
        Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
            state = SoulMonsterState.MOVE
            destination = ai.searchDestination(chunk, attackTarget, location)
            lastAttackTicks = elapsedTick + moveDelay
        }, moveDelay)

        state = SoulMonsterState.WAIT
    }

    private fun attack(attackBlock: AttackBlock) {
        val player = attackBlock.target.player
        val block = attackBlock.block

        if (!entity.isValid || !player.isValid) return
        // send attack ready particle

        // effects
        SoulMonsterSounds.ATTACK_READY.play(entity.eyeLocation)
        BattleMonsterAnimations.ATTACK_READY(monster.color).exhaust(entity, block.centralLocation, meanY = 0.9)

        attackBlocks.add(attackBlock)

        object : BukkitRunnable() {
            var ticks = 0L
            override fun run() {
                if (ticks++ > 60L) {
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
                BattleMonsterAnimations.ATTACK_READY_BLOCK(attackBlockData)
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
                health.decrease(monster.parameter.power)
                if (health.isZero) {
                    player.offer(Keys.DEATH_MESSAGE, DeathMessages.BY_MONSTER(player.name, monster))
                }
                PlayerMessages.HEALTH_DISPLAY(health).sendTo(player)
                PlayerSounds.INJURED.play(player.location)
                BattleMessages.DAMAGE(monster, monster.parameter.power).sendTo(player)
            }

            if (!SoulMonster.ZOMBIE_VILLAGER.isDefeatedBy(player)) {
                BattleMessages.FIRST_DAMAGE.sendTo(player)
            }

            // effects
            SoulMonsterSounds.ATTACK.play(block.centralLocation)

            block.type = Material.AIR
        }, 20L + 60L)

    }

    fun defencedByPlayer(block: Block) {
        if (!attackBlocks.removeIf { block == it.block }) return
        SoulMonsterSounds.DEFENCE.play(block.centralLocation)
        BattleMonsterAnimations.DEFENCE(monster.color).absorb(entity, block.centralLocation, meanY = 0.9)
    }

    fun damageByPlayer(player: Player, damage: Long): Long {
        val trueDamage = damage.coerceIn(0L, health)
        totalDamageMap.compute(player) { _, amount ->
            amount?.plus(trueDamage) ?: trueDamage
        }
        health -= trueDamage
        if (health == 0L) {
            state = SoulMonsterState.DEATH
        }
        if (trueDamage > 0L) {
            BattleBars.AWAKE(health, monster, locale).show(bossBar)
            BattleMonsterAnimations.DAMAGE_FROM_PLAYER.start(eyeLocation)
        }
        return trueDamage
    }

}