package click.seichi.gigantic.extension

import click.seichi.gigantic.util.Junishi
import click.seichi.gigantic.util.MoonPhase
import click.seichi.gigantic.util.NoiseData
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.World

/**
 * @author unicroak
 */
fun World.spawnColoredParticle(
        location: Location,
        color: Color,
        count: Int = 1,
        noiseData: NoiseData = NoiseData()
) = players.forEach { it.spawnColoredParticle(location, color, count, noiseData) }

fun World.spawnColoredParticleSpherically(
        location: Location,
        color: Color,
        count: Int = 1,
        radius: Double
) = players.forEach { it.spawnColoredParticleSpherically(location, color, count, radius) }

// 月齢が1週する間隔
private const val INTERVAL = 24000L.times(8L)


val World.moonPhase: MoonPhase
    get() = when ((fullTime % INTERVAL).div(24000).toInt()) {
        0 -> MoonPhase.MANGETSU
        1 -> MoonPhase.IMACHIZUKI
        2 -> MoonPhase.KAGEN
        3 -> MoonPhase.NIZYUROKUYA
        4 -> MoonPhase.SHINGETSU
        5 -> MoonPhase.MIKAZUKI
        6 -> MoonPhase.ZYOGEN
        7 -> MoonPhase.ZYUUSANYA
        else -> MoonPhase.MANGETSU
    }

// 月が見える時間
private const val MOON_RISE_TIME = 12567L

// 月が沈む
private const val MOON_SET_TIME = 22917L

// 月が昇っているか
val World.isMoonRising: Boolean
    get() = time in MOON_RISE_TIME..MOON_SET_TIME


// 十二時辰
val World.junishiOfTime: Junishi
    get() = when (time) {
        in 17001..19000 -> Junishi.NE
        in 19001..21000 -> Junishi.USHI
        in 21001..23000 -> Junishi.TORA
        in 23001..24000 -> Junishi.U
        in 1..1000 -> Junishi.U
        in 1001..3000 -> Junishi.TATSU
        in 3001..5000 -> Junishi.MI
        in 5001..7000 -> Junishi.UMA
        in 7001..9000 -> Junishi.HITSUJI
        in 9001..11000 -> Junishi.SARU
        in 11001..13000 -> Junishi.TORI
        in 13001..15000 -> Junishi.INU
        in 15001..17000 -> Junishi.I
        else -> Junishi.NE
    }
