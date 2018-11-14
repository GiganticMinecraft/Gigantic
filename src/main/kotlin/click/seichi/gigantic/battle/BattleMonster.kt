package click.seichi.gigantic.battle

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.message.messages.MonsterSpiritMessages
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.ai.AttackBlock
import click.seichi.gigantic.monster.ai.SoulMonsterState
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SoulMonsterSounds
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.util.EulerAngle
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
    private val bossBar: BossBar = Gigantic.createInvisibleBossBar().apply {
        isVisible = true
        style = BarStyle.SOLID
        title = "${ChatColor.RED}" +
                MonsterSpiritMessages.SEAL(monster.getName(locale)).asSafety(locale)
        color = BarColor.RED
        progress = 0.0
    }

    // 攻撃対象プレイヤー
    var attackTarget: Player = spawner
        private set
    // 状態遷移用
    var state: SoulMonsterState = SoulMonsterState.SEAL
        private set
    // 目的地
    var destination: Location? = null
        private set

    var health = monster.parameter.health

    var location: Location = spawnLocation

    val eyeLocation: Location
        get() = location.clone().add(0.0, 0.9, 0.0)
    val color: Color
        get() = monster.color

    private var disappearCount = 0

    fun spawn() {
        SoulMonsterSounds.SPAWN.play(spawnLocation)
    }

    fun join(player: Player) {
        bossBar.addPlayer(player)
    }

    fun leave(player: Player) {
        bossBar.apply {
            removePlayer(player)
        }
    }

    fun remove() {
        bossBar.removeAll()
        entity.remove()
    }

    // 戦闘中のみ呼び出し。プレイヤーが戦闘中に抜けた際にターゲットを更新
    fun updateTargets(joinedPlayers: Set<Player>): Boolean {
        attackTarget = joinedPlayers.shuffled().firstOrNull() ?: return false
        return true
    }

    // 起こしているとき
    fun updateAwakeProgress(nextProgress: Double) {
        bossBar.apply {
            val prev = progress
            if (nextProgress > prev)
                progress = nextProgress
        }
        MonsterSpiritAnimations.AWAKE.start(eyeLocation)
    }

    fun resetAwakeProgress() {
        bossBar.apply {
            progress = 0.0
        }
    }

    fun awake() {
        bossBar.apply {
            color = BarColor.PURPLE
            style = BarStyle.SEGMENTED_20
            title = "${ChatColor.LIGHT_PURPLE}" +
                    MonsterSpiritMessages.START(monster.getName(locale)).asSafety(locale)
        }
        state = SoulMonsterState.MOVE
        destination = ai.searchDestination(chunk, attackTarget, location)
    }

    fun update(elapsedTick: Long): SoulMonsterState {
        when (state) {
            SoulMonsterState.MOVE -> move(elapsedTick)
            SoulMonsterState.ATTACK -> attack(elapsedTick)
            SoulMonsterState.DEATH -> TODO()
            else -> {
            }
        }
        updateLocation()
        return state
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
        (1..monster.parameter.attackTimes).forEachIndexed { index, _ ->
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                val attackBlock = ai.getAttackBlock(chunk, getAttackBlocks(), attackTarget, elapsedTick)
                if (attackBlock == null) {
                    disappearCount++
                    if (disappearCount > 10) {
                        state = SoulMonsterState.DISAPPEAR
                    }
                    return@runTaskLater
                }
                attack(attackBlock)
            }, index * 10L)
        }
        Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
            state = SoulMonsterState.MOVE
            destination = ai.searchDestination(chunk, attackTarget, location)
        }, monster.parameter.attackTimes * 10L)

        state = SoulMonsterState.WAIT
    }

    private fun attack(attackBlock: AttackBlock) {
        val player = attackBlock.target
        val block = attackBlock.block

        if (!entity.isValid || !player.isValid) return
        val attackBlockData = Bukkit.createBlockData(monster.parameter.attackMaterial)
        // send attack ready particle

        // effects
        SoulMonsterSounds.ATTACK_READY.play(entity.eyeLocation)
        MonsterSpiritAnimations.ATTACK_READY(monster.color).exhaust(entity, block.centralLocation, meanY = 0.9)

        attackBlocks.add(attackBlock)

        Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
            if (!entity.isValid || !player.isValid) return@runTaskLater

            if (block.isEmpty) return@runTaskLater

            // effects
            block.world.spawnParticle(Particle.BLOCK_CRACK, block.centralLocation.add(0.0, 0.5, 0.0), 20, attackBlockData)
            player.sendBlockChange(block.location, attackBlockData)

        }, 20L)

        // attack
        Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
            if (!entity.isValid || !player.isValid) return@runTaskLater

            if (!attackBlocks.remove(attackBlock)) return@runTaskLater
            if (block.isEmpty) return@runTaskLater

            // health
            player.manipulate(CatalogPlayerCache.HEALTH) { health ->
                health.decrease(monster.parameter.attackDamage)
                PlayerMessages.HEALTH_DISPLAY(health).sendTo(player)
            }
            // effects
            SoulMonsterSounds.ATTACK.play(block.centralLocation)
            PlayerSounds.INJURED.play(player.location)
            block.world.spawnParticle(Particle.BLOCK_CRACK, block.centralLocation, 20, attackBlockData)

            block.type = Material.AIR
        }, 20L + monster.parameter.tickToAttack)

    }

    fun defencedByPlayer(block: Block) {
        if (!attackBlocks.removeIf { block == it.block }) return
        SoulMonsterSounds.DEFENCE.play(block.centralLocation)
        MonsterSpiritAnimations.DEFENCE(monster.color).absorb(entity, block.centralLocation, meanY = 0.9)
    }


}