package click.seichi.gigantic.nexus

import click.seichi.gigantic.will.Will
import java.util.*

/**
 * 意志との繋がりを持つ保護
 * @author tar0ss
 */
class WillNexus(
        owner: UUID?,
        val will: Will
) : Nexus(owner) {
    constructor(will: Will) : this(null, will)
}