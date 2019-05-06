package click.seichi.gigantic.util

import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import click.seichi.gigantic.will.WillSize
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import kotlin.random.asKotlinRandom

/**
 * @author unicroak
 * @author tar0ss
 */
internal object Random {

    val generator = java.util.Random()

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

    fun nextColor() = Color.fromRGB(nextInt(255), nextInt(255), nextInt(255))

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

    fun nextChatColor() = chatColorSet.random(generator.asKotlinRandom())

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

    fun nextWool() = woolSet.random(generator.asKotlinRandom())

    private val eggSet = setOf(
            Material.BAT_SPAWN_EGG,
            Material.BLAZE_SPAWN_EGG,
            Material.CAVE_SPIDER_SPAWN_EGG,
            Material.CHICKEN_SPAWN_EGG,
            Material.COD_SPAWN_EGG,
            Material.COW_SPAWN_EGG,
            Material.CREEPER_SPAWN_EGG,
            Material.DOLPHIN_SPAWN_EGG,
            Material.DONKEY_SPAWN_EGG,
            Material.DROWNED_SPAWN_EGG,
            Material.ELDER_GUARDIAN_SPAWN_EGG,
            Material.ENDERMAN_SPAWN_EGG,
            Material.ENDERMITE_SPAWN_EGG,
            Material.EVOKER_SPAWN_EGG,
            Material.GHAST_SPAWN_EGG,
            Material.GUARDIAN_SPAWN_EGG,
            Material.HORSE_SPAWN_EGG,
            Material.HUSK_SPAWN_EGG,
            Material.LLAMA_SPAWN_EGG,
            Material.ZOMBIE_VILLAGER_SPAWN_EGG,
            Material.ZOMBIE_SPAWN_EGG,
            Material.ZOMBIE_HORSE_SPAWN_EGG,
            Material.ZOMBIE_PIGMAN_SPAWN_EGG,
            Material.WOLF_SPAWN_EGG,
            Material.WITHER_SKELETON_SPAWN_EGG,
            Material.WITCH_SPAWN_EGG,
            Material.VINDICATOR_SPAWN_EGG,
            Material.VILLAGER_SPAWN_EGG,
            Material.VEX_SPAWN_EGG,
            Material.TURTLE_SPAWN_EGG,
            Material.TROPICAL_FISH_SPAWN_EGG,
            Material.STRAY_SPAWN_EGG,
            Material.SQUID_SPAWN_EGG,
            Material.SPIDER_SPAWN_EGG,
            Material.SLIME_SPAWN_EGG,
            Material.SKELETON_SPAWN_EGG,
            Material.SKELETON_HORSE_SPAWN_EGG,
            Material.SILVERFISH_SPAWN_EGG,
            Material.SHULKER_SPAWN_EGG,
            Material.SHEEP_SPAWN_EGG,
            Material.SALMON_SPAWN_EGG,
            Material.RABBIT_SPAWN_EGG,
            Material.PUFFERFISH_SPAWN_EGG,
            Material.POLAR_BEAR_SPAWN_EGG,
            Material.PIG_SPAWN_EGG,
            Material.PHANTOM_SPAWN_EGG,
            Material.PARROT_SPAWN_EGG,
            Material.OCELOT_SPAWN_EGG,
            Material.MULE_SPAWN_EGG,
            Material.MOOSHROOM_SPAWN_EGG,
            Material.MAGMA_CUBE_SPAWN_EGG
    )

    fun nextEgg() = eggSet.random(generator.asKotlinRandom())
}