package click.seichi.gigantic.quest

import org.joda.time.DateTime

/**
 * @author tar0ss
 */
class QuestPlayer(
        val quest: Quest,
        var isOrdered: Boolean,
        var orderedAt: DateTime,
        var isProcessed: Boolean,
        var processedDegree: Int
) {

}