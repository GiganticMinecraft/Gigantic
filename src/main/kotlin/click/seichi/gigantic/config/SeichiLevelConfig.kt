package click.seichi.gigantic.config

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.util.Polynomial
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * @author tar0ss
 */
object SeichiLevelConfig : SimpleConfiguration("seichi_level", Gigantic.PLUGIN) {

    private val mineBlockPolynomial = Polynomial(-3.0000, 6.3779, -5.4233, 1.9360, 0.11550)
    private val manaPolynomial = Polynomial(-83.30, 12.50, 0.5833)

    val MAX = getInt("max")

    private val MINEBLOCK_MAP by lazy {
        (1..MAX).map {
            it to getLong("level_map.$it.mineblock")
        }.toMap()
    }

    private val MANA_MAP by lazy {
        (1..MAX).map {
            it to getLong("level_map.$it.mana")
        }.toMap()
    }

    override fun makeFile(file: File, plugin: JavaPlugin, fileName: String) {
        file.printWriter().use { out ->
            out.println("max: 999")
            out.println("level_map:")
            for (level in 1..MAX) {
                val needMineBlock = mineBlockPolynomial.calculation(level)
                val mana = when (level) {
                    in 1 until 10 -> 0L
                    else -> manaPolynomial.calculation(level)
                }
                out.println("  $level:")
                out.println("    mineblock : $needMineBlock")
                out.println("    mana : $mana")
            }
        }
    }
}