package click.seichi.gigantic.battle

import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.SoulMonsterState
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class BattleMonster(
        private val monster: SoulMonster,
        spawner: Player,
        spawnLocation: Location
) {

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

    fun update() {
        updateLocation()
    }

    private fun updateLocation() {
        val nextLocation = entity.location.clone().apply {
            if (target.isValid) {
                val eyeLocation = target.eyeLocation.clone()
                val entityLocation = entity.location.clone()
                this.direction = eyeLocation.subtract(entityLocation).toVector().normalize()
            }
        }
        entity.teleport(nextLocation)
    }

    fun wake() {
        state = SoulMonsterState.WAKE
    }

    fun reset() {
        state = SoulMonsterState.SEAL
    }

    fun awake() {
        state = SoulMonsterState.WAIT
    }

}