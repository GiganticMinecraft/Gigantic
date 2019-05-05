package click.seichi.gigantic.product

import click.seichi.gigantic.Currency

/**
 * @author tar0ss
 */
enum class Product(
        val id: Int,
        val currency: Currency,
        val price: Int
) {

    ;
}