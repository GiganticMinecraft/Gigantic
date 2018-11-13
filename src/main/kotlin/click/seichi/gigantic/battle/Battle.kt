package click.seichi.gigantic.battle

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.MonsterSpiritAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.MonsterSpiritMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.monster.SoulMonsterState
import click.seichi.gigantic.sound.sounds.BattleSounds
import click.seichi.gigantic.sound.sounds.MonsterSpiritSounds
import org.bukkit.ChatColor
import org.bukkit.block.Block
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class Battle internal constructor(
        private val spawner: Player,
        private val monster: SoulMonster
) {

    private val locale = spawner.wrappedLocale

    var elapsedTick: Long = -1L

    val chunk = spawner.location.chunk

    val enemy = BattleMonster(monster, spawner)

    private val players = mutableListOf<Player>()

    var isStarted: Boolean = false
        private set

    private val bossBar = Gigantic.createInvisibleBossBar().apply {
        isVisible = true
        style = BarStyle.SOLID
        title = "${ChatColor.RED}" +
                MonsterSpiritMessages.SEAL(monster.getName(locale)).asSafety(locale)
        color = BarColor.RED
        progress = 0.0
    }


    fun getJoinedPlayers() = players.toList()

    fun getSpawner() = if (spawner.isValid) spawner else null

    fun isJoined(player: Player) = players.contains(player)

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
        if (player == enemy.target) {
            players.toList().shuffled().firstOrNull()?.let {
                enemy.setTargetPlayer(it)
            }
        }
    }

    fun spawn() {
        enemy.spawn()
        MonsterSpiritSounds.SPAWN.play(enemy.location)
    }

    fun start() {
        enemy.setNextLocation()
        bossBar.apply {
            color = BarColor.PURPLE
            style = BarStyle.SEGMENTED_20
            title = "${ChatColor.LIGHT_PURPLE}" +
                    MonsterSpiritMessages.START(monster.getName(locale)).asSafety(locale)
        }
        players.forEach {
            BattleSounds.START.playOnly(it)
        }
        isStarted = true
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
        enemy.updateLocation()
        MonsterSpiritAnimations.AMBIENT(monster.color).start(enemy.eyeLocation)
        when (enemy.state) {
            SoulMonsterState.SEAL -> {
                if (elapsedTick % 8L == 0L)
                    MonsterSpiritAnimations.AMBIENT_EXHAUST(monster.color).exhaust(
                            spawner,
                            enemy.eyeLocation.clone().add(0.0, 0.9, 0.0),
                            meanY = 0.9
                    )
            }
            SoulMonsterState.WAKE -> {
                MonsterSpiritAnimations.WAKE.start(enemy.eyeLocation.clone().add(0.0, 0.9, 0.0))
            }
            SoulMonsterState.DISAPPEAR -> {
            }
            SoulMonsterState.WAIT -> {
                enemy.setNextLocation()
            }
            SoulMonsterState.MOVE -> {
                enemy.move()
            }
            SoulMonsterState.ATTACK -> {
                enemy.attack()
            }
            SoulMonsterState.DEATH -> TODO()
        }
        elapsedTick++
    }

    fun tryDefence(player: Player, block: Block) {
        val locations = player.getOrPut(Keys.ATTACK_WAIT_LOCATION_SET)
        if (locations.remove(block.location)) {
            enemy.defencedByPlayer(block)
        }
    }

}