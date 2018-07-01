package click.seichi.gigantic.util

/**
 * n次多項式クラス
 * 0乗から係数を設定する
 *
 * @author tar0ss
 */
class Polynomial(vararg coef: Double) {
    private val coefList = coef.toList()

    private val maxN = coefList.size

    fun calculation(x: Int): Long {
        var sum = 0L
        (0..maxN).forEach { sum += calcItem(it, x).toLong() }
        return sum
    }

    private fun calcItem(n: Int, x: Int) = pow(x, n) * coefList[n]

    private fun pow(a: Int, n: Int): Long {
        var sum = 1L
        (0 until n).forEach { sum *= a }
        return sum
    }
}