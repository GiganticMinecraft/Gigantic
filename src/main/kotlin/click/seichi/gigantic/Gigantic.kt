package click.seichi.gigantic

import click.seichi.gigantic.config.DatabaseConfig
import click.seichi.gigantic.database.cache.PlayerCacheMemory
import click.seichi.gigantic.database.table.*
import click.seichi.gigantic.extension.register
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.listener.*
import click.seichi.gigantic.listener.packet.ExperienceOrbSpawn
import click.seichi.gigantic.player.defalutInventory.inventories.MainInventory
import click.seichi.gigantic.raid.RaidManager
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketListener
import org.bukkit.Bukkit
import org.bukkit.GameMode
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
    }

    override fun onEnable() {
        PLUGIN = this
        PROTOCOL_MG = ProtocolLibrary.getProtocolManager()

        // Remove all armor stands
        server.worlds.forEach { it.getEntitiesByClass(ArmorStand::class.java).forEach { it.remove() } }

        registerListeners(
                MenuListener(),
                PlayerListener(),
                SpiritListener(),
                PlayerMonitor(),
                ItemListener()
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
                    UserRelicTable
            )
        }
    }

    override fun onDisable() {
        Bukkit.getOnlinePlayers().filterNotNull().forEach { player ->
            if (player.gameMode == GameMode.SPECTATOR) {
                player.teleport(MainInventory.lastLocationMap.remove(player.uniqueId))
                player.gameMode = GameMode.SURVIVAL
            }
            PlayerCacheMemory.stopServerWith(player.uniqueId)
            player.kickPlayer("Thank you for playing!!")
        }
        server.scheduler.cancelTasks(this)
        logger.info("Gigantic is disabled!!")
    }

}