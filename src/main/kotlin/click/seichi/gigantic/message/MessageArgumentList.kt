package click.seichi.gigantic.message

import click.seichi.gigantic.extension.NOT_AVAILABLE

/**
 * @author unicroak
 */
class MessageArgumentList : ArrayList<Any> {

    constructor(elements: Collection<Any>) : super(elements)

    constructor(vararg arguments: Any) : this(arguments.toList())

    override fun get(index: Int) = try {
        super.get(index)
    } catch (ex: IndexOutOfBoundsException) {
        String.NOT_AVAILABLE
    }

}
