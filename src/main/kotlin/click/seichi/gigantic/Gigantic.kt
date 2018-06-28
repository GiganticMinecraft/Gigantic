package click.seichi.gigantic

import click.seichi.gigantic.config.DatabaseSetting
import click.seichi.gigantic.database.table.UserTable
import click.seichi.gigantic.listener.InventoryListener
import click.seichi.gigantic.listener.PlayerListener
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
            registerEvents(InventoryListener(), this@Gigantic)
            registerEvents(PlayerListener(), this@Gigantic)
        }
    }

    private fun prepareDatabase() {
        //connect MySQL
        Database.connect("jdbc:mysql://${DatabaseSetting.host}/${DatabaseSetting.database}",
                "com.mysql.jdbc.Driver", DatabaseSetting.user, DatabaseSetting.password)

        //create Tables
        transaction {
            // プレイヤー用のテーブルを作成
            SchemaUtils.createMissingTablesAndColumns(UserTable)
        }
    }

    override fun onDisable() {
        server.scheduler.cancelTasks(this)
        logger.info("Gigantic is disabled!!")
    }

}