package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.popup.pops.MonsterSpiritPops
import click.seichi.gigantic.sound.sounds.MonsterSpiritSounds
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class MonsterSpirit(
        spawnReason: SpawnReason,
        val location: Location,
        val monster: SoulMonster,
        val targetPlayer: Player? = null
) : Spirit(spawnReason, location.chunk) {

    private lateinit var monsterHeadEntity: ArmorStand


    /*private val sensor = Sensor(
            location,
            { player ->
                player ?: return@Sensor false
                when {
                    player.location.distance(location) > 2.5 -> false
                    targetPlayer == null -> true
                    player.uniqueId == targetPlayer.uniqueId -> true
                    else -> false
                }
            },
            { player, count ->
                player ?: return@Sensor
                WillSpiritAnimations.AMBIENT_EXHAUST(will.color).link(player, location, meanY = 0.9)
                if (count % 10 == 0) {
                    WillSpiritSounds.SENSE_SUB.playOnly(player)
                }
            },
            { player ->
                player ?: return@Sensor
                WillMessages.SENSED_WILL(this).sendTo(player)
                WillSpiritSounds.SENSED.playOnly(player)
                player.manipulate(CatalogPlayerCache.MEMORY) {
                    it.add(will, willSize.memory.toLong())
                }
                PlayerMessages.MEMORY_SIDEBAR(
                        player.find(CatalogPlayerCache.MEMORY) ?: return@Sensor,
                        player.find(CatalogPlayerCache.APTITUDE) ?: return@Sensor,
                        false
                ).sendTo(player)
                remove()
            }
    )*/

    // 60秒
    override val lifespan = 20 * 60
    override val spiritType = SpiritType.MONSTER

    override fun onSpawn() {
        targetPlayer ?: return
        MonsterSpiritSounds.SPAWN.play(location)
        monsterHeadEntity = MonsterSpiritPops.SPAWN(
                monster.getIcon(targetPlayer)
        ).pop(oppositeLocation ?: return)
    }

    private val oppositeLocation: Location?
        get() =
            if (targetPlayer == null) null
            else {
                val eyeLocation = targetPlayer.eyeLocation.clone()
                val entityLocation = location.clone()
                location.clone().apply {
                    this.direction = eyeLocation.subtract(entityLocation).toVector().normalize()
                }.subtract(0.0, 0.5, 0.0)
            }

    var ticks: Long = 0L

    override fun onRender() {
        monsterHeadEntity.teleport(oppositeLocation)
        if (ticks % 8L == 0L)
            MonsterSpiritAnimations.AMBIENT_EXHAUST(monster.color).exhaust(
                    targetPlayer ?: return,
                    oppositeLocation?.clone()?.add(0.0, 0.9, 0.0) ?: return,
                    meanY = 0.9
            )
        MonsterSpiritAnimations.AMBIENT(monster.color).start(oppositeLocation?.clone()?.add(0.0, 0.7, 0.0) ?: return)
        ticks++
    }

}