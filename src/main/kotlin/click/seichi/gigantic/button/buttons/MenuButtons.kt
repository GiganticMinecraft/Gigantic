package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.menu.menus.*
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.relic.RelicRarity
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object MenuButtons {

    val PROFILE_PROFILE = object : Button {
        override fun getItemStack(player: Player): ItemStack? {
            return player.getHead().apply {
                setDisplayName(MenuMessages.PROFILE_PROFILE.asSafety(player.wrappedLocale))
                val level = player.find(CatalogPlayerCache.LEVEL) ?: return@apply
                val aptitude = player.find(CatalogPlayerCache.APTITUDE) ?: return@apply
                val health = player.find(CatalogPlayerCache.HEALTH) ?: return@apply
                setLore(
                        MenuMessages.PROFILE_LEVEL(level).asSafety(player.wrappedLocale),
                        MenuMessages.PROFILE_EXP(level).asSafety(player.wrappedLocale),
                        MenuMessages.PROFILE_HEALTH(health).asSafety(player.wrappedLocale),
                        *MenuMessages.PROFILE_WILL_APTITUDE(aptitude).map { it.asSafety(player.wrappedLocale) }.toTypedArray()
                )
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val PROFILE_RAID_BOSS = object : Button {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.ENDER_EYE).apply {
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
                    0L -> ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1)
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
                    amount == 0L -> ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1)
                    relic.rarity == RelicRarity.NORMAL -> Head.RUBY_JEWELLERY.toItemStack()
                    relic.rarity == RelicRarity.RARE -> Head.SAPPHIRE_JEWELLERY.toItemStack()
                    else -> ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1)
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
                val isJoinedOtherRaid = RaidManager
                        .getBattleList()
                        .firstOrNull { it.isJoined(player) } != null
                return boss.head.toItemStack().apply {
                    setDisplayName(MenuMessages.BATTLE_BUTTON_TITLE(boss).asSafety(player.wrappedLocale))
                    setLore(*MenuMessages.BATTLE_BUTTON_LORE(battle, player.find(CatalogPlayerCache.HEALTH)
                            ?: return@apply)
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

    val BELT_SWITCHER_SETTING: (Belt) -> Button = { belt ->
        object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                val switcher = player.find(CatalogPlayerCache.BELT_SWITCHER) ?: return null
                return belt.getFixedButton().getItemStack(player)?.apply {
                    setDisplayName(belt.localizedName.asSafety(player.wrappedLocale))
                    setLore(
                            *MenuMessages.BELT_SWITCHER_SETTING_BUTTON_LORE(switcher.canSwitch(belt))
                                    .map { it.asSafety(player.wrappedLocale) }
                                    .toTypedArray()
                    )
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                player.manipulate(CatalogPlayerCache.BELT_SWITCHER) {
                    it.setCanSwitch(belt, !it.canSwitch(belt))
                    if (!it.canSwitch(belt)) {
                        it.switch()
                        SkillSounds.SWITCH.playOnly(player)
                    }
                }
                player.find(Keys.BELT)?.wear(player)
                BeltSwitchSettingMenu.reopen(player)
            }

        }
    }

    val PROFILE_SKILL = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.BLAZE_POWDER).apply {
                setDisplayName(MenuMessages.PROFILE_SKILL.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === ProfileSkillMenu) return
            ProfileSkillMenu.open(player)
        }

    }

    val PROFILE_SKILL_MINE_BURST = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.BLAZE_POWDER).apply {
                setDisplayName(MenuMessages.MINE_BURST_TITLE.asSafety(player.wrappedLocale))
                setLore(*MenuMessages.MINE_BURST
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val PROFILE_SKILL_FLASH = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.FEATHER).apply {
                setDisplayName(MenuMessages.FLASH_TITLE.asSafety(player.wrappedLocale))
                setLore(*MenuMessages.FLASH
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val PROFILE_SKILL_HEAL = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.APPLE).apply {
                setDisplayName(MenuMessages.HEAL_TITLE.asSafety(player.wrappedLocale))
                setLore(*MenuMessages.HEAL
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val PROFILE_SKILL_SWITCH = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.LEVER).apply {
                setDisplayName(MenuMessages.SWITCH_TITLE.asSafety(player.wrappedLocale))
                setLore(*MenuMessages.SWITCH
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val PROFILE_SKILL_TERRA_DRAIN = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.WHEAT_SEEDS).apply {
                setDisplayName(MenuMessages.TERRA_DRAIN_TITLE.asSafety(player.wrappedLocale))
                setLore(*MenuMessages.TERRA_DRAIN
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val PROFILE_SKILL_WILL_O_THE_WISP = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.IRON_NUGGET).apply {
                setDisplayName(MenuMessages.WILL_O_THE_WISP_TITLE.asSafety(player.wrappedLocale))
                setLore(*MenuMessages.WILL_O_THE_WISP
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())
                setEnchanted(true)
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

}