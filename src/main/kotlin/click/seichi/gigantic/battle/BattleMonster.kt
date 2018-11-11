package click.seichi.gigantic.battle

import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.SoulMonsterState
import click.seichi.gigantic.util.Random
import org.bukkit.Location
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

    val attackDamage = monster.parameter.attack

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
        val speed = ai.getMovementSpeed(location, targetLocation)
        val diff = targetLocation.clone().subtract(location).toVector().normalize().multiply(speed)
        updateLocation(location.add(diff))
        if (targetLocation.distance(location) >= 0.1) return
        state = SoulMonsterState.WAIT
    }

}