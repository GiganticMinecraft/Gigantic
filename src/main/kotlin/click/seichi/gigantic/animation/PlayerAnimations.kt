package click.seichi.gigantic.animation

import click.seichi.gigantic.util.Random
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Firework

/**
 *
 * @author tar0ss
 */
object PlayerAnimations {

    val LEVEL_UP = Animation(60) { location, _ ->
        location.world.spawnParticle(Particle.REDSTONE, location, 10,
                Random.nextGaussian(0.0, 1.0),
                Random.nextGaussian(0.0, 2.0),
                Random.nextGaussian(0.0, 1.0)
        )
    }

    val LAUNCH_FIREWORK = Animation(1) { location, _ ->
        launchFireWorks(location)
    }


    private val types = arrayOf(FireworkEffect.Type.BALL, FireworkEffect.Type.BALL_LARGE, FireworkEffect.Type.BURST, FireworkEffect.Type.CREEPER, FireworkEffect.Type.STAR)

    /**花火を打ち上げる
     *
     */
    fun launchFireWorks(location: Location) {
        val firework = location.world.spawn(location, Firework::class.java)

        // 花火の設定情報オブジェクトを取り出す
        val meta = firework.fireworkMeta
        val effect = FireworkEffect.builder()


        // 形状をランダムに決める
        effect.with(types[Random.nextInt(types.size)])

        // 基本の色を単色～5色以内でランダムに決める
        effect.withColor(*getRandomColors(1 + Random.nextInt(5)))

        // 余韻の色を単色～3色以内でランダムに決める
        effect.withFade(*getRandomColors(1 + Random.nextInt(3)))

        // 爆発後に点滅するかをランダムに決める
        effect.flicker(Random.nextBoolean())

        // 爆発後に尾を引くかをランダムに決める
        effect.trail(Random.nextBoolean())

        // 打ち上げ高さを1以上4以内でランダムに決める
        // （5以上は公式の不具合でクライアントの負荷が増大する）
        meta.power = 1 + Random.nextInt(4)

        // 花火の設定情報を花火に設定
        meta.addEffect(effect.build())
        firework.fireworkMeta = meta
    }

    // カラーをランダムで決める
    private fun getRandomColors(length: Int): Array<Color> {
        return (0 until length).map { Color.fromBGR(Random.nextInt(1 shl 24)) }.toTypedArray()
    }

}