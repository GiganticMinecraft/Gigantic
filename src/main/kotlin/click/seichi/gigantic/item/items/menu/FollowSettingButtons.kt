package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.follow.*
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
object FollowSettingButtons {

    val FOLLOW = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.LIGHT_GRAY_DYE) {
                setDisplayName("${ChatColor.GRAY}" +
                        "${ChatColor.BOLD}" +
                        FollowSettingMenuMessages.FOLLOW.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            FollowMenu.open(player)
            return true
        }
    }

    val MUTE = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.GRAY_DYE) {
                setDisplayName("${ChatColor.GRAY}" +
                        "${ChatColor.BOLD}" +
                        FollowSettingMenuMessages.MUTE.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            MuteMenu.open(player)
            return true
        }
    }

    val FOLLOW_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
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

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                player.unFollow(to.uniqueId)
                FollowMenu.reopen(player)
                PlayerSounds.TOGGLE.playOnly(player)
                return true
            }
        }
    }

    val MUTE_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                return to.getHead().apply {
                    setDisplayName("${ChatColor.WHITE}" +
                            "${ChatColor.BOLD}" +
                            to.name)
                    clearLore()
                    addLore("${ChatColor.GRAY}" +
                            FollowSettingMenuMessages.CLICK_TO_UNMUTE.asSafety(player.wrappedLocale))
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                player.unMute(to.uniqueId)
                MuteMenu.reopen(player)
                PlayerSounds.TOGGLE.playOnly(player)
                return true
            }
        }
    }

    val FOLLOWER = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.ORANGE_DYE) {
                setDisplayName("${ChatColor.GOLD}" +
                        "${ChatColor.BOLD}" +
                        FollowSettingMenuMessages.FOLLOWER.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            FollowerMenu.open(player)
            return true
        }
    }

    val FOLLOWER_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                return to.getHead().apply {
                    setDisplayName("${ChatColor.WHITE}" +
                            "${ChatColor.BOLD}" +
                            to.name)
                    clearLore()
                    when {
                        player.isFollow(to.uniqueId) -> addLore("${ChatColor.LIGHT_PURPLE}" +
                                FollowSettingMenuMessages.FOLLOW_EXCHANGE.asSafety(player.wrappedLocale))
                        player.follows < Config.PLAYER_MAX_FOLLOW -> addLore("${ChatColor.GREEN}" +
                                FollowSettingMenuMessages.CLICK_TO_EXCHANGE.asSafety(player.wrappedLocale))
                        else -> addLore("${ChatColor.RED}" +
                                FollowSettingMenuMessages.MAX_FOLLOW.asSafety(player.wrappedLocale))
                    }
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                if (player.isFollow(to.uniqueId)) return true
                if (player.follows >= Config.PLAYER_MAX_FOLLOW) return true
                player.follow(to.uniqueId)
                FollowerMenu.reopen(player)
                PlayerSounds.TOGGLE.playOnly(player)
                return true
            }
        }
    }

    val FOLLOW_ONLINE = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.PURPLE_DYE) {
                setDisplayName("${ChatColor.LIGHT_PURPLE}" +
                        "${ChatColor.BOLD}" +
                        FollowSettingMenuMessages.FOLLOW_ONLINE.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            FollowPlayerMenu.open(player)
            return true
        }
    }

    val MUTE_ONLINE = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.PINK_DYE) {
                setDisplayName("${ChatColor.LIGHT_PURPLE}" +
                        "${ChatColor.BOLD}" +
                        FollowSettingMenuMessages.MUTE_ONLINE.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            MutePlayerMenu.open(player)
            return true
        }
    }

    val FOLLOW_ONLINE_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                return to.getHead().apply {
                    setDisplayName("${ChatColor.WHITE}" +
                            "${ChatColor.BOLD}" +
                            to.name)
                    clearLore()
                    if (to.isFollow(player.uniqueId)) {
                        addLore("${ChatColor.GOLD}" +
                                FollowSettingMenuMessages.FOLLOWER_NOW.asSafety(player.wrappedLocale))
                    }
                    when {
                        player.follows < Config.PLAYER_MAX_FOLLOW -> addLore("${ChatColor.GREEN}" +
                                FollowSettingMenuMessages.CLICK_TO_FOLLOW.asSafety(player.wrappedLocale))
                        else -> addLore("${ChatColor.RED}" +
                                FollowSettingMenuMessages.MAX_FOLLOW.asSafety(player.wrappedLocale))
                    }
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                if (player.follows >= Config.PLAYER_MAX_FOLLOW) return true
                player.follow(to.uniqueId)
                FollowPlayerMenu.reopen(player)
                PlayerSounds.TOGGLE.playOnly(player)
                return true
            }
        }
    }

    val MUTE_ONLINE_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                return to.getHead().apply {
                    setDisplayName("${ChatColor.WHITE}" +
                            "${ChatColor.BOLD}" +
                            to.name)
                    clearLore()
                    when {
                        player.mutes < Config.PLAYER_MAX_MUTE -> addLore("${ChatColor.GREEN}" +
                                FollowSettingMenuMessages.CLICK_TO_MUTE.asSafety(player.wrappedLocale))
                        else -> addLore("${ChatColor.RED}" +
                                FollowSettingMenuMessages.MAX_MUTE.asSafety(player.wrappedLocale))
                    }
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                if (player.mutes >= Config.PLAYER_MAX_MUTE) return true
                player.mute(to.uniqueId)
                MutePlayerMenu.reopen(player)
                PlayerSounds.TOGGLE.playOnly(player)
                return true
            }
        }
    }

}