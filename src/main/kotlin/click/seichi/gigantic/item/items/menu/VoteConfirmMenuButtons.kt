package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.event.events.SenseEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.message.messages.menu.VoteConfirmMenuMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.sound.sounds.WillSpiritSounds
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author Mr_IK
 */
object VoteConfirmMenuButtons {

    val OK = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.LIME_STAINED_GLASS_PANE) {
                setDisplayName("${ChatColor.GREEN}" +
                        "${ChatColor.BOLD}" +
                        VoteConfirmMenuMessages.CLICK_TO_OK.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            // BagButtons.ktよりコピペ
            // 動作チェック中

            val givenBonus = player.getOrPut(Keys.GIVEN_VOTE_BONUS)
            val voteNum = player.totalVote
            val bonus = voteNum.minus(givenBonus)
            // ボーナスが無ければ終了
            if (bonus <= 0){
                player.sendMessage("${ChatColor.RED}"+
                        VoteConfirmMenuMessages.NOTHING_RESULT.asSafety(player.wrappedLocale))
                player.closeInventory()
                return false
            }

            player.offer(Keys.GIVEN_VOTE_BONUS, givenBonus + 1)

            val givenWillSet = mutableSetOf<Will>()
            // 特典付与処理
            // フリー特典は自動付与なので付ける必要なし．
            // 通常意志特典
            if (Achievement.FIRST_WILL.isGranted(player)) {
                val willSet = Will.values()
                        .filter { it.grade == WillGrade.BASIC }
                        .filter { player.hasAptitude(it) }
                        .shuffled(Random.generator)
                        .take(Defaults.VOTE_BONUS_BASIC_WILL_NUM)
                        .toSet()
                givenWillSet.addAll(willSet)
                willSet.forEach {
                    it.addEthel(player, Defaults.VOTE_BONUS_ETHEL)
                    Bukkit.getPluginManager().callEvent(SenseEvent(it, player, Defaults.VOTE_BONUS_ETHEL))
                }
            }
            // 高度意志特典
            if (Achievement.FIRST_ADVANCED_WILL.isGranted(player)) {
                val willSet = Will.values()
                        .filter { it.grade == WillGrade.ADVANCED }
                        .filter { player.hasAptitude(it) }
                        .shuffled(Random.generator)
                        .take(Defaults.VOTE_BONUS_ADVANCED_WILL_NUM)
                        .toSet()
                givenWillSet.addAll(willSet)
                willSet.forEach {
                    it.addEthel(player, Defaults.VOTE_BONUS_ETHEL)
                    Bukkit.getPluginManager().callEvent(SenseEvent(it, player, Defaults.VOTE_BONUS_ETHEL))
                }
            }
            player.offer(Keys.GIVEN_WILL_SET, givenWillSet)
            object : BukkitRunnable() {
                override fun run() {
                    if (!player.isValid) return
                    player.offer(Keys.GIVEN_WILL_SET, null)
                }
            }.runTaskLater(Gigantic.PLUGIN, 1L)
            player.updateBag()
            player.sendMessage("${ChatColor.GREEN}"+
                    VoteConfirmMenuMessages.GET_RESULT.asSafety(player.wrappedLocale))
            WillSpiritSounds.SENSED.playOnly(player)
            player.closeInventory()
            return true
        }
    }

    val NG = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.RED_STAINED_GLASS_PANE) {
                setDisplayName("${ChatColor.RED}" +
                        "${ChatColor.BOLD}" +
                        VoteConfirmMenuMessages.CLICK_TO_NG.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            // BagButtons.ktよりコピペ
            // 動作チェック中

            // 必要そうなら
            player.closeInventory()
            return true
        }
    }
}