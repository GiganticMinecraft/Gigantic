package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.player.skill.SkillParameters
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
object HookedItemMessages {

    val PICKEL = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.WHITE}" +
                    "不思議なピッケル"
    )

    val PICKEL_LORE = { player: Player ->
        mutableListOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GRAY}" +
                                "どんなに使っても折れない"
                )
        ).apply {
            if (LockedFunction.SKILL_SWITCH.isUnlocked(player)) {
                add(LocalizedText(
                        Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                "\"${SkillParameters.SWITCH_KEY}\" キー を押してツールを変更"
                ))
            }
        }.toList()
    }

    val SHOVEL = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.WHITE}" +
                    "不思議なシャベル"
    )

    val SHOVEL_LORE = { player: Player ->
        mutableListOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GRAY}" +
                                "どんなに使っても折れない"
                )
        ).apply {
            if (LockedFunction.SKILL_SWITCH.isUnlocked(player)) {
                add(LocalizedText(
                        Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                "\"${SkillParameters.SWITCH_KEY}\" キー を押してツールを変更"
                ))
            }
        }.toList()
    }

    val AXE = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.WHITE}" +
                    "不思議な斧"
    )

    val AXE_LORE = { player: Player ->
        mutableListOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GRAY}" +
                                "どんなに使っても折れない"
                )
        ).apply {
            if (LockedFunction.SKILL_SWITCH.isUnlocked(player)) {
                add(LocalizedText(
                        Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                "\"${SkillParameters.SWITCH_KEY}\" キー を押してツールを変更"
                ))
            }
        }.toList()
    }


    val BUCKET = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.WHITE}" +
                    "不思議なバケツ"
    )

    val BUCKET_LORE = { player: Player ->
        mutableListOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GRAY}" +
                                "どんなにすくっても溜まらない"
                )
        ).apply {
            if (LockedFunction.SKILL_SWITCH.isUnlocked(player)) {
                add(LocalizedText(
                        Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                "\"${SkillParameters.SWITCH_KEY}\" キー を押してツールを変更"
                ))
            }
        }.toList()
    }

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
                            "持続時間: ${SkillParameters.MINE_BURST_DURATION}秒"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.AQUA}" +
                            "クールタイム: ${SkillParameters.MINE_BURST_COOLTIME}秒"
            ),
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
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
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "視点方向にワープ"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "クールタイム: ${SkillParameters.FLASH_COOLTIME}秒"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "\"${SkillParameters.FLASH_KEY}\" キー を押して発動"
                    )
            )


    val SWITCH_DETAIL = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}" +
                    "スイッチ詳細設定"
    )

    val SWITCH_DETAIL_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "ショートカット \"${SkillParameters.SWITCH_SETTING_KEY}\" キー"
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

    val TELEPORT = LocalizedText(
            Locale.JAPANESE to
                    "${ChatColor.BLUE}" +
                    "テレポート"
    )

    val TELEPORT_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "ショートカット \"${SkillParameters.TELEPORT_KEY}\" キー"
                    )
            )

}