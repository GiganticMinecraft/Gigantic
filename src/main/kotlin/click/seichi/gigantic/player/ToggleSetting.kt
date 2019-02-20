package click.seichi.gigantic.player

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
enum class ToggleSetting(
        val id: Int,
        private val localizedName: LocalizedText
) {
    GAIN_EXP(0, LocalizedText(
            Locale.JAPANESE to "獲得経験値表示"
    )),
    UNDER_PLAYER(1, LocalizedText(
            Locale.JAPANESE to "低い位置のブロック破壊警告"
    )),
    COMBO(2, LocalizedText(
            Locale.JAPANESE to "コンボ表示"
    ))
    ;

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getToggle(player: Player) = player.getOrPut(Keys.TOGGLE_SETTING_MAP.getValue(this))

    fun toggle(player: Player) = player.transform(Keys.TOGGLE_SETTING_MAP.getValue(this)) { !it }

}