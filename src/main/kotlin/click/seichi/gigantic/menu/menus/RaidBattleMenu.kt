package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.addLore
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.sound.DetailedSound
import click.seichi.gigantic.sound.sounds.MenuSounds
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object RaidBattleMenu : Menu() {

    override val type: InventoryType
        get() = InventoryType.CHEST

    override val size: Int
        get() = 9

    override val openSound: DetailedSound
        get() = MenuSounds.BATTLE_MENU_OPEN

    override val closeSound: DetailedSound
        get() = MenuSounds.BATTLE_MENU_CLOSE

    override fun getTitle(player: Player): String {
        return MenuMessages.RAID_BOSS.asSafety(player.wrappedLocale)
    }

    init {
        (0..RaidManager.maxBattle.coerceAtMost(size)).forEach { slot ->
            registerButton(slot, object : Button {
                override fun getItemStack(player: Player): ItemStack? {
                    val battle = RaidManager
                            .getBattleList()
                            .getOrNull(slot) ?: return ItemStack(Material.AIR)
                    val boss = battle.boss
                    val bossName = boss.localizedName.asSafety(player.wrappedLocale)
                    return boss.head.toItemStack().apply {
                        setDisplayName(MenuMessages.BATTLE_BUTTON_TITLE(bossName).asSafety(player.wrappedLocale))
                        setLore(*MenuMessages.BATTLE_BUTTON_LORE(battle)
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                        addLore(MenuMessages.LINE)
                        addLore(
                                when {
                                    battle.droppedPlayerSet.contains(player.uniqueId) ->
                                        MenuMessages.BATTLE_BUTTON_DROPPED
                                    battle.joinedPlayerSet.contains(player.uniqueId) ->
                                        MenuMessages.BATTLE_BUTTON_LEFT
                                    else -> MenuMessages.BATTLE_BUTTON_JOIN
                                }.asSafety(player.wrappedLocale)
                        )
                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent) {
                    val battle = RaidManager
                            .getBattleList()
                            .getOrNull(slot) ?: return
                    when {
                        battle.droppedPlayerSet.contains(player.uniqueId) -> return
                        battle.joinedPlayerSet.contains(player.uniqueId) -> {
                            battle.left(player)
                        }
                        else -> {
                            battle.join(player)
                        }
                    }
                    reopen(player)
                }

            })
        }
    }

}