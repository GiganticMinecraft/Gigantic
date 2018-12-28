package click.seichi.gigantic.player

import org.joda.time.DateTime

/**
 * @author tar0ss
 */
data class DonateTicket(
        val date: DateTime,
        val amount: Int
)