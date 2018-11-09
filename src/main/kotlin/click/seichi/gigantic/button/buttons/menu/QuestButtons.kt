package click.seichi.gigantic.button.buttons.menu

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.QuestMenuMessages
import click.seichi.gigantic.quest.QuestClient
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
                    setDisplayName("${ChatColor.LIGHT_PURPLE}" +
                            client.quest.localizedName.asSafety(player.wrappedLocale))
                    clearLore()
                    // 説明文
                    client.quest.localizedLore?.map { "${ChatColor.GRAY}" + it.asSafety(player.wrappedLocale) }
                            ?.let {
                                setLore(*it.toTypedArray())
                            }

                    if (client.quest.monsterList.isEmpty()) return@apply
                    // 倒すモンスターの詳細
                    addLore(
                            "${ChatColor.WHITE}" + MenuMessages.LINE,
                            "${ChatColor.WHITE}" + QuestMenuMessages.MONSTER_LIST.asSafety(player.wrappedLocale)
                    )
                    client.quest.monsterList.forEach { monster ->
                        addLore("${ChatColor.WHITE}" + monster.localizedName.asSafety(player.wrappedLocale))
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                val index = client.processedDegree - 1
                val monster = client.quest.monsterList.getOrNull(index) ?: return
                // TODO implements
                player.closeInventory()
            }

        }
    }

}