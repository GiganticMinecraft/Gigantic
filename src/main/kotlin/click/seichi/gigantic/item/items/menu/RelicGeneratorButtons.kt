package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.RelicGeneratorMenu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.RelicGeneratorMenuMessages
import click.seichi.gigantic.message.messages.menu.RelicMenuMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.relic.WillRelic
import click.seichi.gigantic.sound.sounds.MenuSounds
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.math.RoundingMode
import kotlin.random.asKotlinRandom

/**
 * @author tar0ss
 */
object RelicGeneratorButtons {

    val SELECT_ETHEL: (Will) -> Button = { will ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                if (!player.hasAptitude(will)) return null
                val selected = player.getOrPut(Keys.SELECTED_WILL)
                return ItemStack(will.material).apply {

                    setDisplayName("${ChatColor.RESET}${ChatColor.WHITE}" +
                            (if (selected == will) RelicGeneratorMenuMessages.SELECTED.asSafety(player.wrappedLocale)
                            else "") +
                            will.chatColor + "${ChatColor.BOLD}" +
                            will.getName(player.wrappedLocale) +
                            RelicGeneratorMenuMessages.SELECT_ETHEL.asSafety(player.wrappedLocale) +
                            "(${player.ethel(will)})"
                    )
                    clearLore()
                    if (player.getOrPut(Keys.SELECTED_WILL) == will) {
                        setEnchanted(true)
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                if (!player.hasAptitude(will)) return false
                val selected = player.getOrPut(Keys.SELECTED_WILL)
                if (selected != null && selected == will) return true
                player.offer(Keys.SELECTED_WILL, will)
                MenuSounds.WILL_SELECT.playOnly(player)
                RelicGeneratorMenu.reopen(player)
                return true
            }
        }
    }

    val GENERATE = object : Button {
        override fun findItemStack(player: Player): ItemStack? {
            val selected = player.getOrPut(Keys.SELECTED_WILL)
            val generated = player.getOrPut(Keys.GENERETED_WILL_RELIC)
            return ItemStack(Material.END_PORTAL_FRAME).apply {
                clearLore()
                when {
                    // 何も選択していない場合
                    selected == null ->
                        setDisplayName(RelicGeneratorMenuMessages.NULL_OF_ETHEL.asSafety(player.wrappedLocale))
                    // 選択したエーテルが不足している場合
                    player.ethel(selected) < Defaults.RELIC_GENERATOR_REQUIRE_ETHEL ->
                        setDisplayName(RelicGeneratorMenuMessages.LOST_OF_ETHEL.asSafety(player.wrappedLocale))
                    else -> {
                        setDisplayName(RelicGeneratorMenuMessages.GENERATE.asSafety(player.wrappedLocale))
                        addLore("${ChatColor.RESET}" +
                                selected.chatColor +
                                "${ChatColor.BOLD}" +
                                selected.getName(player.wrappedLocale) +
                                RelicGeneratorMenuMessages.USE_ETHEL.asSafety(player.wrappedLocale)
                        )
                    }
                }
                addLore(MenuMessages.LINE)

                if (generated != null) {
                    val will = generated.will
                    val relic = generated.relic
                    addLore("" + will.chatColor + "${ChatColor.BOLD}" +
                            will.getName(player.wrappedLocale) +
                            RelicMenuMessages.GENERATED_ETHEL.asSafety(player.wrappedLocale)
                    )

                    addLore("" + will.chatColor + "${ChatColor.BOLD}" +
                            RelicMenuMessages.GENERATED_RELIC_PREFIX.asSafety(player.wrappedLocale) +
                            relic.getName(player.wrappedLocale) +
                            RelicMenuMessages.GENERATED_RELIC_SUFIX.asSafety(player.wrappedLocale)
                    )
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            val selected = player.getOrPut(Keys.SELECTED_WILL) ?: return true
            if (player.ethel(selected) < Defaults.RELIC_GENERATOR_REQUIRE_ETHEL) return true
            // 減算処理
            player.transform(Keys.ETHEL_MAP[selected]!!) { it - Defaults.RELIC_GENERATOR_REQUIRE_ETHEL }

            // ランダムにレリックを選択
            val willRelic = WillRelic.values().filter { it.will == selected }.random(Random.generator.asKotlinRandom())
            // レリックを付与
            willRelic.relic.dropTo(player)
            // レリックを保存
            player.offer(Keys.GENERETED_WILL_RELIC, willRelic)
            // 音を再生
            MenuSounds.RELIC_GENERATE.play(player.location)
            // 更新(サイドバー更新も兼ねて)
            Achievement.update(player, isForced = true)
            // 再度開く
            RelicGeneratorMenu.reopen(player)

            // 更新後にすぐに削除
            object : BukkitRunnable() {
                override fun run() {
                    if (!player.isValid) return
                    player.offer(Keys.GENERETED_WILL_RELIC, null)
                }
            }.runTaskLater(Gigantic.PLUGIN, 1L)
            return true
        }
    }

    val GENERATED = object : Button {
        override fun findItemStack(player: Player): ItemStack? {
            val generated = player.getOrPut(Keys.GENERETED_WILL_RELIC) ?: return null
            val will = generated.will
            val relic = generated.relic
            return ItemStack(generated.material).apply {
                setDisplayName("${ChatColor.RESET}" +
                        will.chatColor + "${ChatColor.BOLD}" +
                        relic.getName(player.wrappedLocale) +
                        "($amount)")
                setLore(*relic.getLore(player.wrappedLocale).map { "${ChatColor.GRAY}" + it }.toTypedArray())
                addLore("${ChatColor.WHITE}" + MenuMessages.LINE)
                val multiplier = generated.calcMultiplier(player)
                addLore("${ChatColor.YELLOW}${ChatColor.BOLD}" +
                        RelicMenuMessages.BONUS_EXP.asSafety(player.wrappedLocale) +
                        "${ChatColor.RESET}${ChatColor.WHITE}" +
                        RelicMenuMessages.BREAK_MUL.asSafety(player.wrappedLocale) +
                        multiplier.toBigDecimal().setScale(2, RoundingMode.HALF_UP))
                val bonusLore = generated.getLore(player.wrappedLocale)
                bonusLore.forEachIndexed { index, s ->
                    if (index == 0) {
                        addLore("${ChatColor.YELLOW}${ChatColor.BOLD}" +
                                RelicMenuMessages.CONDITIONS.asSafety(player.wrappedLocale) +
                                "${ChatColor.RESET}${ChatColor.WHITE}" + s)
                    } else {
                        addLore("${ChatColor.RESET}${ChatColor.WHITE}" + s)
                    }
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            return true
        }
    }

}