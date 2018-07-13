package click.seichi.gigantic

import click.seichi.gigantic.config.DatabaseConfig
import click.seichi.gigantic.database.table.UserMineBlockTable
import click.seichi.gigantic.database.table.UserTable
import click.seichi.gigantic.database.table.UserWillTable
import click.seichi.gigantic.extension.register
import click.seichi.gigantic.listener.*
import click.seichi.gigantic.listener.packet.ExperienceOrbSpawn
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketListener
import kotlinx.coroutines.experimental.newSingleThreadContext
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * @author tar0ss
 * @unicroak
 */
class Gigantic : JavaPlugin() {

    companion object {
        lateinit var PLUGIN: Gigantic
        // Database thread
        val DB = newSingleThreadContext("DB")
        // protocolLib manager
        lateinit var PROTOCOL_MG: ProtocolManager
    }

    override fun onEnable() {
        PLUGIN = this
        PROTOCOL_MG = ProtocolLibrary.getProtocolManager()

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
            SchemaUtils.createMissingTablesAndColumns(UserTable, UserWillTable, UserMineBlockTable)
        }
    }

    override fun onDisable() {
        server.scheduler.cancelTasks(this)
        logger.info("Gigantic is disabled!!")
    }

}