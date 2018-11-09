package click.seichi.gigantic.quest

import org.joda.time.DateTime

/**
 * @author tar0ss
 */
class QuestClient(
        private val quest: Quest,
        var isOrdered: Boolean,
        var orderedAt: DateTime,
        var isProcessed: Boolean,
        var processedDegree: Int
) {

    // クエスト発注
    fun order() {
        if (isOrdered) return
        isOrdered = true
        orderedAt = DateTime.now()
    }

}