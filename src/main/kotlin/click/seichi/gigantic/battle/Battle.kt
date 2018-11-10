package click.seichi.gigantic.battle

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.MonsterSpiritMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.SoulMonsterState
import click.seichi.gigantic.sound.sounds.MonsterSpiritSounds
import org.bukkit.Location
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class Battle(
        private val monster: SoulMonster,
        private val spawner: Player,
        spawnLocation: Location
) {

    private val locale = spawner.wrappedLocale

    var elapsedTick: Long = -1L

    val chunk = spawnLocation.chunk

    val enemy = BattleMonster(monster, spawner, spawnLocation)

    private val players = mutableListOf<Player>()

    private val bossBar = Gigantic.createInvisibleBossBar().apply {
        isVisible = true
        style = BarStyle.SOLID
        title = MonsterSpiritMessages.SPIRIT_SEALED(monster.getName(locale)).asSafety(locale)
        color = BarColor.RED
        progress = 0.0
    }

    fun getJoinedPlayers() = players.toList()

    fun isJoin(player: Player) = players.contains(player)

    fun join(player: Player): Boolean {
        return when (enemy.state) {
            SoulMonsterState.SEAL,
            SoulMonsterState.WAKE -> {
                players.add(player)
                bossBar.addPlayer(player)
                true
            }
            else -> {
                false
            }
        }
    }

    fun leave(player: Player) {
        players.remove(player)
        bossBar.apply {
            removePlayer(player)
            if (players.isEmpty()) {
                progress = 0.0
                enemy.reset()
            }
        }
    }

    fun spawn() {
        enemy.spawn()
        MonsterSpiritSounds.SPAWN.play(enemy.location)
    }

    fun start() {
        enemy.awake()
    }

    fun end() {
        bossBar.removeAll()
        enemy.remove()
    }

    fun wake(nextProgress: Double) {
        bossBar.apply {
            val prev = progress
            if (nextProgress > prev)
                progress = nextProgress
        }
        enemy.wake()
    }

    fun update() {
        enemy.update()
        MonsterSpiritAnimations.AMBIENT(monster.color).start(enemy.eyeLocation)
        when (enemy.state) {
            SoulMonsterState.SEAL -> {
                if (elapsedTick % 8L == 0L)
                    MonsterSpiritAnimations.AMBIENT_EXHAUST(monster.color).exhaust(
                            spawner,
                            enemy.eyeLocation
                    )
            }
            SoulMonsterState.WAKE -> {
                MonsterSpiritAnimations.WAKE.start(enemy.eyeLocation)
            }
            SoulMonsterState.DISAPPEAR -> {
            }
            SoulMonsterState.WAIT -> {
                // move to 3 step
            }
            SoulMonsterState.MOVE -> TODO()
            SoulMonsterState.ATTACK -> TODO()
            SoulMonsterState.DEATH -> TODO()
        }
        elapsedTick++
    }

}