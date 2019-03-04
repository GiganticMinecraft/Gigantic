package click.seichi.gigantic

import click.seichi.gigantic.cache.PlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.command.*
import click.seichi.gigantic.config.*
import click.seichi.gigantic.database.table.DonateHistoryTable
import click.seichi.gigantic.database.table.rank.RankValueTable
import click.seichi.gigantic.database.table.user.*
import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.listener.*
import click.seichi.gigantic.listener.packet.ExperienceOrbSpawn
import click.seichi.gigantic.player.spell.spells.SkyWalk
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
        /**
         * ONにするとすべてのプレイヤーが近くのブロックを掘れるようになる
         *
         * コマンドでOn/Off可能
         * 主に放送中に使用
         *
         */
        var IS_LIVE = false

        val DEFAULT_LOCALE = Locale.JAPANESE!!

        val NORMAL_RESOURCE_PACK_URL = "http://map.spring.seichi.click/resourcepacks/Spring_Texture_ver1.2.zip"

        val LIGHT_RESOURCE_PACK_URL = "http://map.spring.seichi.click/resourcepacks/Spring_Texture_No_Particle_ver1.2.zip"

        val SKILLED_BLOCK_SET = mutableSetOf<Block>()

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

        Bukkit.getServer().messenger.registerOutgoingPluginChannel(this, "BungeeCord")
        Bukkit.getServer().messenger.registerIncomingPluginChannel(this, "BungeeCord", GiganticMessageListener())

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
                AchievementListener(),
                SpellListener(),
                ChatListener(),
                TipsListener(),
                NightVisionListener()
        )

        registerPacketListeners(
                ExperienceOrbSpawn()
        )

        bindCommands(
                // 本番環境で実装されているとプレイヤーに最悪乗っ取られた場合に対処できないので除外
//                "vote" to VoteCommand(),
//                "donate" to DonateCommand(),
                "tell" to TellCommand(),
                "reply" to ReplyCommand(),
                "live" to LiveCommand(),
                "home" to HomeCommand(),
                "now" to NowCommand()
        )

        prepareDatabase(
                // user
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
                UserFollowTable,
                UserHomeTable,
                UserMuteTable,
                UserToggleTable,
                //rank
                RankValueTable
        )

        // reflectionを使うので先に生成
        Head.values().forEach { it.toItemStack() }

        SpiritManager.onEnabled()

        // 3秒後にTickEventを毎tick発火
        object : BukkitRunnable() {
            var ticks = 0L
            override fun run() {
                Bukkit.getServer().pluginManager.callEvent(TickEvent(ticks++))
            }
        }.runTaskTimer(this, 3 * 20L, 1)

        logger.info("Gigantic is enabled")
    }


    override fun onDisable() {

        SpiritManager.getSpiritSet().forEach { it.remove() }

        Bukkit.getOnlinePlayers().filterNotNull().forEach { player ->
            if (!PlayerCacheMemory.contains(player.uniqueId)) return@forEach

            if (player.gameMode == GameMode.SPECTATOR) {
                player.getOrPut(Keys.AFK_LOCATION)?.let {
                    player.teleportSafely(it)
                }
                player.gameMode = GameMode.SURVIVAL
            }

            player.getOrPut(Keys.SPELL_SKY_WALK_PLACE_BLOCKS).apply {
                forEach { block ->
                    SkyWalk.revert(block)
                }
                Gigantic.SKILLED_BLOCK_SET.removeAll(this)
            }

            try {
                PlayerCacheMemory.writeThenRemoved(player.uniqueId)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            server.scheduler.cancelTasks(this)
            player.kickPlayer("Restarting...Please wait a few seconds.")
        }

        //全ての破壊済ブロックを確認し，破壊されていなければ消す
        SKILLED_BLOCK_SET.filter { !it.isAir }.also {
            logger.info("破壊されているはずのブロックが${it.size}個 破壊されていなかったため，削除しました．")
        }.forEach {
            it.type = Material.AIR
        }

        logger.info("Gigantic is disabled")
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