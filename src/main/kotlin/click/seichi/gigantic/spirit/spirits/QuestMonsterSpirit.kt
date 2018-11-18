package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.battle.BattleManager
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.BattleMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.sound.sounds.SoulMonsterSounds
import click.seichi.gigantic.spirit.Sensor
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import click.seichi.gigantic.topbar.bars.BattleBars
import click.seichi.gigantic.util.Random
import org.bukkit.Chunk
import org.bukkit.boss.BossBar
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.util.EulerAngle

/**
 * @author tar0ss
 */
class QuestMonsterSpirit(
        spawnReason: SpawnReason,
        val chunk: Chunk,
        val spawner: Player,
        val monster: SoulMonster,
        val quest: Quest
) : Spirit(spawnReason, chunk) {

    private val senseDuration = 60

    // モンスターのボスバー
    private val bossBar: BossBar = Gigantic.createInvisibleBossBar()

    // モンスターの実体
    private lateinit var entity: ArmorStand

    private val players: MutableSet<Player> = mutableSetOf()

    private var isStarted = false

    private lateinit var sensor: Sensor

    override val lifespan = -1
    override val spiritType = SpiritType.MONSTER

    override fun onSpawn() {
        spawner.offer(Keys.BATTLE_SPIRIT, this)
        var spawnLocation = spawner.location
        while (spawner.location.distance(spawnLocation) <= 3.0) {
            spawnLocation = chunk.getBlock(Random.nextInt(15), 0, Random.nextInt(15)).let { block ->
                chunk.world.getHighestBlockAt(block.location).centralLocation.add(0.0, -0.5, 0.0)
            }
        }
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
                headPose = EulerAngle(0.3, 0.0, 0.0)
            }
        }
        sensor = Sensor(
                entity.eyeLocation,
                { player ->
                    player ?: return@Sensor false
                    when {
                        player != spawner -> false
                        player.location.distance(entity.eyeLocation) > 2.5 -> false
                        else -> true
                    }
                },
                { player, count ->
                    player ?: return@Sensor
                    players.add(player)
                    bossBar.addPlayer(player)
                    val nextProgress = count.div(senseDuration.toDouble())
                    BattleBars.SEAL(nextProgress, monster, spawner.wrappedLocale).show(bossBar)
                    MonsterSpiritAnimations.AWAKE.start(entity.eyeLocation.add(0.0, 0.9, 0.0))
                    if (count % 10 == 0)
                        SoulMonsterSounds.SENSE_SUB.playOnly(player)
                },
                { player ->
                    player ?: return@Sensor
                    BattleManager.newBattle(chunk, spawner, players, monster, quest).run {
                        start(entity.location)
                    }
                    isStarted = true
                },
                { player ->
                    player ?: return@Sensor
                    players.remove(player)
                    bossBar.removePlayer(player)
                    if (players.isEmpty())
                        BattleBars.RESET(monster, spawner.wrappedLocale).show(bossBar)
                },
                senseDuration
        )
        SoulMonsterSounds.SPAWN.play(spawnLocation)
        BattleMessages.SPAWN(monster).sendTo(spawner)
    }

    private var elapsedTick = -1L

    override fun onRender() {
        elapsedTick++
        sensor.update()
        if (elapsedTick % 8L == 0L)
            MonsterSpiritAnimations.AMBIENT_EXHAUST(monster.color).exhaust(
                    spawner,
                    entity.eyeLocation,
                    meanY = 0.9
            )
        MonsterSpiritAnimations.AMBIENT(monster.color).start(entity.eyeLocation)

        if (!spawner.isValid || spawner.location.distance(entity.eyeLocation) > 30 || isStarted) {
            entity.remove()
            bossBar.removeAll()
            remove()
        }
    }

    override fun onRemove() {
        spawner.offer(Keys.BATTLE_SPIRIT, null)
    }

}