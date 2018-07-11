package click.seichi.gigantic.player

import click.seichi.gigantic.database.PlayerDao

interface RemotablePlayer {
    fun load(playerDao: PlayerDao)
    fun init()
    fun finish()
    fun save(playerDao: PlayerDao)
}
