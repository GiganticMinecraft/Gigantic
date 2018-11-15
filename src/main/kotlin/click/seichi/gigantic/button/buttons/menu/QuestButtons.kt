package click.seichi.gigantic.button.buttons.menu

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.menus.QuestSelectMenu
import click.seichi.gigantic.message.messages.MenuMessages
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
                    val titleColor = if (client.isProcessed) ChatColor.LIGHT_PURPLE else ChatColor.DARK_GRAY
                    setDisplayName("$titleColor${ChatColor.BOLD}" +
                            client.quest.getTitle(player.wrappedLocale))
                    clearLore()
                    // 説明文
                    client.quest.getLore(player.wrappedLocale)?.map { "${ChatColor.GRAY}" + it }
                            ?.let {
                                setLore(*it.toTypedArray())
                            }
                    if (client.isProcessed) {
                        addLore("${ChatColor.RED}${ChatColor.UNDERLINE}" +
                                QuestMenuMessages.PROCESS_ON.asSafety(player.wrappedLocale))
                    } else {
                        addLore("${ChatColor.GREEN}${ChatColor.UNDERLINE}" +
                                QuestMenuMessages.PROCESS_OFF.asSafety(player.wrappedLocale))
                    }
                    if (client.quest.monsterList.isEmpty()) return@apply
                    // 倒すモンスターの詳細
                    addLore(
                            "${ChatColor.WHITE}" + MenuMessages.LINE,
                            "${ChatColor.YELLOW}" + QuestMenuMessages.MONSTER_LIST.asSafety(player.wrappedLocale)
                    )
                    client.quest.monsterList.forEachIndexed { index: Int, monster: SoulMonster ->
                        val color = if (client.processedDegree > index) ChatColor.DARK_RED else ChatColor.WHITE
                        addLore("$color" + monster.getName(player.wrappedLocale))
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                client.isProcessed = !client.isProcessed
                PlayerSounds.TOGGLE.playOnly(player)
                QuestSelectMenu.reopen(player)
            }

        }
    }

}