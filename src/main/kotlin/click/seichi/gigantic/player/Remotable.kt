package click.seichi.gigantic.player

import click.seichi.gigantic.database.PlayerDao

interface Remotable {
    fun load(playerDao: PlayerDao)
    fun init()
    fun finish()
    fun save(playerDao: PlayerDao)
}
