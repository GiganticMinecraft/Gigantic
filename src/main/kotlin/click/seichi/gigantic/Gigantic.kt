package click.seichi.gigantic

import click.seichi.gigantic.config.DatabaseConfig
import click.seichi.gigantic.database.table.UserMineBlockTable
import click.seichi.gigantic.database.table.UserTable
import click.seichi.gigantic.database.table.UserWillTable
import click.seichi.gigantic.listener.MenuListener
import click.seichi.gigantic.listener.PlayerListener
import click.seichi.gigantic.listener.PlayerMonitor
import click.seichi.gigantic.listener.SpiritListener
import kotlinx.coroutines.experimental.newSingleThreadContext
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * @author tar0ss
 */
class Gigantic : JavaPlugin() {

    companion object {
        lateinit var PLUGIN: Gigantic
        // Database thread
        val DB = newSingleThreadContext("DB")
    }

    override fun onEnable() {
        PLUGIN = this

        registerListener()

        prepareDatabase()

        logger.info("Gigantic is enabled!!")
    }

    private fun registerListener() {
        server.pluginManager.run {
            registerEvents(MenuListener(), this@Gigantic)
            registerEvents(PlayerListener(), this@Gigantic)
            registerEvents(SpiritListener(), this@Gigantic)
            registerEvents(PlayerMonitor(), this@Gigantic)
        }
    }

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