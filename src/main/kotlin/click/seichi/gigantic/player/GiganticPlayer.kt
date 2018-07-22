package click.seichi.gigantic.player

import click.seichi.gigantic.player.belt.Belt
import click.seichi.gigantic.player.components.*
import click.seichi.gigantic.player.defalutInventory.DefaultInventory
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import java.util.*

interface GiganticPlayer {
    val player: Player
    val uniqueId: UUID
    var locale: Locale
    val mana: Mana
    val mineBlock: MineBlock
    val level: Level
    val memory: Memory
    val aptitude: WillAptitude
    val defaultInventory: DefaultInventory
    var belt: Belt
    val mineCombo: MineCombo
    val mineBurst: MineBurst
    val raidData: RaidData

    val manaBar: BossBar

    fun switchBelt()
}
