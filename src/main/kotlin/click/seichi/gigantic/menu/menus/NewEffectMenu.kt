package click.seichi.gigantic.menu.menus

import black.bracken.drainage.dsl.InventoryUI
import black.bracken.drainage.extension.openInventory
import click.seichi.gigantic.Currency
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.extension.getHead
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.sound.sounds.MenuSounds
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author unicroak
 */
class NewEffectMenu(private val pageIndex: Int = 0) : InventoryUI {

    override val layout by build(9 * 6) {
        title = "エフェクト ${pageIndex + 1}/${getAvailableEffectList(player).size / EFFECT_AMOUNT_PER_PAGE + 1}"

        defaultSlot {
            icon(Material.BLACK_STAINED_GLASS_PANE) {
                name = " "
            }
        }

        onOpen {
            if (pageIndex > 0) {
                MenuSounds.PAGE_CHANGE.playOnly(this@build.player)
            } else {
                MenuSounds.MENU_OPEN.playOnly(this@build.player)
            }
        }

        put(10) {
            icon(player.getHead()) {
                name = "${ChatColor.AQUA}${ChatColor.BOLD}プレイヤー情報"
                lore {
                    """
                        ${ChatColor.AQUA}${ChatColor.BOLD}残り${ChatColor.YELLOW}${ChatColor.BOLD}投票p: ${ChatColor.WHITE}${Currency.VOTE_POINT.calcRemainAmount(player)}
                        ${ChatColor.AQUA}${ChatColor.BOLD}残り${ChatColor.GOLD}${ChatColor.BOLD}寄付p: ${ChatColor.WHITE}${Currency.DONATE_POINT.calcRemainAmount(player)}
                        ${MenuMessages.LINE}
                        ${ChatColor.GRAY}JMSから投票することで${Defaults.VOTE_POINT_PER_VOTE}投票ポイント獲得できる
                        ${ChatColor.GRAY}寄付${Defaults.DONATITON_PER_DONATE_POINT}円につき1寄付ポイント獲得できる
                    """.trimIndent()
                }
            }
        }

        put(19) {
            val effect = player.getOrPut(Keys.EFFECT)

            icon(effect.icon) {
                name = "${ChatColor.AQUA}現在使用中のエフェクト"
                lore { effect.lore }

                gleam()
            }
        }

        put(9 until 9 * 4) { slotIndex ->
            val effect = getEffectOrNullAt(player, slotIndex) ?: GiganticEffect.DEFAULT

            icon(effect.icon) {
                name = effect.displayName

                lore += effect.description
                lore {
                    """

                        ${ChatColor.GREEN}${ChatColor.BOLD}通常破壊 : ${ChatColor.RESET}${if (effect.hasGeneralBreakEffect) "あり" else "なし"}
                        ${ChatColor.AQUA}${ChatColor.BOLD}複数破壊 : ${ChatColor.RESET}${if (effect.hasMultiBreakEffect) "あり" else "なし"}
                        ${MenuMessages.LINE}
                        ${if (effect.isSelected(player)) "${ChatColor.YELLOW}${ChatColor.UNDERLINE}選択中" else "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで選択"}
                    """.trimIndent()
                }

                if (effect.isSelected(player)) {
                    gleam()
                }
            }

            onClick {
                if (!effect.isSelected(player)) {
                    effect.select(player)
                    PlayerSounds.TOGGLE.playOnly(player)
                    player.openInventory(NewEffectMenu(pageIndex))
                }
            }

            filter {
                getEffectOrNullAt(player, slotIndex) != null
            }
        }

        put(49) {
            icon(Head.PUMPKIN_LEFT_ARROW.toItemStack()) {
                name = "次へ"
            }

            filter { pageIndex > 0 }

            onClick { player.openInventory(NewEffectMenu(pageIndex - 1)) }
        }

        put(51) {
            icon(Head.PUMPKIN_RIGHT_ARROW.toItemStack()) {
                name = "前へ"
            }

            filter { shouldOpenNextPage(player) }

            onClick { player.openInventory(NewEffectMenu(pageIndex + 1)) }
        }
    }

    companion object {
        private const val EFFECT_AMOUNT_PER_PAGE = 6 * 3
    }

    private fun getAvailableEffectList(player: Player): List<GiganticEffect> {
        return listOf(GiganticEffect.DEFAULT)
                .plus(GiganticEffect.values()) // .plus(player.getOrPut(Keys.MENU_EFFECT_LIST))
                .filter { effect -> effect.isBought(player) }
                .distinct() // GiganticEffect.DEFAULTを頭に持っていく
                .drop(EFFECT_AMOUNT_PER_PAGE * pageIndex) // ページ分飛ばす
    }

    private fun getSlotEffectMap(player: Player): Map<Int, GiganticEffect?> {
        val availableEffectList = getAvailableEffectList(player)

        return (9 until 9 * 4) // インベントリの2行目から4行目
                .filter { it % 9 in 3..7 } // もしスロットの列が3..7より外であれば弾く
                .foldIndexed(mapOf()) { foldIndex, effectList, slotIndex ->
                    effectList.plus(slotIndex to availableEffectList.getOrNull(foldIndex))
                }
    }

    private fun shouldOpenNextPage(player: Player): Boolean {
        return getAvailableEffectList(player).drop(EFFECT_AMOUNT_PER_PAGE).isNotEmpty()
    }

    private fun getEffectOrNullAt(player: Player, index: Int): GiganticEffect? {
        return getSlotEffectMap(player)[index]
    }

}