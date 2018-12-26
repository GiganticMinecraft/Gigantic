package click.seichi.gigantic.breaker

/**
 * @author tar0ss
 */
data class BreakArea(
        // 幅
        val width: Int,
        // 高さ
        val height: Int,
        // 奥行
        val depth: Int
) {
    fun add(width: Int, height: Int, depth: Int): BreakArea {
        // 幅は必ず奇数値
        var nextWidth = this.width + width
        if (nextWidth % 2 == 0) {
            nextWidth--
        }
        return BreakArea(
                nextWidth,
                this.height + height,
                this.depth + depth
        )
    }

    fun calcBreakNum() = width * height * depth
}