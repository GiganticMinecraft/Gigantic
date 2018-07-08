package click.seichi.gigantic.config

import click.seichi.gigantic.util.Polynomial
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author tar0ss
 */
object SeichiLevelConfig : MineBlockBasedLevelConfig("seichi_level") {

    val manaMap by lazy {
        (1..max).map {
            it to getLong("level_map.$it.mana")
        }.toMap()
    }

    override fun makeFile(file: File, plugin: JavaPlugin, fileName: String) {
        val defaultMaxLevel = 999
        val mineBlockPolynomial = Polynomial(-2.0000, 6.3779, -5.4233, 1.9360, 0.11550)
        val manaPolynomial = Polynomial(-83.30, 12.50, 0.5833)

        file.printWriter().use { out ->
            out.println("max: $defaultMaxLevel")
            out.println("level_map:")
            for (level in 1..defaultMaxLevel) {
                val needMineBlock = mineBlockPolynomial.calculation(level)
                val mana = when (level) {
                    in 1 until 10 -> 0L
                    else -> manaPolynomial.calculation(level)
                }
                out.println("  $level:")
                out.println("    mineBlock : $needMineBlock")
                out.println("    mana : $mana")
            }
        }
    }
}