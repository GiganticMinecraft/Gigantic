package click.seichi.gigantic.quest

import org.joda.time.DateTime

/**
 * @author tar0ss
 */
data class QuestClient(
        val quest: Quest,
        var isOrdered: Boolean,
        var orderedAt: DateTime,
        var isProcessed: Boolean,
        /**
         * [processedDegree]
         *
         *  0: 開始直後
         *  n: モンスター討伐n回目
         */
        var processedDegree: Int
)