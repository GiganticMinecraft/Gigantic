package click.seichi.gigantic.product

import click.seichi.gigantic.Currency
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.database.dao.PurchaseHistory
import click.seichi.gigantic.database.dao.user.User
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PurchaseMessages
import click.seichi.gigantic.player.PurchaseTicket
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * @author tar0ss
 */
enum class Product(
        val id: Int,
        // 購入に使用する通貨
        val currency: Currency,
        // 購入に必要な金額
        val price: Int,
        // 購入できる最大数
        val maxAmount: Int
) {
    EXPLOSION(
            1,
            Currency.VOTE_POINT,
            50,
            1
    ),
    BLIZZARD(
            2,
            Currency.VOTE_POINT,
            70,
            1
    ),
    MAGIC(
            3,
            Currency.DONATE_POINT,
            50,
            1
    ),
    FLAME(
            4,
            Currency.VOTE_POINT,
            30,
            1
    ),
    WITCH_SCENT(
            5,
            Currency.DONATE_POINT,
            30,
            1
    ),
    SLIME(
            6,
            Currency.DONATE_POINT,
            10,
            1
    ),
    BUBBLE(
            7,
            Currency.DONATE_POINT,
            10,
            1
    ),
    ALCHEMIA(
            8,
            Currency.DONATE_POINT,
            20,
            1
    ),
    ;

    companion object {
        // 重複確認
        val hasDuplicateId = values().size != values().map { it.id }.toSet().size

        val idMap = values().map { it.id to it }.toMap()

        fun findById(id: Int) = idMap[id]
    }

    // 購入した個数
    fun boughtAmount(player: Player): Int {
        return getTicketList(player)
                .filter { !it.isCancelled }
                .fold(0) { source, ticket ->
                    source + ticket.amount
                }
    }

    fun getTicketList(player: Player): List<PurchaseTicket> {
        return player.getOrPut(Keys.PURCHASE_TICKET_LIST)
                .filter { it.product == this }
    }

    fun buy(player: Player, amount: Int = 1) {
        val new = PurchaseTicket(
                null,
                this,
                amount
        )
        // ユーザー側に反映させる
        player.transform(Keys.PURCHASE_TICKET_LIST) {
            it.toMutableList().apply {
                add(new)
            }
        }
        val uniqueId = player.uniqueId
        var purchaseId: Int? = null
        //非同期でデータベースに追加
        runTaskAsync {
            transaction {
                val user = User.findById(uniqueId) ?: return@transaction
                purchaseId = PurchaseHistory.new {
                    this@new.user = user
                    this@new.productId = new.product.id
                    this@new.amount = new.amount
                    this@new.createdAt = new.date
                    this@new.isCancelled = new.isCancelled
                    if (new.cancelledAt != null)
                        this@new.cancelledAt = new.cancelledAt
                }.id.value
            }
            // データベースに挿入した情報を反映する
            runTask {
                if (purchaseId == null) {
                    warning(PurchaseMessages.CONSOLE_ERROR_INSERT_DATABASE)
                    warning("UUID:$uniqueId")
                    warning("PurchaseTicket:$new")
                }
                if (!player.isValid) return@runTask
                if (purchaseId == null) {
                    PurchaseMessages.USER_ERROR_INSERT_DATABASE.sendTo(player)
                }
                purchaseId?.let { id ->
                    player.find(Keys.PURCHASE_TICKET_LIST)?.find { it === new }?.setIdIfAbsent(id)
                }
            }
        }
    }

    fun canBuy(player: Player, amount: Int = 1): Boolean {
        return boughtAmount(player) + amount <= maxAmount &&
                currency.calcRemainAmount(player) >= price.times(amount)
    }
}