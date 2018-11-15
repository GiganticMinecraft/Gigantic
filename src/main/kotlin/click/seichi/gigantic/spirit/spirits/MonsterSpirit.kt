package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.battle.Battle
import click.seichi.gigantic.battle.BattleManager
import click.seichi.gigantic.sound.sounds.SoulMonsterSounds
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
                    player != battle.spawner -> false
                    player.location.distance(battle.enemy.location) > 2.5 -> false
                    else -> true
                }
            },
            { player, count ->
                player ?: return@Sensor
                if (!battle.isJoined(player))
                    battle.join(player)
                battle.updateAwakeProgress(count.div(senseDuration.toDouble()))
                if (count % 10 == 0)
                    SoulMonsterSounds.SENSE_SUB.playOnly(player)
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
        battle.spawnEnemy()
    }

    override fun onRender() {
        battle.getJoinedPlayers().filter { !it.isValid }
                .forEach { battle.leave(it) }
        if (!battle.isStarted) {
            sensor.update()
        }
        battle.update()

        if (battle.isEnded) {
            BattleManager.endBattle(battle)
            remove()
        }
    }

    override fun onRemove() {
//        when (battle.enemy.state) {
//            SoulMonsterState.DISAPPEAR -> SoulMonsterSounds.DISAPPEAR.play(battle.enemy.location)
//        }
    }

}