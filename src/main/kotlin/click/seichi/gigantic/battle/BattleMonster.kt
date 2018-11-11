package click.seichi.gigantic.battle

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.SoulMonsterState
import click.seichi.gigantic.sound.sounds.MonsterSpiritSounds
import click.seichi.gigantic.util.Random
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class BattleMonster(
        private val monster: SoulMonster,
        spawner: Player
) {

    private var spawnLocation: Location

    val chunk = spawner.location.chunk

    init {
        do {
            spawnLocation = chunk.getBlock(Random.nextInt(15), 0, Random.nextInt(15)).let { block ->
                chunk.world.getHighestBlockAt(block.location).centralLocation.add(0.0, -0.5, 0.0)
            }
        } while (spawner.location.distance(spawnLocation) <= 3.0)
    }

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
        }
    }

    private val ai = monster.ai

    val location: Location
        get() = entity.location

    val eyeLocation: Location
        get() = entity.eyeLocation


    var health = monster.parameter.health

    val attackDamage = monster.parameter.attackDamage

    val speed = monster.parameter.speed

    var state = SoulMonsterState.SEAL
        private set

    var target: Player = spawner
        private set

    var targetLocation = spawnLocation

    fun spawn() {
        entity.apply {
            helmet = monster.getIcon()
        }
        updateLocation()
    }

    fun remove() {
        state = SoulMonsterState.DISAPPEAR
        entity.remove()
    }

    fun updateLocation(loc: Location? = null) {
        val nextLocation = loc ?: entity.location
        val fixedLocation = nextLocation.clone().apply {
            if (target.isValid) {
                val eyeLocation = target.eyeLocation.clone()
                val entityLocation = nextLocation.clone()
                this.direction = eyeLocation.subtract(entityLocation).toVector().normalize()
            }
        }
        entity.teleport(fixedLocation)
    }

    fun wake() {
        state = SoulMonsterState.WAKE
    }

    fun reset() {
        state = SoulMonsterState.SEAL
    }

    fun setTargetPlayer(player: Player) {
        target = player
        updateLocation()
    }

    fun setNextLocation() {
        state = SoulMonsterState.MOVE
        targetLocation = ai.searchNextTargetLocation(chunk, target)
    }

    fun move() {
        val speed = monster.parameter.speed
        val diff = targetLocation.clone().subtract(location).toVector().normalize().multiply(speed)
        updateLocation(location.add(diff))
        if (targetLocation.distance(location) >= 0.1) return
        state = SoulMonsterState.ATTACK
    }

    fun attack() {
        val attackPlayer = target
        monster.ai.getAttackBlockSet(chunk, target, monster.parameter.attackTimes)
                .forEachIndexed { index, block ->
                    Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                        if (!entity.isValid || !attackPlayer.isValid) return@runTaskLater
                        val attackBlockData = Bukkit.createBlockData(monster.parameter.attackMaterial)
                        // send attack ready particle
                        MonsterSpiritSounds.ATTACK_READY.play(entity.eyeLocation)
                        MonsterSpiritAnimations.ATTACK_READY(monster.color).exhaust(entity, block.centralLocation, meanY = 1.5)
                        Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                            if (!entity.isValid || !attackPlayer.isValid) return@runTaskLater
                            if (block.isEmpty) return@runTaskLater
                            attackPlayer.sendBlockChange(block.location, attackBlockData)
                            attackPlayer.getOrPut(Keys.ATTACK_BLOCK_SET).add(block)
                        }, 20L)

                        // attack
                        Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                            if (!entity.isValid || !attackPlayer.isValid) return@runTaskLater
                            attackPlayer.getOrPut(Keys.ATTACK_BLOCK_SET).remove(block)
                            if (block.isEmpty) return@runTaskLater
                            attackPlayer.manipulate(CatalogPlayerCache.HEALTH) { health ->
                                health.decrease(monster.parameter.attackDamage)
                                PlayerMessages.HEALTH_DISPLAY(health).sendTo(attackPlayer)
                            }
                            MonsterSpiritSounds.ATTACK.play(block.centralLocation)
                            block.world.spawnParticle(Particle.BLOCK_CRACK, block.centralLocation, 1, attackBlockData)
                            block.type = Material.AIR
                        }, 20L + monster.parameter.tickToAttack)

                    }, index * 10L)
                }
        state = SoulMonsterState.WAIT
    }

}