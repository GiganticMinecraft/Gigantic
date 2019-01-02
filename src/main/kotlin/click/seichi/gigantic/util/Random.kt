package click.seichi.gigantic.util

import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import click.seichi.gigantic.will.WillSize
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material

/**
 * @author unicroak
 * @author tar0ss
 */
internal object Random {

    private val generator = java.util.Random()

    fun nextBoolean() = generator.nextBoolean()

    fun nextDouble() = generator.nextDouble()

    fun nextDouble(to: Double) = generator.nextDouble() * to

    fun nextDouble(from: Double, to: Double) = generator.nextDouble() * to + from

    fun nextInt() = generator.nextInt()

    fun nextInt(to: Int) = generator.nextInt(to)

    fun nextInt(from: Int, to: Int) = generator.nextInt(to - from) + from

    fun nextSign() = if (nextDouble() < 0.5) 1 else -1

    fun nextGaussian(mean: Double = 0.0, variance: Double = 1.0) = generator.nextGaussian() * variance + mean

    fun nextWill() = Will.values().toList().shuffled().first()

    fun nextWill(grade: WillGrade) = Will.values().filter { it.grade == grade }.toList().shuffled().first()

    tailrec fun nextWillSizeWithRegularity(): WillSize = WillSize.values().toList()
            .shuffled()
            .firstOrNull { Random.nextDouble() < it.probability }
            ?.let { it }
            ?: nextWillSizeWithRegularity()

    fun nextColor() = Color.fromRGB(nextInt(255), nextInt(255), nextInt(255))!!

    private val chatColorSet = setOf(
            ChatColor.YELLOW,
            ChatColor.LIGHT_PURPLE,
            ChatColor.GOLD,
            ChatColor.GREEN,
            ChatColor.RED,
            ChatColor.AQUA,
            ChatColor.BLUE,
            ChatColor.GRAY
    )

    fun nextChatColor() = chatColorSet.random()

    private val woolSet = setOf(
            Material.RED_WOOL,
            Material.YELLOW_WOOL,
            Material.GREEN_WOOL,
            Material.BLUE_WOOL,
            Material.BROWN_WOOL,
            Material.CYAN_WOOL,
            Material.LIGHT_BLUE_WOOL,
            Material.LIME_WOOL,
            Material.MAGENTA_WOOL,
            Material.ORANGE_WOOL,
            Material.PINK_WOOL,
            Material.PURPLE_WOOL,
            Material.YELLOW_WOOL
    )

    fun nextWool() = woolSet.random()
}