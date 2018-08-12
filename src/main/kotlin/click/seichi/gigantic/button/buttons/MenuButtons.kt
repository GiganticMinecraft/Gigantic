package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.menu.menus.ProfileBossMenu
import click.seichi.gigantic.menu.menus.ProfileRelicMenu
import click.seichi.gigantic.menu.menus.RaidBattleMenu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.relic.RelicRarity
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object MenuButtons {

    val PROFILE = object : Button {
        override fun getItemStack(player: Player): ItemStack? {
            return player.getHead().apply {
                setDisplayName(MenuMessages.PROFILE.asSafety(player.wrappedLocale))
                val level = player.find(CatalogPlayerCache.LEVEL) ?: return@apply
                val aptitude = player.find(CatalogPlayerCache.APTITUDE) ?: return@apply
                setLore(
                        MenuMessages.PROFILE_LEVEL(level).asSafety(player.wrappedLocale),
                        MenuMessages.PROFILE_EXP(level).asSafety(player.wrappedLocale),
                        *MenuMessages.PROFILE_WILL_APTITUDE(aptitude).map { it.asSafety(player.wrappedLocale) }.toTypedArray()
                )
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val PROFILE_RAID_BOSS = object : Button {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.EYE_OF_ENDER).apply {
                setDisplayName(MenuMessages.PROFILE_RAID_BOSS.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === ProfileBossMenu) return
            ProfileBossMenu.open(player)
        }

    }

    val PROFILE_RELIC = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return Head.JEWELLERY_BOX.toItemStack().apply {
                setDisplayName(MenuMessages.PROFILE_RAID_RELIC.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === ProfileRelicMenu) return
            ProfileRelicMenu.open(player)
        }
    }

    val PROFILE_RAID_BOSS_INFO: (Boss, Long) -> Button = { boss: Boss, defeatCount: Long ->
        object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                val color = when (defeatCount) {
                    0L -> ChatColor.DARK_GRAY
                    in 1..29 -> ChatColor.WHITE
                    in 30..99 -> ChatColor.YELLOW
                    else -> ChatColor.LIGHT_PURPLE
                }
                return when (defeatCount) {
                    0L -> ItemStack(Material.STAINED_GLASS_PANE, 1, 15)
                    else -> boss.head.toItemStack()
                }.apply {
                    setDisplayName("$color${boss.localizedName.asSafety(player.wrappedLocale)}")
                    setLore("$color${MenuMessages.PROFILE_RAID_BOSS_DEFEATED(defeatCount).asSafety(player.wrappedLocale)}")
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
            }

        }
    }

    val PROFILE_RAID_RELIC_INFO: (Relic, Long) -> Button = { relic: Relic, amount: Long ->
        object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                val color = when (amount) {
                    0L -> ChatColor.DARK_GRAY
                    in 1..29 -> ChatColor.WHITE
                    in 30..99 -> ChatColor.YELLOW
                    else -> ChatColor.LIGHT_PURPLE
                }
                return when {
                    amount == 0L -> ItemStack(Material.STAINED_GLASS_PANE, 1, 15)
                    relic.rarity == RelicRarity.NORMAL -> Head.RUBY_JEWELLERY.toItemStack()
                    relic.rarity == RelicRarity.RARE -> Head.SAPPHIRE_JEWELLERY.toItemStack()
                    else -> ItemStack(Material.STAINED_GLASS_PANE, 1, 15)
                }.apply {
                    setDisplayName("$color${relic.localizedName.asSafety(player.wrappedLocale)}")
                    setLore(*relic.description.map { "$color${it.asSafety(player.wrappedLocale)}" }.toTypedArray(),
                            "$color${MenuMessages.PROFILE_RAID_RELIC_AMOUNT(amount).asSafety(player.wrappedLocale)}")
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
            }

        }
    }

    val RAID_BATTLE_BOSS: (Int) -> Button = { slot: Int ->
        object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                val battle = RaidManager
                        .getBattleList()
                        .getOrNull(slot) ?: return ItemStack(Material.AIR)
                val boss = battle.boss
                val bossName = boss.localizedName.asSafety(player.wrappedLocale)
                val isJoinedOtherRaid = RaidManager
                        .getBattleList()
                        .firstOrNull { it.isJoined(player) } != null
                return boss.head.toItemStack().apply {
                    setDisplayName(MenuMessages.BATTLE_BUTTON_TITLE(bossName).asSafety(player.wrappedLocale))
                    setLore(*MenuMessages.BATTLE_BUTTON_LORE(battle)
                            .map { it.asSafety(player.wrappedLocale) }
                            .toTypedArray())
                    addLore(MenuMessages.LINE)
                    addLore(
                            when {
                                battle.isDropped(player) ->
                                    MenuMessages.BATTLE_BUTTON_DROPPED
                                battle.isJoined(player) ->
                                    MenuMessages.BATTLE_BUTTON_LEFT
                                isJoinedOtherRaid ->
                                    MenuMessages.BATTLE_BUTTON_JOINED
                                else -> MenuMessages.BATTLE_BUTTON_JOIN
                            }.asSafety(player.wrappedLocale)
                    )
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                val battle = RaidManager
                        .getBattleList()
                        .getOrNull(slot) ?: return
                val isJoinedOtherRaid = RaidManager
                        .getBattleList()
                        .firstOrNull { it.isJoined(player) } != null
                when {
                    battle.isDropped(player) -> return
                    battle.isJoined(player) -> {
                        battle.left(player)
                    }
                    isJoinedOtherRaid -> return
                    else -> {
                        battle.join(player)
                    }
                }
                RaidBattleMenu.reopen(player)
            }

        }
    }

}