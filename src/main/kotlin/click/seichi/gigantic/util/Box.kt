package click.seichi.gigantic.util

/**
 * @author tar0ss
 */
open class Box(
        val width: Int,
        val height: Int,
        val depth: Int
) {
    constructor(box: Box) : this(box.width, box.height, box.depth)
}