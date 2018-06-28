package click.seichi.gigantic.database.remote

import click.seichi.gigantic.Gigantic

/**
 * @author tar0ss
 */
interface Remotable {
    val DB
        get() = Gigantic.DB
}