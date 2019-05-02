package click.seichi.gigantic.tool

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.item.HandItem
import click.seichi.gigantic.item.items.HandItems
import click.seichi.gigantic.player.Defaults
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
enum class Tool(
        val id: Int,
        private val tool: HandItem
) {
    PICKEL(1, HandItems.PICKEL),
    SHOVEL(2, HandItems.SHOVEL),
    AXE(3, HandItems.AXE),
    //SWORD(4, HandItems.SWORD)
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]

        fun findSuitableTool(block: Block): Tool? {
            return when (block.type) {
                Material.STONE,
                Material.MOSSY_COBBLESTONE,
                Material.MOSSY_COBBLESTONE_WALL,
                Material.MOSSY_STONE_BRICKS,
                Material.SANDSTONE,
                Material.CHISELED_RED_SANDSTONE,
                Material.CHISELED_SANDSTONE,
                Material.CUT_RED_SANDSTONE,
                Material.CUT_SANDSTONE,
                Material.SMOOTH_RED_SANDSTONE,
                Material.SMOOTH_SANDSTONE,
                Material.OBSIDIAN,
                Material.DIORITE,
                Material.ANDESITE,
                Material.STONE_BRICKS,
                Material.CHISELED_STONE_BRICKS,
                Material.CRACKED_STONE_BRICKS,
                Material.EMERALD_ORE,
                Material.REDSTONE_ORE,
                Material.LAPIS_ORE,
                Material.GOLD_ORE,
                Material.IRON_ORE,
                Material.DIAMOND_ORE,
                Material.COAL_ORE,
                Material.NETHER_QUARTZ_ORE,
                Material.NETHERRACK,
                Material.END_STONE,
                Material.TERRACOTTA,
                Material.ICE,
                Material.FROSTED_ICE,
                Material.PURPUR_BLOCK,
                Material.BONE_BLOCK,
                Material.OAK_PLANKS,
                Material.SPRUCE_PLANKS,
                Material.BIRCH_PLANKS,
                Material.JUNGLE_PLANKS,
                Material.ACACIA_PLANKS,
                Material.DARK_OAK_PLANKS,
                Material.WHITE_TERRACOTTA,
                Material.ORANGE_TERRACOTTA,
                Material.MAGENTA_TERRACOTTA,
                Material.LIGHT_BLUE_TERRACOTTA,
                Material.YELLOW_TERRACOTTA,
                Material.LIME_TERRACOTTA,
                Material.PINK_TERRACOTTA,
                Material.GRAY_TERRACOTTA,
                Material.LIGHT_GRAY_TERRACOTTA,
                Material.CYAN_TERRACOTTA,
                Material.PURPLE_TERRACOTTA,
                Material.BLUE_TERRACOTTA,
                Material.BROWN_TERRACOTTA,
                Material.GREEN_TERRACOTTA,
                Material.RED_TERRACOTTA,
                Material.BLACK_TERRACOTTA,
                Material.PRISMARINE,
                Material.PRISMARINE_BRICKS,
                Material.DARK_PRISMARINE,
                Material.PACKED_ICE,
                Material.BLUE_ICE,
                Material.MAGMA_BLOCK,
                Material.INFESTED_CHISELED_STONE_BRICKS,
                Material.INFESTED_COBBLESTONE,
                Material.INFESTED_CRACKED_STONE_BRICKS,
                Material.INFESTED_MOSSY_STONE_BRICKS,
                Material.INFESTED_STONE,
                Material.INFESTED_STONE_BRICKS,
                Material.COBBLESTONE,
                Material.GRANITE,
                Material.TUBE_CORAL_BLOCK,
                Material.HORN_CORAL_BLOCK,
                Material.FIRE_CORAL_BLOCK,
                Material.BUBBLE_CORAL_BLOCK,
                Material.BRAIN_CORAL_BLOCK,
                Material.DEAD_TUBE_CORAL_BLOCK,
                Material.DEAD_HORN_CORAL_BLOCK,
                Material.DEAD_FIRE_CORAL_BLOCK,
                Material.DEAD_BUBBLE_CORAL_BLOCK,
                Material.DEAD_BRAIN_CORAL_BLOCK -> PICKEL

                Material.RED_SAND,
                Material.DIRT,
                Material.GRASS_BLOCK,
                Material.GRASS_PATH,
                Material.SAND,
                Material.GRAVEL,
                Material.CLAY,
                Material.COARSE_DIRT,
                Material.PODZOL,
                Material.SNOW_BLOCK,
                Material.SNOW,
                Material.MYCELIUM -> SHOVEL

                Material.BIRCH_LOG,
                Material.ACACIA_LOG,
                Material.DARK_OAK_LOG,
                Material.JUNGLE_LOG,
                Material.OAK_LOG,
                Material.SPRUCE_LOG,
                Material.STRIPPED_BIRCH_LOG,
                Material.STRIPPED_ACACIA_LOG,
                Material.STRIPPED_DARK_OAK_LOG,
                Material.STRIPPED_JUNGLE_LOG,
                Material.STRIPPED_OAK_LOG,
                Material.STRIPPED_SPRUCE_LOG,
                Material.MUSHROOM_STEM,
                Material.RED_MUSHROOM_BLOCK,
                Material.BROWN_MUSHROOM_BLOCK,
                Material.PUMPKIN,
                Material.MELON -> AXE

                Material.SMOOTH_STONE -> SHOVEL

                Material.SPONGE,
                Material.WET_SPONGE -> null
                else -> null
            }
        }
    }

    fun findItemStack(player: Player) = tool.toShownItemStack(player)

    fun update(player: Player) {
        val slot = player.getOrPut(Keys.BELT).toolSlot
        player.inventory.setItem(slot, findItemStack(player) ?: Defaults.ITEM)
    }


    fun grant(player: Player) {
        player.offer(Keys.TOOL_UNLOCK_MAP[this]!!, true)
    }

    fun revoke(player: Player) {
        player.offer(Keys.TOOL_UNLOCK_MAP[this]!!, false)
        player.offer(Keys.TOOL_TOGGLE_MAP[this]!!, false)
    }

    fun canSwitch(player: Player): Boolean {
        if (!player.getOrPut(Keys.TOOL_UNLOCK_MAP[this]!!)) return false

        return player.getOrPut(Keys.TOOL_TOGGLE_MAP[this]!!)
    }

    fun isGranted(player: Player): Boolean {
        return player.getOrPut(Keys.TOOL_UNLOCK_MAP[this]!!)
    }

    fun toggle(player: Player): Boolean {
        var canSwitch = false
        if (isGranted(player))
            player.transform(Keys.TOOL_TOGGLE_MAP[this]!!) {
                canSwitch = !it
                canSwitch
            }
        return canSwitch
    }

}