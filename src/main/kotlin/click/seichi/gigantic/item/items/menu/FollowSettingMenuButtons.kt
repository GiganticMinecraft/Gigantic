package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.RefineItem
import click.seichi.gigantic.menu.menus.FollowMenu
import click.seichi.gigantic.menu.menus.FollowPlayerMenu
import click.seichi.gigantic.menu.menus.FollowerMenu
import click.seichi.gigantic.menu.menus.PlayerListRefineMenu
import click.seichi.gigantic.message.messages.menu.FollowSettingMenuMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object FollowSettingMenuButtons {

    val FOLLOW = object : Button {
        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.LIGHT_GRAY_DYE).apply {
                setDisplayName("${ChatColor.GRAY}" +
                        "${ChatColor.BOLD}" +
                        FollowSettingMenuMessages.FOLLOW.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            FollowMenu.open(player)
            return true
        }
    }

    val FOLLOW_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                return to.getHead().apply {
                    setDisplayName("${ChatColor.WHITE}" +
                            "${ChatColor.BOLD}" +
                            to.name)
                    clearLore()
                    if (to.isFollow(player.uniqueId)) {
                        addLore("${ChatColor.LIGHT_PURPLE}" +
                                FollowSettingMenuMessages.FOLLOW_EXCHANGE.asSafety(player.wrappedLocale))
                    }
                    addLore("${ChatColor.GRAY}" +
                            FollowSettingMenuMessages.CLICK_TO_UNFOLLOW.asSafety(player.wrappedLocale))
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                player.unFollow(to.uniqueId)
                FollowMenu.reopen(player)
                return true
            }
        }
    }

    val FOLLOWER = object : Button {
        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.ORANGE_DYE).apply {
                setDisplayName("${ChatColor.GOLD}" +
                        "${ChatColor.BOLD}" +
                        FollowSettingMenuMessages.FOLLOWER.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            FollowerMenu.open(player)
            return true
        }
    }

    val FOLLOWER_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                return to.getHead().apply {
                    setDisplayName("${ChatColor.WHITE}" +
                            "${ChatColor.BOLD}" +
                            to.name)
                    clearLore()
                    when {
                        player.isFollow(to.uniqueId) -> addLore("${ChatColor.LIGHT_PURPLE}" +
                                FollowSettingMenuMessages.FOLLOW_EXCHANGE.asSafety(player.wrappedLocale))
                        player.follows < Config.PLAYER_MAX_FOLLOW -> addLore("${ChatColor.GREEN}" +
                                FollowSettingMenuMessages.CLICK_TO_FOLLOW.asSafety(player.wrappedLocale))
                        else -> addLore("${ChatColor.RED}" +
                                FollowSettingMenuMessages.MAX_FOLLOW.asSafety(player.wrappedLocale))
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                if (player.isFollow(to.uniqueId)) return true
                if (player.follows >= Config.PLAYER_MAX_FOLLOW) return true
                player.follow(to.uniqueId)
                FollowerMenu.reopen(player)
                return true
            }
        }
    }

    val FOLLOW_ONLINE = object : Button {
        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.PURPLE_DYE).apply {
                setDisplayName("${ChatColor.LIGHT_PURPLE}" +
                        "${ChatColor.BOLD}" +
                        FollowSettingMenuMessages.FOLLOW_ONLINE.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            FollowPlayerMenu.open(player)
            return true
        }
    }

    val FOLLOW_ONLINE_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                return to.getHead().apply {
                    setDisplayName("${ChatColor.WHITE}" +
                            "${ChatColor.BOLD}" +
                            to.name)
                    clearLore()
                    when {
                        to.isFollow(player.uniqueId) -> addLore("${ChatColor.GOLD}" +
                                FollowSettingMenuMessages.FOLLOWER_NOW.asSafety(player.wrappedLocale))
                        player.follows < Config.PLAYER_MAX_FOLLOW -> addLore("${ChatColor.GREEN}" +
                                FollowSettingMenuMessages.CLICK_TO_FOLLOW.asSafety(player.wrappedLocale))
                        else -> addLore("${ChatColor.RED}" +
                                FollowSettingMenuMessages.MAX_FOLLOW.asSafety(player.wrappedLocale))
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                if (player.follows >= Config.PLAYER_MAX_FOLLOW) return true
                player.follow(to.uniqueId)
                FollowPlayerMenu.reopen(player)
                return true
            }
        }
    }

    val REFINE = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.HOPPER).apply {
                setDisplayName("${ChatColor.AQUA}" +
                        FollowSettingMenuMessages.REFINE.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === PlayerListRefineMenu) return false
            PlayerListRefineMenu.open(player)
            return true
        }

    }

    val REFINE_ITEM: (RefineItem) -> Button = { item: RefineItem ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                val isRefine = player.getOrPut(Keys.REFINE_ITEM_MAP[item]!!)
                val itemStack = if (isRefine) ItemStack(Material.ENDER_EYE)
                else ItemStack(Material.BLACK_STAINED_GLASS_PANE)
                return itemStack.apply {
                    val color = if (isRefine) ChatColor.GREEN else ChatColor.RED
                    setDisplayName("$color" +
                            FollowSettingMenuMessages.ONLINE.asSafety(player.wrappedLocale))
                    setLore("${ChatColor.WHITE}${ChatColor.UNDERLINE}${ChatColor.BOLD}" +
                            FollowSettingMenuMessages.CLICK_TO_TOGGLE.asSafety(player.wrappedLocale))
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                player.transform(Keys.REFINE_ITEM_MAP[item]!!) { !it }
                PlayerSounds.TOGGLE.playOnly(player)
                PlayerListRefineMenu.reopen(player)
                return true
            }
        }
    }

}