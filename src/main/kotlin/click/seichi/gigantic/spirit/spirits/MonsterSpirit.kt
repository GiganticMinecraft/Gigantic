package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.battle.Battle
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.SoulMonsterState
import click.seichi.gigantic.sound.sounds.MonsterSpiritSounds
import click.seichi.gigantic.spirit.Sensor
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class MonsterSpirit(
        spawnReason: SpawnReason,
        location: Location,
        val monster: SoulMonster,
        val targetPlayer: Player
) : Spirit(spawnReason, location.chunk) {

    private val battle = Battle(monster, targetPlayer, location)

    private val senseDuration = 60

    private val sensor = Sensor(
            location,
            { player ->
                player ?: return@Sensor false
                when {
                    player.location.distance(location) > 2.5 -> false
                    player.uniqueId == targetPlayer.uniqueId -> true
                    else -> false
                }
            },
            { player, count ->
                player ?: return@Sensor
                if (!battle.isJoin(player))
                    battle.join(player)
                battle.wake(count.div(senseDuration.toDouble()))
                if (count % 10 == 0)
                    MonsterSpiritSounds.SENSE_SUB.playOnly(player)
            },
            { player ->
                player ?: return@Sensor
                battle.start()
            },
            { player ->
                player ?: return@Sensor
                battle.leave(player)
            },
            senseDuration
    )

    override val lifespan = -1
    override val spiritType = SpiritType.MONSTER

    override fun onSpawn() {
        battle.spawn()
    }

    private fun disappearCondition(): Boolean {
        targetPlayer
        return targetPlayer.location.distance(battle.enemy.location) > 30.0 &&
                battle.enemy.state == SoulMonsterState.SEAL
    }

    override fun onRender() {
        if (disappearCondition()) {
            battle.end()
            remove()
        }

        sensor.update()
        battle.update()
    }

    override fun onRemove() {
        when (battle.enemy.state) {
            SoulMonsterState.DISAPPEAR -> MonsterSpiritSounds.DISAPPEAR.play(battle.enemy.location)
        }
    }

}