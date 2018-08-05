package click.seichi.gigantic.data.keys

import click.seichi.gigantic.data.caches.Cache


/**
 * [Cache]のデータに対応したキーを表す.
 *
 * @author unicroak
 * @author tar0ss
 */
interface Key<S : Cache<S>, V> {

    val default: V

    fun satisfyWith(value: V): Boolean
}