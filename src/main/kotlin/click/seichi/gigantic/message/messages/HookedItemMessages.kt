package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object HookedItemMessages {

    val PICKEL = LocalizedText(
            Locale.JAPANESE to "不思議なピッケル"
    )

    val PICKEL_LORE = mutableListOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "どんなに使っても折れない"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                            "\"f\" キー を押してツールを変更"
            )
    ).toList()


    val SHOVEL = LocalizedText(
            Locale.JAPANESE to "不思議なシャベル"
    )

    val SHOVEL_LORE = mutableListOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "どんなに使っても折れない"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                            "\"f\" キー を押してツールを変更"
            )
    ).toList()


    val AXE = LocalizedText(
            Locale.JAPANESE to "不思議な斧"
    )

    val AXE_LORE = mutableListOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "どんなに使っても折れない"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                            "\"f\" キー を押してツールを変更"
            )
    ).toList()

    val SWORD = LocalizedText(
            Locale.JAPANESE to "不思議な剣"
    )

    val SWORD_LORE = mutableListOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "どんなに使っても折れない"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                            "\"f\" キー を押してツールを変更"
            )
    ).toList()

    val MINE_BURST = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}" +
                    "マインバースト"
    )

    val MINE_BURST_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "少しの間だけ掘る速度が上昇"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.AQUA}" +
                            "持続時間: ${Config.SKILL_MINE_BURST_DURATION}秒"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.AQUA}" +
                            "クールタイム: ${Config.SKILL_MINE_BURST_COOLTIME}秒"
            )
    )

    val JUMP = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}" +
                    "ジャンプ"
    )

    val JUMP_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "スニークしながら" +
                            "${ChatColor.LIGHT_PURPLE}" +
                            "${Config.ELYTRA_CHARGE_UP_TIME.div(20)}秒" +
                            "${ChatColor.GRAY}" +
                            "チャージ後"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "スニークを離すと飛び立てる!!"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED}" +
                            ""
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "落下中にスペースを押すと滑空できます"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.RED}" +
                            "落下死" +
                            "${ChatColor.GRAY}" +
                            "に注意してください．"
            )
    )


    val SKY_WALK = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}" +
                    "スカイ・ウォーク"
    )

    val FLASH = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}" +
                    "フラッシュ"
    )

    val FLASH_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "視点方向にワープ"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "クールタイム: ${Config.SKILL_FLASH_COOLTIME}秒"
                    )
            )


    val MANA_STONE = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.AQUA}" +
                    "マナストーン"
    )

    val MANA_STONE_LORE = mutableListOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "輝くひし形の石"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GRAY}" +
                            "これを持っているとマルチ・ブレイクが使える"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                            "右クリックでしまう"
            )
    )

}