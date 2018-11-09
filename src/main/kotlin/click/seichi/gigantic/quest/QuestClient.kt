package click.seichi.gigantic.quest

import org.joda.time.DateTime

/**
 * @author tar0ss
 */
class QuestClient(
        val quest: Quest,
        var isOrdered: Boolean,
        var orderedAt: DateTime,
        var isProcessed: Boolean,
        /**
         * [processedDegree]
         *
         *  0: 開始直後
         *  n: モンスター討伐n回目
         *
         */
        var processedDegree: Int
) {

    // クエスト発注
    fun order() {
        if (isOrdered) return
        isOrdered = true
        orderedAt = DateTime.now()
    }

    // クエスト進行
    fun process(degree: Int) {
        isProcessed = true
        processedDegree = degree
    }

    // クエスト完了
    fun complete() {
        isOrdered = false
        isProcessed = false
        processedDegree = 0
    }

}