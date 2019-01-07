package click.seichi.gigantic

import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.command.*
import click.seichi.gigantic.config.*
import click.seichi.gigantic.database.table.*
import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.extension.bind
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.isAir
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
                ElytraListener(),
                ToolListener(),
                WillListener(),
                AchievementListener()
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
        BROKEN_BLOCK_SET.filter { !it.isAir }.also {
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