package click.seichi.gigantic.skill

import click.seichi.gigantic.util.Box

/**
 * @author tar0ss
 */
enum class BreakStyle(val marginHeight: (Box) -> Int) {
    // 目の高さと同じ位置
    NORMAL({ 1 }),
    // 最高高度から見下ろす形
    DOWN({ it.height }),
    // 最低高度から見上げる形
    UP({ 0 }),
    ;
}