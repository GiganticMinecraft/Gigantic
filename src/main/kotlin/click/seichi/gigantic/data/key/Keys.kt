package click.seichi.gigantic.data.key

import click.seichi.gigantic.data.container.PlayerDataContainer

/**
 * @author tar0ss
 */
object Keys {

    val MINE_COMBO = object : Key<PlayerDataContainer, Long> {
        override val default: Long
            get() = 0L

        override fun satisfyWith(value: Long): Boolean {
            return value >= 0L
        }
    }

}