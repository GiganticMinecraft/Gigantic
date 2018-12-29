package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.enchantLevel
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
                            "これを持っていると魔法が使える"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                            "右クリックでしまう"
            )
    )

    val SEICHI_SPEED_ENCHANT = { level: Int ->
        val levelString = String.enchantLevel(level)
        LocalizedText(
                Locale.JAPANESE to "整地効率 $levelString"
        )
    }

    val COMBO_ATTACK_ENCHANT = { level: Int ->
        val levelString = String.enchantLevel(level)
        LocalizedText(
                Locale.JAPANESE to "コンボアタック $levelString"
        )
    }

    val CONDENSE_WATER_ENCHANT = LocalizedText(
            Locale.JAPANESE to "氷結 Ⅰ"
    )

    val CONDENSE_LAVA_ENCHANT = LocalizedText(
            Locale.JAPANESE to "火成 Ⅰ"
    )

    val CUT_ENCHANT = LocalizedText(
            Locale.JAPANESE to "木こり Ⅰ"
    )


}