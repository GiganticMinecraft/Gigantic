package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.battle.BattleManager
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.QuestSelectMenu
import click.seichi.gigantic.message.messages.menu.QuestMenuMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.quest.QuestClient
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object QuestButtons {

    val QUEST: (QuestClient) -> Button = { client ->
        object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                return ItemStack(Material.ENCHANTED_BOOK).apply {
                    if (!client.isProcessed) {
                        type = Material.BOOK
                    }
//                    val titleColor = if (client.isProcessed) ChatColor.LIGHT_PURPLE else
                    setDisplayName("${client.quest.category.color}" +
                            "${client.quest.category.getTitle(player.wrappedLocale)} " +
                            "${ChatColor.BOLD}" +
                            client.quest.getTitle(player.wrappedLocale))
                    clearLore()
                    // 説明文
                    client.quest.getLore(player.wrappedLocale)?.map { "${ChatColor.GRAY}" + it }
                            ?.let {
                                setLore(*it.toTypedArray())
                            }
                    if (client.quest.monsterList.isNotEmpty()) {
                        // 倒すモンスターの詳細
                        addLore("${ChatColor.YELLOW}${ChatColor.BOLD}" +
                                QuestMenuMessages.MONSTER_LIST.asSafety(player.wrappedLocale)
                        )
                        if (client.isProcessed)
                            addLore("${ChatColor.GRAY}" +
                                    QuestMenuMessages.MONSTER_REASON.asSafety(player.wrappedLocale))
                        client.quest.monsterList.forEachIndexed { index: Int, monster: SoulMonster ->
                            val color = if (client.processedDegree > index) ChatColor.DARK_RED else ChatColor.WHITE
                            addLore("$color - " + monster.getName(player.wrappedLocale))
                        }
                    }
                    addLore(" ")
                    if (client.isProcessed) {
                        addLore("${ChatColor.RED}${ChatColor.UNDERLINE}${ChatColor.BOLD}" +
                                QuestMenuMessages.PROCESS_ON.asSafety(player.wrappedLocale))
                    } else {
                        addLore("${ChatColor.GREEN}${ChatColor.UNDERLINE}${ChatColor.BOLD}" +
                                QuestMenuMessages.PROCESS_OFF.asSafety(player.wrappedLocale))
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                client.isProcessed = !client.isProcessed

                PlayerSounds.TOGGLE.playOnly(player)
                QuestSelectMenu.reopen(player)

                if (client.isProcessed) return

                BattleManager.findBattle(player)?.end()
            }

        }
    }

}