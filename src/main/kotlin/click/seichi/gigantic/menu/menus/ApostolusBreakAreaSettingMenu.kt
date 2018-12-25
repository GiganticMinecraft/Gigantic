package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.ApostolusMenuMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object ApostolusBreakAreaSettingMenu : Menu() {

    override val size: Int
        get() = 3 * 9

    override fun getTitle(player: Player): String {
        return ApostolusMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        // 戻るボタン
        registerButton(0, BackButton(this, SpellMenu))

        registerButton(11, object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                return ItemStack(Material.DIAMOND_PICKAXE).apply {
                    setDisplayName(ApostolusMenuMessages.CURRENT_AREA.asSafety(player.wrappedLocale))

                    val breakArea = player.getOrPut(Keys.APOSTOLUS_BREAK_AREA)

                    setLore(*ApostolusMenuMessages.CURRENT_AREA_LORE(breakArea)
                            .map { it.asSafety(player.wrappedLocale) }
                            .toTypedArray())

                    itemMeta = itemMeta.apply {
                        addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                reopen(player)
            }

        })

        // 横幅変更（上げる）
        registerButton(12, object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                return ItemStack(Material.YELLOW_STAINED_GLASS_PANE).apply {
                    val breakArea = player.getOrPut(Keys.APOSTOLUS_BREAK_AREA)
                    setDisplayName(ApostolusMenuMessages.BIGGER_WIDTH(breakArea).asSafety(player.wrappedLocale))
                }
            }


            override fun onClick(player: Player, event: InventoryClickEvent) {
                player.transform(Keys.APOSTOLUS_BREAK_AREA) { breakArea ->
                    val nextBreakArea = breakArea.add(2, 0, 0)
                    if (nextBreakArea.width > 0)
                        nextBreakArea
                    else breakArea
                }
                PlayerSounds.TOGGLE.playOnly(player)
                reopen(player)
            }

        })
        // 横幅変更（下げる）
        registerButton(10,
                object : Button {
                    override fun findItemStack(player: Player): ItemStack? {
                        return ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE).apply {
                            val breakArea = player.getOrPut(Keys.APOSTOLUS_BREAK_AREA)
                            setDisplayName(ApostolusMenuMessages.SMALLER_WIDTH(breakArea).asSafety(player.wrappedLocale))
                        }
                    }

                    override fun onClick(player: Player, event: InventoryClickEvent) {
                        player.transform(Keys.APOSTOLUS_BREAK_AREA) { breakArea ->
                            val nextBreakArea = breakArea.add(-2, 0, 0)
                            if (nextBreakArea.width > 0)
                                nextBreakArea
                            else breakArea
                        }
                        PlayerSounds.TOGGLE.playOnly(player)
                        reopen(player)
                    }

                })

        // 高さ変更（上げる）
        registerButton(2,
                object : Button {
                    override fun findItemStack(player: Player): ItemStack? {
                        return ItemStack(Material.ORANGE_STAINED_GLASS_PANE).apply {
                            val breakArea = player.getOrPut(Keys.APOSTOLUS_BREAK_AREA)
                            setDisplayName(ApostolusMenuMessages.BIGGER_HEIGHT(breakArea).asSafety(player.wrappedLocale))
                        }
                    }

                    override fun onClick(player: Player, event: InventoryClickEvent) {
                        player.transform(Keys.APOSTOLUS_BREAK_AREA) { breakArea ->
                            val nextBreakArea = breakArea.add(0, 1, 0)
                            if (nextBreakArea.height > 0)
                                nextBreakArea
                            else breakArea
                        }
                        PlayerSounds.TOGGLE.playOnly(player)
                        reopen(player)
                    }

                })
        // 高さ変更（下げる）
        registerButton(20,
                object : Button {
                    override fun findItemStack(player: Player): ItemStack? {
                        return ItemStack(Material.PINK_STAINED_GLASS_PANE).apply {
                            val breakArea = player.getOrPut(Keys.APOSTOLUS_BREAK_AREA)
                            setDisplayName(ApostolusMenuMessages.SMALLER_HEIGHT(breakArea).asSafety(player.wrappedLocale))
                        }
                    }

                    override fun onClick(player: Player, event: InventoryClickEvent) {
                        player.transform(Keys.APOSTOLUS_BREAK_AREA) { breakArea ->
                            val nextBreakArea = breakArea.add(0, -1, 0)
                            if (nextBreakArea.height > 0)
                                nextBreakArea
                            else breakArea
                        }
                        PlayerSounds.TOGGLE.playOnly(player)
                        reopen(player)
                    }

                })

        // 奥行変更（上げる）
        registerButton(3,
                object : Button {
                    override fun findItemStack(player: Player): ItemStack? {
                        return ItemStack(Material.MAGENTA_STAINED_GLASS_PANE).apply {
                            val breakArea = player.getOrPut(Keys.APOSTOLUS_BREAK_AREA)
                            setDisplayName(ApostolusMenuMessages.BIGGER_DEPTH(breakArea).asSafety(player.wrappedLocale))
                        }
                    }

                    override fun onClick(player: Player, event: InventoryClickEvent) {
                        player.transform(Keys.APOSTOLUS_BREAK_AREA) { breakArea ->
                            val nextBreakArea = breakArea.add(0, 0, 1)
                            if (nextBreakArea.depth > 0)
                                nextBreakArea
                            else breakArea
                        }
                        PlayerSounds.TOGGLE.playOnly(player)
                        reopen(player)
                    }

                })
        // 奥行変更（下げる）
        registerButton(19,
                object : Button {
                    override fun findItemStack(player: Player): ItemStack? {
                        return ItemStack(Material.LIME_STAINED_GLASS_PANE).apply {
                            val breakArea = player.getOrPut(Keys.APOSTOLUS_BREAK_AREA)
                            setDisplayName(ApostolusMenuMessages.SMALLER_DEPTH(breakArea).asSafety(player.wrappedLocale))
                        }
                    }

                    override fun onClick(player: Player, event: InventoryClickEvent) {
                        player.transform(Keys.APOSTOLUS_BREAK_AREA) { breakArea ->
                            val nextBreakArea = breakArea.add(0, 0, -1)
                            if (nextBreakArea.depth > 0)
                                nextBreakArea
                            else breakArea
                        }
                        PlayerSounds.TOGGLE.playOnly(player)
                        reopen(player)
                    }

                })
    }

}