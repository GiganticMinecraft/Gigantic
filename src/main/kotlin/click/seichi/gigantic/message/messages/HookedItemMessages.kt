package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.skill.SkillParameters
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object HookedItemMessages {

    val PICKEL = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.WHITE}" +
                    "ピッケル"
    )

    val SHOVEL = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.WHITE}" +
                    "シャベル"
    )

    val AXE = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.WHITE}" +
                    "斧"
    )

    val MINE_BURST = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}" +
                    "マインバースト"
    )

    val MINE_BURST_LORE = listOf(
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.GRAY}" +
                                "${SkillParameters.MINE_BURST_DURATION}秒間破壊速度上昇"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.AQUA}" +
                                "クールタイム : ${SkillParameters.MINE_BURST_COOLTIME}秒"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.DARK_GRAY}" +
                                "\"${SkillParameters.MINE_BURST_KEY}\" キー を押して発動"
                )
        )


    val FLASH = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}" +
                    "フラッシュ"
    )

    val FLASH_LORE =
        listOf(
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.GRAY}" +
                                "ブロックに向けて発動すると"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.GRAY}" +
                                "そのブロックに向けてテレポート"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.AQUA}" +
                                "クールタイム : ${SkillParameters.FLASH_COOLTIME}秒"
                ),
                LocalizedText(
                        Locale.JAPANESE to
                                "${ChatColor.DARK_GRAY}" +
                                "\"${SkillParameters.FLASH_KEY}\" キー を押して発動"
                )
        )


    val SWITCH = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}${ChatColor.BOLD}" +
                    "スイッチ詳細設定"
    )


}