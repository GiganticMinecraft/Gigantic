package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.Defaults
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object RelicGeneratorMenuMessages {
    val TITLE = LocalizedText(
            Locale.JAPANESE to "レリック生成器"
    )

    val SELECT_ETHEL = LocalizedText(
            Locale.JAPANESE to "のエーテル"
    )

    val SELECTED = LocalizedText(
            Locale.JAPANESE to "選択中: "
    )

    val NULL_OF_ETHEL = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RESET}" +
                    "${ChatColor.RED}" +
                    "エーテルを選択してください"
    )

    val LOST_OF_ETHEL = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RESET}" +
                    "${ChatColor.RED}" +
                    "必要なエーテルが足りません"
    )

    val USE_ETHEL = LocalizedText(
            Locale.JAPANESE to "のエーテルを${Defaults.RELIC_GENERATOR_REQUIRE_ETHEL}消費します"
    )

    val GENERATE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RESET}" +
                    "${ChatColor.GREEN}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                    "生成する"
    )

}