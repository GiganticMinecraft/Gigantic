package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.Defaults
import java.util.*

/**
 * @author tar0ss
 */
object RelicMenuMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "レリックを選ぶ"
    )

    val NUM = LocalizedText(
            Locale.JAPANESE to "所持数"
    )

    val WILL = LocalizedText(
            Locale.JAPANESE to "の意志"
    )

    val RELICS = LocalizedText(
            Locale.JAPANESE to "レリック一覧"
    )

    val CONDITIONS = LocalizedText(
            Locale.JAPANESE to "条件: "
    )

    val BONUS_EXP = LocalizedText(
            Locale.JAPANESE to "ボーナス経験値: "
    )

    val BREAK_MUL = LocalizedText(
            Locale.JAPANESE to "整地量 x "
    )

    val GENERATED_ETHEL = LocalizedText(
            Locale.JAPANESE to "のエーテルを${Defaults.RELIC_GENERATOR_REQUIRE_ETHEL}使って"
    )

    val GENERATED_RELIC_PREFIX =
            LocalizedText(
                    Locale.JAPANESE to "レリック「"
            )
    val GENERATED_RELIC_SUFIX =
            LocalizedText(
                    Locale.JAPANESE to "」を手に入れた!!"
            )

    val RELATIONSHIP = LocalizedText(
            Locale.JAPANESE to "あなたとの関係: "
    )


}