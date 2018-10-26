package click.seichi.gigantic

import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.DatabaseConfig
import click.seichi.gigantic.database.table.*
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.register
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.listener.*
import click.seichi.gigantic.listener.packet.ExperienceOrbSpawn
import click.seichi.gigantic.raid.RaidManager
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketListener
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.ArmorStand
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * @author tar0ss
 * @unicroak
 */
class Gigantic : JavaPlugin() {

    companion object {
        lateinit var PLUGIN: Gigantic
        // protocolLib manager
        lateinit var PROTOCOL_MG: ProtocolManager

        val DEFAULT_LOCALE = Locale.JAPANESE!!

        fun createInvisibleBossBar(): BossBar = Bukkit.createBossBar(
                "title",
                BarColor.YELLOW,
                BarStyle.SOLID
        ).apply {
            isVisible = false
        }

        // 落下判定に使う。自然生成されるブロックのみ
        val CRUSTS = setOf(
                Material.STONE,
                Material.GRANITE,
                Material.MOSSY_COBBLESTONE,
                Material.MOSSY_COBBLESTONE_WALL,
                Material.MOSSY_STONE_BRICKS,
                Material.RED_SAND,
                Material.SANDSTONE,
                Material.CHISELED_RED_SANDSTONE,
                Material.CHISELED_SANDSTONE,
                Material.CUT_RED_SANDSTONE,
                Material.CUT_SANDSTONE,
                Material.SMOOTH_RED_SANDSTONE,
                Material.SMOOTH_SANDSTONE,
                Material.SANDSTONE,
                Material.OBSIDIAN,
                Material.DIORITE,
                Material.ANDESITE,
                Material.STONE_BRICKS,
                Material.CHISELED_STONE_BRICKS,
                Material.CRACKED_STONE_BRICKS,
                Material.DIRT,
                Material.GRASS_BLOCK,
                Material.EMERALD_ORE,
                Material.REDSTONE_ORE,
                Material.LAPIS_ORE,
                Material.GOLD_ORE,
                Material.IRON_ORE,
                Material.DIAMOND_ORE,
                Material.COAL_ORE,
                Material.REDSTONE_ORE,
                Material.NETHER_QUARTZ_ORE,
                Material.NETHERRACK,
                Material.SAND,
                Material.GRAVEL,
                Material.MOSSY_COBBLESTONE,
                Material.END_STONE,
                Material.TERRACOTTA,
                Material.ICE,
                Material.FROSTED_ICE,
                Material.PURPUR_BLOCK,
                Material.BONE_BLOCK,
                Material.CLAY,
                Material.COARSE_DIRT,
                Material.PODZOL,
                Material.OAK_PLANKS,
                Material.SPRUCE_PLANKS,
                Material.BIRCH_PLANKS,
                Material.JUNGLE_PLANKS,
                Material.ACACIA_PLANKS,
                Material.DARK_OAK_PLANKS,
                Material.SPONGE,
                Material.WET_SPONGE,
                Material.COBWEB,
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
                Material.TERRACOTTA,
                Material.PRISMARINE,
                Material.PRISMARINE_BRICKS,
                Material.DARK_PRISMARINE
        )

        val LOGS = setOf(
                Material.BIRCH_LOG,
                Material.ACACIA_LOG,
                Material.DARK_OAK_LOG,
                Material.JUNGLE_LOG,
                Material.OAK_LOG,
                Material.SPRUCE_LOG
        )

        val LEAVES = setOf(
                Material.ACACIA_LEAVES,
                Material.BIRCH_LEAVES,
                Material.DARK_OAK_LEAVES,
                Material.JUNGLE_LEAVES,
                Material.OAK_LEAVES,
                Material.SPRUCE_LEAVES
        )

        val AIRS = setOf(
                Material.AIR,
                Material.CAVE_AIR,
                Material.VOID_AIR
        )

        val TREES = setOf(*LOGS.toTypedArray(), *LEAVES.toTypedArray())
    }

    override fun onEnable() {
        PLUGIN = this
        PROTOCOL_MG = ProtocolLibrary.getProtocolManager()

        // Remove all armor stands
        server.worlds.forEach { world -> world.getEntitiesByClass(ArmorStand::class.java).forEach { it.remove() } }

        registerListeners(
                MenuListener(),
                PlayerListener(),
                SpiritListener(),
                PlayerMonitor(),
                ItemListener(),
                BlockListener()
        )

        registerPacketListeners(
                ExperienceOrbSpawn(this)
        )

        prepareDatabase()

        // reflectionを使うので先に生成
        Head.values().forEach { it.toItemStack() }

        // add new battle
        (1..RaidManager.maxBattle).forEach { RaidManager.newBattle() }


        logger.info("Gigantic is enabled!!")
    }

    private fun registerListeners(vararg listeners: Listener) = listeners.forEach { it.register() }

    private fun registerPacketListeners(vararg listeners: PacketListener) = listeners.forEach { PROTOCOL_MG.addPacketListener(it) }

    private fun prepareDatabase() {
        //connect MySQL
        Database.connect("jdbc:mysql://${DatabaseConfig.HOST}/${DatabaseConfig.DATABASE}",
                "com.mysql.jdbc.Driver", DatabaseConfig.USER, DatabaseConfig.PASSWORD)

        //create Tables
        transaction {
            // プレイヤー用のテーブルを作成
            SchemaUtils.createMissingTablesAndColumns(
                    UserTable,
                    UserWillTable,
                    UserMineBlockTable,
                    UserBossTable,
                    UserRelicTable,
                    UserLockedTable,
                    UserBeltTable
            )
        }
    }

    override fun onDisable() {
        Bukkit.getOnlinePlayers().filterNotNull().forEach { player ->
            if (player.gameMode == GameMode.SPECTATOR) {
                player.find(CatalogPlayerCache.AFK_LOCATION)?.let { it ->
                    player.teleport(it.getLocation())
                }
                player.gameMode = GameMode.SURVIVAL
            }
            RaidManager.getBattleList().firstOrNull { it.isJoined(player) }?.drop(player)
            PlayerCacheMemory.remove(player.uniqueId, false)
            player.kickPlayer("Restarting...Please wait a few minutes.")
        }
        server.scheduler.cancelTasks(this)
        logger.info("Gigantic is disabled!!")
    }

}