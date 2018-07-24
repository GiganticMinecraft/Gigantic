package click.seichi.gigantic.data.key

import click.seichi.gigantic.data.container.DataContainer

/**
 * @author tar0ss
 */
interface Key<S : DataContainer<S>, V> {

    val default: V

    fun satisfyWith(value: V): Boolean

}