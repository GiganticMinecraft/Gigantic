package click.seichi.gigantic.database

import click.seichi.gigantic.database.dao.rank.RankValue
import java.util.*

/**
 * @author tar0ss
 */
class RankEntity(uniqueId: UUID) {

    val rankValue = RankValue.findById(uniqueId) ?: RankValue.new(uniqueId) {}

}