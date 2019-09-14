package click.seichi.gigantic.ranking

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.extension.combo
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.scheduler.BukkitRunnable


/**
 * @author Mr_IK
 */
class Combo30minRanking {

    init{
        object : BukkitRunnable() {
            override fun run() {
                rankingOpen()
            }
        }.runTaskTimer(Gigantic.PLUGIN, 36000L, 36000L)
    }

    fun rankingOpen(){
        var onePlayerName = "None"
        var twoPlayerName = "None"
        var threePlayerName = "None"
        var onePlayerCombo = 0L
        var twoPlayerCombo = 0L
        var threePlayerCombo = 0L

        // 計測用総回し
        Bukkit.getOnlinePlayers().forEach { player ->
            val playerName = player.name
            val playerCombo = player.combo


            when {
                // 1位
                onePlayerCombo < playerCombo -> {
                    threePlayerCombo = twoPlayerCombo
                    twoPlayerCombo = onePlayerCombo
                    onePlayerCombo = playerCombo

                    threePlayerName = twoPlayerName
                    twoPlayerName = onePlayerName
                    onePlayerName = playerName
                }
                onePlayerCombo == playerCombo -> onePlayerName += ", $playerName"

                // 2位
                twoPlayerCombo < playerCombo -> {
                    threePlayerCombo = twoPlayerCombo
                    twoPlayerCombo = playerCombo

                    threePlayerName = twoPlayerName
                    twoPlayerName = playerName
                }
                twoPlayerCombo == playerCombo -> twoPlayerName += ", $playerName"

                // 3位
                threePlayerCombo < playerCombo -> {
                    threePlayerCombo = playerCombo

                    threePlayerName = playerName
                }
                threePlayerCombo == playerCombo -> threePlayerName += ", $playerName"
            }
        }

        // 結果通知用
        Bukkit.getOnlinePlayers().forEach { player ->
            player.sendMessage(
                    "${ChatColor.YELLOW}${ChatColor.BOLD}☆☆☆☆☆☆☆☆" +
                            "${ChatColor.AQUA}${ChatColor.BOLD}定期コンボ量ランキング発表！" +
                            "${ChatColor.YELLOW}${ChatColor.BOLD}☆☆☆☆☆☆☆☆")
            player.sendMessage(
                    "${ChatColor.GOLD}${ChatColor.BOLD}♚第一位♚ " +
                            "${ChatColor.YELLOW}${ChatColor.BOLD}${onePlayerName}  " +
                            "${ChatColor.BLUE}${onePlayerCombo} combo")
            player.sendMessage(
                    "${ChatColor.GRAY}${ChatColor.BOLD}♕第二位♕ " +
                            "${ChatColor.WHITE}${ChatColor.BOLD}${twoPlayerName}  " +
                            "${ChatColor.BLUE}${twoPlayerCombo} combo")
            player.sendMessage(
                    "${ChatColor.DARK_AQUA}${ChatColor.BOLD}☆第三位☆ " +
                            "${ChatColor.AQUA}${ChatColor.BOLD}${threePlayerName}  " +
                            "${ChatColor.BLUE}${threePlayerCombo} combo")
        }
    }

}