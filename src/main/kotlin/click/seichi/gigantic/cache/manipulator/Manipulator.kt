package click.seichi.gigantic.cache.manipulator

import click.seichi.gigantic.cache.cache.Cache

/**
 * [Cache]のデータに対応したマニピュレーターを表す
 *
 * [M]: Mutable
 *
 * If you wants to use not mutable value,
 * You can use [click.seichi.gigantic.cache.key.Key].
 *
 * @author tar0ss
 */
interface Manipulator<M : Manipulator<M, C>, C : Cache<C>> {

    fun from(cache: Cache<C>): M?

    fun set(cache: Cache<C>): Boolean

}