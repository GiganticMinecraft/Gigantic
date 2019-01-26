package click.seichi.gigantic.will

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.WillMessages
import click.seichi.gigantic.relic.WillRelic
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
enum class WillRelationship(
        private val localizedName: LocalizedText,
        val maxDistance: Double
) {
    // 新友
    FRESH(WillMessages.FRESH, 4.5),
    // 普友
    HOMIE(WillMessages.HOMIE, 5.0),
    // 深友
    FRIEND(WillMessages.FRIEND, 6.0),
    // 親友
    BESTIE(WillMessages.BESTIE, 7.5),
    // 臣友
    BFF(WillMessages.BFF, 9.0),
    // 心友
    SOULMATE(WillMessages.SOULMATE, 15.0),
    // 神友
    PARTNER(WillMessages.PARTNER, Double.MAX_VALUE),
    ;

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    companion object {
        private val relicTypeMap = WillRelic.values().groupBy { it.will }

        fun calcRelationship(player: Player, will: Will): WillRelationship {

            // 持っているレリック
            val pRelicList = relicTypeMap[will]!!.filter { it.relic.has(player) }
            val allNum = pRelicList.fold(0L) { source, willRelic ->
                source + willRelic.relic.getDroppedNum(player)
            }
            // コンプリート
            return if (pRelicList.size == relicTypeMap[will]!!.size) {
                when {
                    // かつ総数が100以上
                    allNum >= 100 -> PARTNER
                    allNum >= 25 -> SOULMATE
                    else -> BFF
                }
            } else {
                when {
                    // 持っているレリックの種類が７つ以上
                    pRelicList.size >= 7 -> BESTIE
                    pRelicList.size >= 5 -> FRIEND
                    pRelicList.size >= 3 -> HOMIE
                    else -> FRESH
                }
            }
        }
    }

}