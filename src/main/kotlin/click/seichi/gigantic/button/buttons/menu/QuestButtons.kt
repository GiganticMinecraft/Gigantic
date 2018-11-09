package click.seichi.gigantic.button.buttons.menu

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.QuestMenuMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.quest.QuestData
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object QuestButtons {

    val QUEST: (QuestData) -> Button = { questData ->
        object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                return ItemStack(Material.ENCHANTED_BOOK).apply {
                    setDisplayName("${ChatColor.LIGHT_PURPLE}" +
                            questData.quest.localizedName.asSafety(player.wrappedLocale))
                    clearLore()
                    // 説明文
                    questData.quest.localizedLore?.map { "${ChatColor.GRAY}" + it.asSafety(player.wrappedLocale) }
                            ?.let {
                                setLore(*it.toTypedArray())
                            }

                    if (questData.quest.monsterList.isEmpty()) return@apply
                    // 倒すモンスターの詳細
                    addLore(
                            "${ChatColor.WHITE}" + MenuMessages.LINE,
                            "${ChatColor.WHITE}" + QuestMenuMessages.MONSTER_LIST.asSafety(player.wrappedLocale)
                    )
                    questData.quest.monsterList.forEach { monster: SoulMonster ->
                        addLore("${ChatColor.WHITE}" + monster.getName(player))
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                val index = questData.processedDegree - 1
                val monster = questData.quest.monsterList.getOrNull(index) ?: return
                // TODO implements
                player.closeInventory()
            }

        }
    }

}