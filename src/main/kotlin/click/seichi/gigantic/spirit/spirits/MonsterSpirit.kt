package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.battle.Battle
import click.seichi.gigantic.monster.SoulMonsterState
import click.seichi.gigantic.sound.sounds.MonsterSpiritSounds
import click.seichi.gigantic.spirit.Sensor
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason

/**
 * @author tar0ss
 */
class MonsterSpirit(
        spawnReason: SpawnReason,
        private val battle: Battle
) : Spirit(spawnReason, battle.chunk) {

    private val senseDuration = 60

    private val sensor = Sensor(
            battle.enemy.location,
            { player ->
                player ?: return@Sensor false
                when {
                    player.location.distance(battle.enemy.location) > 2.5 -> false
                    else -> true
                }
            },
            { player, count ->
                player ?: return@Sensor
                if (!battle.isJoined(player))
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
        val spawner = battle.getSpawner() ?: return true
        when {
            spawner.location.distance(battle.enemy.location) > 30.0 && !battle.isStarted -> return true
            battle.getJoinedPlayers().isEmpty() && battle.isStarted -> return true
        }
        return false
    }

    override fun onRender() {
        battle.getJoinedPlayers().filter { !it.isValid }
                .forEach { battle.leave(it) }
        if (disappearCondition()) {
            battle.end()
            remove()
        }
        if (!battle.isStarted) {
            sensor.update()
        }
        battle.update()
    }

    override fun onRemove() {
        when (battle.enemy.state) {
            SoulMonsterState.DISAPPEAR -> MonsterSpiritSounds.DISAPPEAR.play(battle.enemy.location)
        }
    }

}