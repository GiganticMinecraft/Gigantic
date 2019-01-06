package click.seichi.gigantic

import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.command.*
import click.seichi.gigantic.config.*
import click.seichi.gigantic.database.table.*
import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.extension.bind
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.register
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.listener.*
import click.seichi.gigantic.listener.packet.ExperienceOrbSpawn
import click.seichi.gigantic.spirit.SpiritManager
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketListener
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.ArmorStand
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import kotlin.properties.Delegates

/**
 * @author tar0ss
 * @unicroak
 */
class Gigantic : JavaPlugin() {

    companion object {
        lateinit var PLUGIN: Gigantic
            private set
        // protocolLib manager
        lateinit var PROTOCOL_MG: ProtocolManager
            private set
        var IS_DEBUG: Boolean by Delegates.notNull()
            private set
        var IS_LIVE = false

        val DEFAULT_LOCALE = Locale.JAPANESE!!

        val BROKEN_BLOCK_SET = mutableSetOf<Block>()

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
                Material.GRASS_PATH,
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
                Material.COBBLESTONE
        )

        val LOGS = setOf(
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
                Material.BROWN_MUSHROOM_BLOCK
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

        val GRASSES = setOf(
                Material.GRASS_BLOCK,
                Material.GRASS_PATH,
                Material.GRASS,
                Material.TALL_GRASS,
                Material.TALL_SEAGRASS,
                Material.FERN,
                Material.SEA_PICKLE,
                Material.DANDELION,
                Material.POPPY,
                Material.BLUE_ORCHID,
                Material.ALLIUM,
                Material.AZURE_BLUET,
                Material.RED_TULIP,
                Material.ORANGE_TULIP,
                Material.WHITE_TULIP,
                Material.PINK_TULIP,
                Material.OXEYE_DAISY,
                Material.BROWN_MUSHROOM_BLOCK,
                Material.RED_MUSHROOM_BLOCK,
                Material.BROWN_MUSHROOM,
                Material.RED_MUSHROOM,
                Material.MUSHROOM_STEM,
                Material.CACTUS,
                Material.SUNFLOWER,
                Material.LILAC,
                Material.ROSE_BUSH,
                Material.PEONY,
                Material.LARGE_FERN
        )

        val WATER_PLANTS = setOf(
                Material.SEAGRASS,
                Material.SEA_PICKLE,
                Material.TALL_SEAGRASS,
                Material.KELP_PLANT,
                Material.KELP,
                Material.BRAIN_CORAL,
                Material.BUBBLE_CORAL,
                Material.FIRE_CORAL,
                Material.HORN_CORAL,
                Material.TUBE_CORAL,
                Material.BRAIN_CORAL_FAN,
                Material.BUBBLE_CORAL_FAN,
                Material.FIRE_CORAL_FAN,
                Material.HORN_CORAL_FAN,
                Material.TUBE_CORAL_FAN
        )

        val TREES = setOf(*LOGS.toTypedArray(), *LEAVES.toTypedArray())
    }

    override fun onEnable() {
        PLUGIN = this
        PROTOCOL_MG = ProtocolLibrary.getProtocolManager()

        server.worlds.forEach { world ->
            // Remove all armor stands
            world.getEntitiesByClass(ArmorStand::class.java).forEach { it.remove() }
        }

        loadConfiguration(
                Config,
                PlayerLevelConfig,
                DatabaseConfig,
                DebugConfig,
                ManaConfig
        )

        IS_DEBUG = Config.DEBUG_MODE

        registerListeners(
                MenuListener(),
                PlayerListener(),
                SpiritListener(),
                PlayerMonitor(),
                ItemListener(),
                BlockListener(),
                WorldListener(),
                EntityListener(),
                ChunkListener(),
                BattleListener(),
                ElytraListener()
        )

        registerPacketListeners(
                ExperienceOrbSpawn()
        )

        bindCommands(
                "vote" to VoteCommand(),
                "donate" to DonateCommand(),
                "tell" to TellCommand(),
                "reply" to ReplyCommand(),
                "live" to LiveCommand()
        )

        prepareDatabase(
                UserTable,
                UserWillTable,
                UserExpTable,
                UserMonsterTable,
                UserRelicTable,
                UserAchievementTable,
                UserToolTable,
                UserBeltTable,
                UserQuestTable,
                UserEffectTable,
                DonateHistoryTable,
                UserFollowTable
        )

        // reflectionを使うので先に生成
        Head.values().forEach { it.toItemStack() }

        SpiritManager.onEnabled()

        // 3秒後にTickEventを毎tick発火
        object : BukkitRunnable() {
            override fun run() {
                Bukkit.getServer().pluginManager.callEvent(TickEvent())
            }
        }.runTaskTimer(this, 3 * 20L, 1)

        logger.info("Gigantic is enabled!!")
    }


    override fun onDisable() {

        SpiritManager.getSpiritSet().forEach { it.remove() }

        server.scheduler.cancelTasks(this)

        //全ての破壊済ブロックを確認し，破壊されていなければ消す
        BROKEN_BLOCK_SET.filter { !it.isEmpty }.also {
            logger.info("破壊されているはずのブロックが${it.size}個 破壊されていなかったため，削除しました．")
        }.forEach {
            it.type = Material.AIR
        }

        Bukkit.getOnlinePlayers().filterNotNull().forEach { player ->
            if (player.gameMode == GameMode.SPECTATOR) {
                player.getOrPut(Keys.AFK_LOCATION)?.let { it ->
                    player.teleport(it)
                }
                player.gameMode = GameMode.SURVIVAL
            }
            try {
                PlayerCacheMemory.writeThenRemoved(player.uniqueId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            player.kickPlayer("Restarting...Please wait a few seconds.")
        }

        logger.info("Gigantic is disabled!!")
        Bukkit.getPluginManager().disablePlugin(this)
    }

    private fun loadConfiguration(vararg configurations: SimpleConfiguration) = configurations.forEach { it.init(this) }

    private fun registerListeners(vararg listeners: Listener) = listeners.forEach { it.register() }

    private fun registerPacketListeners(vararg listeners: PacketListener) = listeners.forEach { PROTOCOL_MG.addPacketListener(it) }

    private fun bindCommands(vararg commands: Pair<String, CommandExecutor>) = commands.toMap().forEach { id, executor -> executor.bind(id) }

    private fun prepareDatabase(vararg tables: Table) {
        //connect MySQL
        Database.connect("jdbc:mysql://${DatabaseConfig.HOST}/${DatabaseConfig.DATABASE}",
                "com.mysql.jdbc.Driver", DatabaseConfig.USER, DatabaseConfig.PASSWORD)

        // create Tables
        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                    *tables
            )
        }
    }

}