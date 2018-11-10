package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.MonsterSpiritMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.popup.pops.MonsterSpiritPops
import click.seichi.gigantic.sound.sounds.MonsterSpiritSounds
import click.seichi.gigantic.spirit.Sensor
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import org.bukkit.Location
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class MonsterSpirit(
        spawnReason: SpawnReason,
        private val location: Location,
        val monster: SoulMonster,
        val targetPlayer: Player? = null
) : Spirit(spawnReason, location.chunk) {
    enum class MonsterState {
        // 待機
        WAIT,
        // 目覚め
        WAKE,
        // 消滅
        DISAPPEAR,
        // 移動
        MOVE,
        // 攻撃
        ATTACK,
        // 死亡
        DEATH;
    }

    private lateinit var monsterHeadEntity: ArmorStand

    private var monsterState = MonsterState.WAIT

    private val senseDuration = 60

    private val senseBar = Gigantic.createInvisibleBossBar().apply {
        targetPlayer ?: return@apply
        isVisible = true
        style = BarStyle.SOLID
        title = MonsterSpiritMessages.SPIRIT_SEALED(monster.getName(targetPlayer)).asSafety(targetPlayer.wrappedLocale)
        color = BarColor.RED
        progress = 0.0
    }

    private val sensor = Sensor(
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
                monsterState = MonsterState.WAKE
                senseBar.apply {
                    val prev = progress
                    val next = count.div(senseDuration.toDouble())
                    if (next > prev)
                        progress = next
                    addPlayer(player)
                }
                if (count % 10 == 0)
                    MonsterSpiritSounds.SENSE_SUB.playOnly(player)
            },
            { player ->
                player ?: return@Sensor
                player.sendMessage("sensed!!!")
            },
            { player ->
                player ?: return@Sensor
                senseBar.apply {
                    removePlayer(player)
                    if (players.isEmpty()) {
                        progress = 0.0
                        monsterState = MonsterState.WAIT
                    }
                }
            },
            senseDuration
    )

    override val lifespan = -1
    override val spiritType = SpiritType.MONSTER

    private val fixedLocation: Location?
        get() =
            if (targetPlayer == null) null
            else {
                val eyeLocation = targetPlayer.eyeLocation.clone()
                val entityLocation = location.clone()
                location.clone().apply {
                    this.direction = eyeLocation.subtract(entityLocation).toVector().normalize()
                }.subtract(0.0, 0.5, 0.0)
            }


    override fun onSpawn() {
        targetPlayer ?: return
        MonsterSpiritSounds.SPAWN.play(location)
        monsterHeadEntity = MonsterSpiritPops.SPAWN(
                monster.getIcon(targetPlayer)
        ).pop(fixedLocation ?: return)
    }

    private var ticks: Long = 0L

    private fun disappearCondition(): Boolean {
        targetPlayer ?: return true
        return targetPlayer.location.distance(location) > 30.0 &&
                monsterState == MonsterState.WAIT
    }

    override fun onRender() {
        if (disappearCondition()) {
            monsterState = MonsterState.DISAPPEAR
            remove()
        }

        monsterHeadEntity.teleport(fixedLocation)
        MonsterSpiritAnimations.AMBIENT(monster.color).start(fixedLocation?.clone()?.add(0.0, 0.7, 0.0) ?: return)
        // in waiting
        when (monsterState) {
            MonsterState.WAIT -> {
                if (ticks % 8L == 0L)
                    MonsterSpiritAnimations.AMBIENT_EXHAUST(monster.color).exhaust(
                            targetPlayer ?: return,
                            fixedLocation?.clone()?.add(0.0, 0.9, 0.0) ?: return,
                            meanY = 0.9
                    )
                sensor.update()
            }
            MonsterState.WAKE -> {
                MonsterSpiritAnimations.WAKE.start(fixedLocation?.clone()?.add(0.0, 0.9, 0.0) ?: return)
                sensor.update()
            }
        }
        ticks++
    }

    override fun onRemove() {
        when (monsterState) {
            MonsterState.DISAPPEAR -> MonsterSpiritSounds.DISAPPEAR.play(location)
        }
    }

}