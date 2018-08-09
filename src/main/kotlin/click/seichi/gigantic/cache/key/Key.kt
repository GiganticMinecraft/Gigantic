package click.seichi.gigantic.cache.key

import click.seichi.gigantic.cache.cache.Cache


/**
 * [Cache]のデータに対応したキーを表す.
 *
 * [V] : Immutable or Primitive
 *
 * If you wants to use mutable value,
 * You can use [click.seichi.gigantic.cache.manipulator.Manipulator].
 *
 * @author unicroak
 * @author tar0ss
 */
interface Key<S : Cache<S>, V> {

    val default: V

    fun satisfyWith(value: V): Boolean
}